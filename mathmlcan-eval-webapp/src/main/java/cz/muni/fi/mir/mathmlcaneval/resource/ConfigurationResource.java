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

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import cz.muni.fi.mir.mathmlcaneval.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlcaneval.service.InputConfigurationService;
import cz.muni.fi.mir.mathmlcaneval.service.support.JsonPatchParser;
import cz.muni.fi.mir.mathmlcaneval.support.Response;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configurations")
@RequiredArgsConstructor
public class ConfigurationResource {

  private final InputConfigurationService inputConfigurationService;
  private final ApplicationRunService applicationRunService;


  @GetMapping
  public Page<ConfigurationResponse> findAll(Pageable pageable) {
    return inputConfigurationService.findAll(pageable);
  }

  @PostMapping
  public ResponseEntity<ConfigurationResponse> create(
    @Valid @RequestBody CreateConfigurationRequest request) {
    ConfigurationResponse result = inputConfigurationService.save(request);
    return ResponseEntity.created(URI.create("asd")).body(result);
  }

  @GetMapping("/{configId}/app-runs")
  public List<ApplicationRunResponse> runsWithConfiguration(@PathVariable Long configId) {
    return applicationRunService.getRunsByConfiguration(configId);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ConfigurationResponse> patch(@PathVariable Long id, @RequestBody JsonNode input) {
    JsonPatch patch = JsonPatchParser.validateInput(input, null, null);

    return ResponseEntity.ok(inputConfigurationService.update(id, patch));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response> delete(@PathVariable Long id) {
    this.inputConfigurationService.delete(id);

    return Response.OK;
  }
}
