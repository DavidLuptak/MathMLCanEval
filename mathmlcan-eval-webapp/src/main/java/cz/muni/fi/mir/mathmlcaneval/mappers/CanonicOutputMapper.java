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

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.responses.ApplicationRunDetailedResponse.CanonicalizationContainer;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CanonicOutputMapper {
  @Mapping(source = "id", target = "canonicId")
  @Mapping(source = "error", target = "canonicalizationError")
  @Mapping(source = "pretty", target = "canonicXml")
  @Mapping(source = "formula.id", target = "formulaId")
  @Mapping(source = "formula.pretty", target = "formulaXml")
  CanonicalizationContainer map(CanonicOutput canonicOutput);

  Set<CanonicalizationContainer> map(Set<CanonicOutput> outputs);
}
