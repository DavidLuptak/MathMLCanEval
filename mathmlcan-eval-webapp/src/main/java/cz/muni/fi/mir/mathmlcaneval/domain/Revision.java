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

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "revisions")
public class Revision extends BaseEntity {

  private static final long serialVersionUID = -7738917977580794290L;
  @Column(name = "name")
  private String name;
  @Column(name = "note")
  private String note;
  @Column(name = "sha1")
  private String sha1; //40 chars
  @Column(name = "commit_time")
  private LocalDateTime commitTime;
  @Column(name = "sync_time")
  private LocalDateTime syncTime;
}
