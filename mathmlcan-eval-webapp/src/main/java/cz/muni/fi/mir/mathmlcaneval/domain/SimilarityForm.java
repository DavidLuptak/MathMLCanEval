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

import cz.muni.fi.mir.mathmlcaneval.support.CustomDoubleArrayType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

@Getter
@Setter
@Entity
@TypeDef(name = "double-array", typeClass = CustomDoubleArrayType.class, defaultForType = double[].class)
@Table(name = "canonic_outputs_similarity_forms")
public class SimilarityForm extends BaseEntity {

  private static final long serialVersionUID = -2548045225317036120L;
  @Column(name = "vector_form")
  private double[] vectorForm;
  @Column(name = "text_form")
  private String textForm;
}
