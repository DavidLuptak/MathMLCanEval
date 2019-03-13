package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.domain.SimilarityForm;
import cz.muni.fi.mir.mathmlcaneval.repository.MathmlElementRepository;
import cz.muni.fi.mir.mathmlcaneval.service.SimilarityService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
@RequiredArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {
  private final MathmlElementRepository mathmlElementRepository;


  @Override
  public SimilarityForm generateSimilarity(Document document) {

    SimilarityForm result = new SimilarityForm();

    Forms forms = new Forms();

    countTravel(document.getDocumentElement(), forms);

    result.setVectorForm(toVector(forms.getMap()).toNormal().asDoubleArray());
    result.setTextForm(forms.getStrings().toString());

    return result;
  }


  private AVector toVector(Map<String, Integer> map) {
    double[] result = new double[(int) mathmlElementRepository.count()];

    map.forEach((k, v) -> result[mathmlElementRepository.getPositionForElement(k).orElseThrow()] = v);

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

    void pushElement(String element) {
      map.merge(element, 1, (a, b) -> a + 1);
    }

    void pushText(String text) {
      String trimmed = text.trim();
      if (!trimmed.isEmpty()) {
        this.strings.add(text);
      }
    }
  }
}
