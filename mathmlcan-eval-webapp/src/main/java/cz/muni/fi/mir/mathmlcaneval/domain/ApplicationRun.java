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
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "application_runs")
public class ApplicationRun extends BaseEntity {

  private static final long serialVersionUID = 4749719209581052866L;

  @Column(name = "run_start")
  private LocalDateTime start;
  @Column(name = "run_end")
  private LocalDateTime end;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "configuration")
  private InputConfiguration inputConfiguration;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "revision")
  private Revision revision;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users")
  private User startedBy;
  @OneToMany(mappedBy = "applicationRun")
  private Set<CanonicOutput> canonicOutputs;
}
