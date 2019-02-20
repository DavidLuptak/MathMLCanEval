package cz.muni.fi.mir.mathmlcaneval.configurations;

import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;

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
