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
package cz.muni.fi.mir.mathmlcaneval.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formula_collections")
public class FormulaCollection extends BaseEntity {

  private static final long serialVersionUID = 581198003680257750L;

  @Column(name = "name")
  private String name;
  @Column(name = "note")
  private String note;
  @Column(name = "visible_to_public")
  private Boolean visibleToPublic;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  private User creator;
  @OneToMany
  @JoinTable(name = "formula_collections_formulas",
    joinColumns = @JoinColumn(name = "formula_collection", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "formula", referencedColumnName = "id"))
  private Set<Formula> formulas;
}
