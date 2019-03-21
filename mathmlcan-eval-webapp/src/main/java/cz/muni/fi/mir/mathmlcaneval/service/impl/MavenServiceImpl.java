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
package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.MavenService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

@Log4j2
@Component
@RequiredArgsConstructor
public class MavenServiceImpl implements MavenService {

  private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  private static final XPathFactory xPathfactory = XPathFactory.newInstance();
  private static final Properties properties = new Properties();
  private static final List<String> MAVEN_GOALS = List.of("clean", "package", "-B");
  // cannot be static see squid:S2885
  private final XPath xpath = xPathfactory.newXPath();

  private final Invoker invoker;

  @PostConstruct
  public void init() {
    properties.setProperty("maven.javadoc.skip", "true");
  }


  @Override
  public InputStream invokeMavenBuild(Path revision) {
    final var pom = revision.resolve("pom.xml");
    try {
      return runLockedMaven(maven -> {
        final var request = new DefaultInvocationRequest();
        request.setPomFile(pom.toFile());
        request.setGoals(MAVEN_GOALS);
        request.setProperties(properties);

        log.info("Maven Goals, properties were set for build process for revision {}",
          () -> revision);

        return invoker.execute(request);
      }, pom);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }


  private String version(Path pom) throws Exception {
    log.trace("Finding version for pom file {}", () -> pom);
    final var doc = factory.newDocumentBuilder().parse(pom.toFile());
    final var expr = xpath.compile("/project/version");

    final var nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

    return nl.item(0).getFirstChild().getNodeValue();
  }

  private InputStream runLockedMaven(final MavenFunction mavenFunction, Path pom)
    throws IOException {
    //FileLock lock = null;
    try (FileChannel channel = FileChannel.open(pom)) {
      log.info("Channel lock acquired for maven installation for {}", () -> pom);
      //lock = channel.lock();
      final var result = mavenFunction.runMaven(this.invoker);
      log.debug("Maven invoked checking result code");
      if (result.getExitCode() != 0) {
        log.warn("Maven has failed");
        throw new RuntimeException("maven has failed");
      } else {
        log.info("Maven build for pom {} has been successful", () -> pom);
        try {
          return Files
            .newInputStream(pom.getParent()
              .resolve("target")
              .resolve(String
                .format("mathml-canonicalizer-%s-jar-with-dependencies.jar", version(pom))));

        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    } catch (MavenInvocationException ex) {
      System.err.println(ex);

      throw new RuntimeException(ex);
    } finally {
      /*if (lock != null) {
        lock.release();
      }*/
      log.info("done");
    }
  }

  @FunctionalInterface
  interface MavenFunction {

    InvocationResult runMaven(Invoker invoker) throws MavenInvocationException;
  }
}
