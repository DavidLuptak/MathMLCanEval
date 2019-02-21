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
package cz.muni.fi.mir.mathmlcaneval.configurations;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;

/**
 * @author dominik.szalai
 * @since 2.0.0
 */
@Configuration
public class ApplicationConfiguration {

  @Bean
  public AfterburnerModule afterburnerModule() {
    // see https://github.com/FasterXML/jackson-modules-base/issues/37
    final var am = new AfterburnerModule();
    am.setUseValueClassLoader(true);

    return am;
  }

  @Bean
  public ProblemModule problemModule() {
    return new ProblemModule().withStackTraces();
  }
}
