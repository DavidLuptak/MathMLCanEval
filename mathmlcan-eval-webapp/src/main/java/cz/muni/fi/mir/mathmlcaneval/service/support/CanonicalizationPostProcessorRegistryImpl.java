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
package cz.muni.fi.mir.mathmlcaneval.service.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CanonicalizationPostProcessorRegistryImpl implements
  CanonicalizationPostProcessorRegistry {

  private final List<CanonicalizationPostProcessor> postProcessors;

  @Override
  public List<String> getPostProcessors() {
    return postProcessors
      .stream()
      .map(CanonicalizationPostProcessor::name)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<CanonicalizationPostProcessor> getProcessor(String processor) {
    for (CanonicalizationPostProcessor cpp : postProcessors) {
      if (cpp.name().equals(processor)) {
        return Optional.of(cpp);
      }
    }

    return Optional.empty();
  }

  @Override
  public List<CanonicalizationPostProcessor> getProcessors(List<String> processors) {
    final var result = new ArrayList<CanonicalizationPostProcessor>();

    for (CanonicalizationPostProcessor cpp : postProcessors) {
      if (processors.contains(cpp.name())) {
        result.add(cpp);
      }
    }

    return result;
  }
}
