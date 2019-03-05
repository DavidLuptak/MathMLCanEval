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
package cz.muni.fi.mir.mathmlcaneval.configurations;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import cz.muni.fi.mir.mathmlcaneval.configurations.props.LocationProperties;
import cz.muni.fi.mir.mathmlcaneval.support.MavenInvokerOutputHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.zalando.problem.ProblemModule;

/**
 * @author dominik.szalai
 * @since 2.0.0
 */
@Log4j2
@Configuration
@EnableAsync
@RequiredArgsConstructor
@EnableJpaRepositories(bootstrapMode = BootstrapMode.LAZY, enableDefaultTransactions = false, basePackages = "cz.muni.fi.mir.mathmlcaneval.repository")
public class ApplicationConfiguration {
  private final LocationProperties locationProperties;

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

  @Bean
  public Invoker invoker() {
    final var invoker = new DefaultInvoker();
    invoker.setMavenHome(locationProperties.getM2Home().toFile());
    invoker.setOutputHandler(new MavenInvokerOutputHandler(Level.TRACE));

    return invoker;
  }
}
