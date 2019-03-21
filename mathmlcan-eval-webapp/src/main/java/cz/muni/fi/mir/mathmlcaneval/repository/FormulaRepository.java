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
package cz.muni.fi.mir.mathmlcaneval.repository;

import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormulaRepository extends JpaRepository<Formula, Long> {

  @Query("SELECT fc.formulas FROM FormulaCollection fc WHERE fc.id = :collection")
  List<Formula> getFormulasInCollection(Long collection);

  @Query("SELECT f.hashValue FROM Formula f WHERE f.hashValue IN :hashes")
  List<String> getFormulasByHashes(List<String> hashes);
}
