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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import cz.muni.fi.mir.mathmlcaneval.service.PatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatchingServiceImpl implements PatchingService {

  private final ObjectMapper objectMapper;


  @Override
  public <S, T> T patch(JsonPatch jsonPatch, S source, Class<T> target) {
    try {
      final var patched = jsonPatch.apply(this.objectMapper.convertValue(source, JsonNode.class));
      return objectMapper.treeToValue(patched, target);
    } catch (JsonPatchException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
