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

import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.mappers.FormulaCollectionMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaCollectionRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import cz.muni.fi.mir.mathmlcaneval.security.SecurityService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaCollectionService;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FormulaCollectionServiceImpl implements FormulaCollectionService {

  private final FormulaCollectionRepository formulaCollectionRepository;
  private final FormulaCollectionMapper formulaCollectionMapper;
  private final SecurityService securityService;

  @Override
  @Transactional
  public FormulaCollectionResponse save(FormulaCollectionRequest create) {
    FormulaCollection collection = formulaCollectionMapper.map(create);
    collection.setCreator(new User(securityService.getCurrentUserId()));

    formulaCollectionRepository.save(collection);

    return formulaCollectionMapper.map(collection);
  }

  @Override
  @ReadOnly
  public List<FormulaCollectionResponse> findAll() {
    return formulaCollectionMapper.map(formulaCollectionRepository.findAll());
  }

  @Override
  @ReadOnly
  public Optional<FormulaCollectionResponse> findById(Long id) {
    return formulaCollectionRepository
      .findById(id)
      .map(formulaCollectionMapper::map);
  }

  @Override
  @ReadOnly
  public List<FormulaCollectionResponse> collectionsWithFormula(Long formulaId) {
    return formulaCollectionMapper.map(formulaCollectionRepository.getCollectionsWithFormula(formulaId));
  }
}
