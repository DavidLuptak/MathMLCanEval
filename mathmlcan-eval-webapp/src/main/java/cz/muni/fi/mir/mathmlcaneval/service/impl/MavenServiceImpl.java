package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.MavenService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import lombok.extern.log4j.Log4j2;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

@Log4j2
@Component
public class MavenServiceImpl implements MavenService {

  private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  private static final XPathFactory xPathfactory = XPathFactory.newInstance();
  private static final XPath xpath = xPathfactory.newXPath();
  private static final Properties properties = new Properties();
  private static final String M2_HOME = "C:\\Program Files\\JetBrains\\IntelliJ IDEA 191.5109.14\\plugins\\maven\\lib\\maven3"; //todo

  @PostConstruct
  public void init() {
    properties.setProperty("maven.javadoc.skip", "true");
  }


  @Override
  public InputStream invokeMavenBuild(Path revision) {
    final var pom = revision.resolve("pom.xml");
    final var request = new DefaultInvocationRequest();
    request.setPomFile(pom.toFile());
    request.setGoals(List.of("clean", "package"));
    request.setProperties(properties);

    try {
      final var invoker = new DefaultInvoker();
      invoker.setMavenHome(new File(M2_HOME));
      invoker.setOutputHandler(new InvocationOutputHandler() {
        @Override
        public void consumeLine(String s) throws IOException {
          log.trace(s);
        }
      });
      invoker.execute(request);
    } catch (MavenInvocationException ex) {
      throw new RuntimeException(ex);
    }

    try {
      return Files
        .newInputStream(revision
          .resolve("target")
          .resolve(String
            .format("mathml-canonicalizer-%s-jar-with-dependencies.jar", version(pom))));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  private String version(Path pom) throws Exception {
    final var doc = factory.newDocumentBuilder().parse(pom.toFile());
    final var expr = xpath.compile("/project/version");

    final var nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

    return nl.item(0).getFirstChild().getNodeValue();
  }
}
