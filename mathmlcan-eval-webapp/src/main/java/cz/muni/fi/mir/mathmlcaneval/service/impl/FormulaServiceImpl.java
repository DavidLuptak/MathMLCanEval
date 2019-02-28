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

import cz.muni.fi.mir.mathmlcaneval.mappers.FormulaMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.FormulaRepository;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FormulaServiceImpl implements FormulaService {

  private final FormulaRepository formulaRepository;
  private final FormulaMapper formulaMapper;

  @ReadOnly
  @Override
  public List<FormulaResponse> findAll() {
    return formulaMapper.map(formulaRepository.findAll());
  }

  @ReadOnly
  @Override
  public Optional<FormulaResponse> findById(Long id) {
    return formulaRepository.findById(id)
      .map(formulaMapper::map);
  }

  @ReadOnly
  @Override
  public List<FormulaResponse> findAll(Pageable pageable) {
    return formulaMapper.map(formulaRepository.findAll(pageable).getContent());
  }

  @ReadOnly
  @Override
  public List<FormulaResponse> getFormulasForCollection(Long collection) {
    return formulaMapper.map(formulaRepository.getFormulasInCollection(collection));
  }
}
