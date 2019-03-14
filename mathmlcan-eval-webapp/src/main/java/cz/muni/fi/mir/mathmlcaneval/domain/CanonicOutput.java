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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "canonic_outputs")
public class CanonicOutput extends BaseEntity implements XmlContent {

  @Column(name = "raw", columnDefinition = "xml")
  private String raw;
  @Column(name="pretty", columnDefinition = "xml")
  private String pretty;
  @Column(name = "hash")
  private String hash;
  @Column(name = "error")
  private String error;
  @Column(name = "thumbnail")
  private byte[] thumbnail;
  @Column(name = "ocr_changed")
  private boolean ocrChanged;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "formula")
  private Formula formula;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_run")
  private ApplicationRun applicationRun;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "similarity_form")
  private SimilarityForm similarityForm;


  @Override
  public String getXmlContent() {
    return this.raw;
  }
}
