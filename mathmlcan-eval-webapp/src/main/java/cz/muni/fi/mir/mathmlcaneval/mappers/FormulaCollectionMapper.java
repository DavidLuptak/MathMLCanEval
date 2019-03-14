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

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import cz.muni.fi.mir.mathmlcaneval.domain.FormulaCollection;
import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FormulaCollectionMapper {

  @Mapping(source = "ownedBy.id", target = "ownedById")
  @Mapping(source = "ownedBy.name", target = "ownedByName")
  FormulaCollectionResponse map(FormulaCollection formulaCollection);

  FormulaCollection map(FormulaCollectionRequest request);

  List<FormulaCollectionResponse> map(List<FormulaCollection> formulaCollection);

  default Formula map(Long id) {
    Formula f = new Formula();
    f.setId(id);

    return f;
  }
}
