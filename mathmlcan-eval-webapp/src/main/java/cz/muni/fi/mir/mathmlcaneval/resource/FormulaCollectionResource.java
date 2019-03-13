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

import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaCollectionService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class FormulaCollectionResource {

  private final FormulaCollectionService formulaCollectionService;
  private final FormulaService formulaService;

  @GetMapping
  public Page<FormulaCollectionResponse> listAll(Pageable pageable) {
    return formulaCollectionService.findAll(pageable);
  }

  @PostMapping
  public ResponseEntity<FormulaCollectionResponse> save(@Valid @RequestBody
    FormulaCollectionRequest request) {

    FormulaCollectionResponse response = formulaCollectionService.save(request);

    return ResponseEntity.created(URI.create("asd")).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FormulaCollectionResponse> getById(@PathVariable Long id) {
    return ResponseEntity.of(formulaCollectionService.findById(id));
  }

  @GetMapping("/{id}/formulas")
  public ResponseEntity<List<FormulaResponse>> formulasInCollection(@PathVariable Long id) {
    return ResponseEntity.ok(formulaService.getFormulasForCollection(id));
  }
}
