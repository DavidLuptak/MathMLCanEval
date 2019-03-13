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

import cz.muni.fi.mir.mathmlcaneval.support.XmlContent;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formulas")
public class Formula extends BaseEntity implements XmlContent {
  @Column(columnDefinition = "xml")
  private String raw;
  @Column(columnDefinition = "xml")
  private String pretty;
  private String note;
  private String hashValue;
  private LocalDateTime insertTime;
  private byte[] thumbnail;

  @OneToMany(mappedBy = "formula")
  private Set<CanonicOutput> canonicOutputs = new HashSet<>();

  @Override
  public String getXmlContent() {
    return this.raw;
  }
}
