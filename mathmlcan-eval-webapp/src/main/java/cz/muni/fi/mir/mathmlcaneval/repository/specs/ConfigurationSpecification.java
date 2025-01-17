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
package cz.muni.fi.mir.mathmlcaneval.repository.specs;

import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration;
import cz.muni.fi.mir.mathmlcaneval.domain.InputConfiguration_;
import cz.muni.fi.mir.mathmlcaneval.domain.User_;
import org.springframework.data.jpa.domain.Specification;

public class ConfigurationSpecification {

  public static Specification<InputConfiguration> publicOrMine(final Long userId){
    return (root,query, cb) -> cb.or(
      cb.equal(root.join(InputConfiguration_.ownedBy).get(User_.id), userId),
      cb.equal(root.get(InputConfiguration_.visibleToPublic), true)
    );
  }
}
