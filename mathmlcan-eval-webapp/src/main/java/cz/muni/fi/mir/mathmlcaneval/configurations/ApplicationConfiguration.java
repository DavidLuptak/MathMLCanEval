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
import cz.muni.fi.mir.mathmlcaneval.service.XmlDocumentService;
import cz.muni.fi.mir.mathmlcaneval.service.impl.XmlDocumentServiceImpl;
import cz.muni.fi.mir.mathmlcaneval.support.MavenInvokerOutputHandler;
import java.net.URISyntaxException;
import javax.cache.Caching;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
@EnableCaching
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

  @Bean
  @SneakyThrows
  public DocumentBuilder documentBuilder() {
    final var factory = DocumentBuilderFactory.newInstance();
    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    return factory.newDocumentBuilder();
  }

  @Bean
  @SneakyThrows
  public Transformer transformer() {
    final var factory = TransformerFactory.newInstance();
    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

    final var transformer = factory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");


    return transformer;
  }

  @Bean
  @SneakyThrows
  public XmlDocumentService xmlDocumentService(DocumentBuilder db, Transformer tf) {
    final var xpath = XPathFactory.newInstance()
      .newXPath().compile("//text()[normalize-space(.) = '']");

    return new XmlDocumentServiceImpl(db, tf, xpath);
  }

  @Bean
  @Profile("!ci")
  public CacheManager cacheManager() throws URISyntaxException {
    return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(
      getClass().getResource("/ehcache.xml").toURI(),
      getClass().getClassLoader()
    ));
  }
}
