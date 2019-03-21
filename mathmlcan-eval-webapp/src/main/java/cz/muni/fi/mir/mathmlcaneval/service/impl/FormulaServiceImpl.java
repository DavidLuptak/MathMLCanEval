/*
 * Copyright © 2013 the original author or authors (webmias@fi.muni.cz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.mappers.FormulaMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaCollectionRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.FileImportRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.security.SecurityService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import cz.muni.fi.mir.mathmlcaneval.service.XmlDocumentService;
import cz.muni.fi.mir.mathmlcaneval.support.FileValidator;
import cz.muni.fi.mir.mathmlcaneval.support.FileValidator.FileType;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Service
@RequiredArgsConstructor
public class FormulaServiceImpl implements FormulaService {

  private static final int BUFFER_SIZE = 4096;

  private final FormulaRepository formulaRepository;
  private final FormulaMapper formulaMapper;
  private final FileValidator fileValidator;
  private final XmlDocumentService xmlDocumentService;
  private final FormulaCollectionRepository formulaCollectionRepository;
  private final SecurityService securityService;

  @ReadOnly
  @Override
  public Optional<FormulaResponse> findById(Long id) {
    return formulaRepository.findById(id)
      .map(formulaMapper::map);
  }

  @ReadOnly
  @Override
  public Page<FormulaResponse> findAll(Pageable pageable) {
    return formulaRepository
      .findAll(pageable)
      .map(formulaMapper::map);
  }

  @ReadOnly
  @Override
  public List<FormulaResponse> getFormulasForCollection(Long collection) {
    return formulaMapper.map(formulaRepository.getFormulasInCollection(collection));
  }

  @Transactional
  @Override
  public List<Long> massImport(FileImportRequest request) {
    final var formulas = new ArrayList<Formula>();
    FormulaCollection formulaCollection = null;
    for (MultipartFile file : request.getFile()) {
      if (file.getContentType() != null) {
        try (final var buffer = new BufferedInputStream(file.getInputStream())) {
          buffer.mark(0);
          final var header = buffer.readNBytes(4);
          buffer.reset();
          switch (file.getContentType()) {
            case "application/zip":
            case "application/x-zip-compressed": {
              if (fileValidator.isValid(header, FileType.ZIP)) {
                try (final var zip = new ZipArchiveInputStream(buffer)) {
                  ZipArchiveEntry ze = null;
                  while ((ze = zip.getNextZipEntry()) != null) {
                    formulas.add(fromRaw(convert(zip)));
                  }
                } catch (IOException ex) {
                  System.err.println(ex);
                }

                if(file.getOriginalFilename() != null && file.getOriginalFilename().startsWith("collection_")) {
                  String collectionName = StringUtils.substringAfter(file.getOriginalFilename(), "collection_");
                  collectionName = StringUtils.replaceChars(collectionName, '_', ' ');
                  formulaCollection = new FormulaCollection();
                  formulaCollection.setName(collectionName);
                  formulaCollection.setVisibleToPublic(false);
                  formulaCollection.setNote(String.format("Imported on %s from file %s", LocalDateTime.now().toString(), file.getOriginalFilename()));;
                  formulaCollection.setOwnedBy(new User(securityService.getCurrentUserId(false)));
                }
              } else {
                System.out.println("invalid zip file");
              }
            }
            break;
            case "text/xml":
            case "application/xml": {
              if (fileValidator.isValid(header, FileType.XML)) {
                formulas.add(fromRaw(IOUtils.toString(file.getInputStream(), "UTF-8")));
              } else {
                System.out.println("invalid xml");
              }
            }
            break;
            default:
              throw new RuntimeException();
          }
        } catch (IOException ex) {
          System.err.println(ex);
        }
      } else {
        System.out.println("missing content type");
      }
    }

    final var existing = formulaRepository
      .getFormulasByHashes(formulas.stream()
        .map(Formula::getHashValue).collect(Collectors.toList()));

    final var result = new ArrayList<Long>();

    for(Formula imported : formulas) {
      if(!existing.contains(imported.getHashValue())) {
        formulaRepository.save(imported);

        if(formulaCollection != null) {
          formulaCollection.getFormulas().add(imported);
        }

        result.add(imported.getId());
      }
    }

    if(formulaCollection != null) {
      formulaCollectionRepository.save(formulaCollection);
    }

    return result;
  }

  private Formula fromRaw(String raw) {
    final var result = new Formula();
    result.setRaw(raw);
    result.setHashValue(DigestUtils.sha256Hex(result.getRaw().getBytes()));
    result.setInsertTime(LocalDateTime.now());
    final var document = xmlDocumentService.buildDocument(raw);
    result.setPretty(xmlDocumentService.prettyPrintToString(document));

    return result;
  }

  private String convert(InputStream inputStream) throws IOException {
    try (final var out = new ByteArrayOutputStream(BUFFER_SIZE)) {
      final var buffer = new byte[BUFFER_SIZE];
      var count = -1;
      while ((count = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
        out.write(buffer, 0, count);
      }

      return out.toString();
    }
  }
}
