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
package cz.muni.fi.mir.mathmlcaneval.mappers;

import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration;
import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {

  @Mapping(source = "ownedBy.id", target = "ownedById")
  @Mapping(source = "ownedBy.name", target = "ownedByName")
  ConfigurationResponse map(InputConfiguration configuration);

  List<ConfigurationResponse> map(List<InputConfiguration> configuration);

  InputConfiguration map(CreateConfigurationRequest request);
}
