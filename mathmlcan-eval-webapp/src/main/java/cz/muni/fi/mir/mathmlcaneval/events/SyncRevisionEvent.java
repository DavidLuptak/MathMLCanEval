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
package cz.muni.fi.mir.mathmlcaneval.events;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SyncRevisionEvent extends ApplicationEvent {
  private final LocalDateTime from;
  private final LocalDateTime to;
  private final String sha1;

  public SyncRevisionEvent(Object source, LocalDateTime from, LocalDateTime to, String sha1) {
    super(source);
    this.from = from;
    this.to = to;
    this.sha1 = sha1;
  }
}
