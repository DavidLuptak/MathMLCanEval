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

import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import cz.muni.fi.mir.mathmlcaneval.service.InputConfigurationService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configurations")
@RequiredArgsConstructor
public class ConfigurationResource {

  private final InputConfigurationService inputConfigurationService;


  @GetMapping
  public List<ConfigurationResponse> findAll() {
    return inputConfigurationService.findAll();
  }

  @PostMapping
  public ResponseEntity<ConfigurationResponse> create(
    @Valid @RequestBody CreateConfigurationRequest request) {
    ConfigurationResponse result = inputConfigurationService.save(request);
    return ResponseEntity.created(URI.create("asd")).body(result);
  }
}
