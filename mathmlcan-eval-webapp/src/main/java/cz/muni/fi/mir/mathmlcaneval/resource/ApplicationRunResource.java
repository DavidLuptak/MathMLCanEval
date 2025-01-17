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

import cz.muni.fi.mir.mathmlcaneval.requests.CanonicalizationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunDetailedResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunResponse;
import cz.muni.fi.mir.mathmlcaneval.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlcaneval.support.Response;
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
@RequestMapping("/api/app-runs")
@RequiredArgsConstructor
public class ApplicationRunResource {
  private final ApplicationRunService applicationRunService;


  @PostMapping
  public ResponseEntity<Response> submit(@Valid @RequestBody CanonicalizationRequest request) {
    applicationRunService.save(request);

    return ResponseEntity.accepted().build();
  }

  @GetMapping
  public Page<ApplicationRunResponse> query(Pageable pageable) {
    return applicationRunService.query(pageable);
  }

  @GetMapping("/{id}/details")
  public ResponseEntity<ApplicationRunDetailedResponse> fetchDetails(@PathVariable Long id) {
    return ResponseEntity.of(applicationRunService.fetchDetailed(id));
  }
}
