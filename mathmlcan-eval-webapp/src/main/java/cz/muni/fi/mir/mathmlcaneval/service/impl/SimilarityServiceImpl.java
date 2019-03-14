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

@Service
@RequiredArgsConstructor
public class SimilarityServiceImpl implements SimilarityService {
  private final MathmlElementRepository mathmlElementRepository;


  @Override
  public SimilarityForm generateSimilarity(Document document) {
    final var result = new SimilarityForm();
    final var forms = new Forms();

    countTravel(document.getDocumentElement(), forms);

    result.setVectorForm(toVector(forms.getMap()).toNormal().asDoubleArray());
    result.setTextForm(forms.getStrings().toString());

    return result;
  }


  private AVector toVector(Map<String, Integer> map) {
    final var result = new double[(int) mathmlElementRepository.count()];

    map.forEach((k, v) -> result[mathmlElementRepository.getPositionForElement(k).orElseThrow()] = v);

    return Vectorz.create(result);
  }


  private void countTravel(Node node, Forms forms) {
    if (node == null) {
      return;
    }

    final var nodeList = node.getChildNodes();

    for (int i = 0; i < nodeList.getLength(); i++) {
      final var currentNode = nodeList.item(i);
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
      final var trimmed = text.trim();
      if (!trimmed.isEmpty()) {
        this.strings.add(text);
      }
    }
  }
}
