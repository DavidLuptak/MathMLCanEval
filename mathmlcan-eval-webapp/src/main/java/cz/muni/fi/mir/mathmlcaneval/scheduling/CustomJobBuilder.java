/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.scheduling;

import static org.quartz.JobBuilder.newJob;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomJobBuilder {

  private final Class<? extends Job> type;

  private Map<String, Object> data;
  private String name;
  private JobGroup group;


  public static CustomJobBuilder builder(Class<? extends Job> type) {
    return new CustomJobBuilder(type);
  }

  public CustomJobBuilder data(Map<String, Object> data) {
    this.data = data;
    return this;
  }

  public CustomJobBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CustomJobBuilder group(JobGroup group) {
    this.group = group;
    return this;
  }

  public JobDetail build() {
    if (this.group == null) {
      throw new IllegalArgumentException();
    }

    return newJob(type)
      .withIdentity(nameOrDefault(), this.group.getGroup())
      .usingJobData(new JobDataMap(data()))
      .build();
  }

  private String nameOrDefault() {
    return this.name == null ? UUID.randomUUID().toString() : this.name;
  }

  private Map<String, Object> data() {
    return this.data == null ? new HashMap<>() : this.data;
  }

}
