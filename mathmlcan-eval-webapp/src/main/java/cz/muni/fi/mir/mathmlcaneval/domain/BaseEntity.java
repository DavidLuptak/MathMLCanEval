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

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 * Super entity containing id and temporary id used during set operations
 *
 * @author dominik.szalai
 * @since 2.0.0
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue
  protected Long id;

  @Transient
  private UUID tempId;

  private UUID getTempId() {
    if (tempId == null) {
      tempId = UUID.randomUUID();
    }

    return tempId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseEntity that = (BaseEntity) o;
    if (that.getId() == null || this.getId() == null) {
      return Objects.equals(that.getTempId(), this.getTempId());
    } else {
      return Objects.equals(id, that.id);
    }
  }

  @Override
  public int hashCode() {
    if (id == null) {
      return Objects.hashCode(getTempId());
    } else {
      return Objects.hash(id);
    }
  }
}
