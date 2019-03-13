package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.domain.SimilarityForm;
import cz.muni.fi.mir.mathmlcaneval.service.SimilarityService;
import cz.muni.fi.mir.mathmlcaneval.support.Elements;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
@RequiredArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {

  private final DocumentBuilder builder;


  @Override
  public SimilarityForm generateSimilarity(CanonicOutput canonicOutput) {

    SimilarityForm result = new SimilarityForm();

    Document doc;
    try{
      doc = builder.parse(new InputSource(new StringReader(canonicOutput.getXml())));
    } catch (SAXException | IOException ex) {
      throw new RuntimeException(ex);
    }

    Forms forms = new Forms();

    countTravel(doc.getDocumentElement(), forms);

    result.setVectorForm(toVector(forms.getMap()).toNormal().asDoubleArray());
    result.setTextForm(forms.getStrings().toString());

    return result;
  }


  private AVector toVector(Map<String, Integer> map) {
    double[] result = new double[Elements.values().length];

    map.forEach((k, v) -> result[Elements.convert(k).getPosition()] = v);

    return Vectorz.create(result);
  }


  private void countTravel(Node node, Forms forms) {
    if (node == null) {
      return;
    }

    NodeList nodeList = node.getChildNodes();

    for (int i = 0; i < nodeList.getLength(); i++) {
      Node currentNode = nodeList.item(i);
      if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
        forms.pushElement(currentNode.getNodeName());
      } else if (currentNode.getNodeType() == Node.TEXT_NODE) {
        forms.pushText(currentNode.getTextContent());
      }

      countTravel(currentNode, forms);
    }
  }


  @Getter
  static class Forms {

    private Map<String, Integer> map = new HashMap<>();
    private List<String> strings = new ArrayList<>();

    public void pushElement(String element) {
      map.merge(element, 1, (a, b) -> a + 1);
    }

    public void pushText(String text) {
      String trimmed = text.trim();
      if (!trimmed.isEmpty()) {
        this.strings.add(text);
      }
    }
  }
}
