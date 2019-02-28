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
package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaCollectionService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/formulas")
@RestController
@RequiredArgsConstructor
public class FormulaResource {

  private final FormulaService formulaService;
  private final FormulaCollectionService formulaCollectionService;
  @GetMapping
  public List<FormulaResponse> findAll(Pageable pageable) {
    return formulaService.findAll(pageable);
  }

  @GetMapping("/{id}/collections")
  public List<FormulaCollectionResponse> collections(@PathVariable Long formulaId) {
    return formulaCollectionService.collectionsWithFormula(formulaId);
  }
}
