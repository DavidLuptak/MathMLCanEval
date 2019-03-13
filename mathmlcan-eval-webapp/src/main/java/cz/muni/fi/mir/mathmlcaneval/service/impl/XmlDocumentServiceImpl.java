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

import cz.muni.fi.mir.mathmlcaneval.service.XmlDocumentService;
import cz.muni.fi.mir.mathmlcaneval.support.XmlContent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility service that converts xml document represented by {@link XmlContent}
 *
 * @author dominik.szalai
 * @since 2.0.0
 */
@Log4j2
@RequiredArgsConstructor
public class XmlDocumentServiceImpl implements XmlDocumentService {

  private final DocumentBuilder documentBuilder;
  private final Transformer transformer;
  private final XPathExpression normalizeDocument;

  @Override
  public Document buildDocument(XmlContent xmlContent) {
    try (InputStream is = new ByteArrayInputStream(xmlContent.getXmlContent().getBytes())) {
      return documentBuilder.parse(is);
    } catch (IOException | SAXException ex) {
      throw new RuntimeException(ex);
    }
  }


  @Override
  public Document prettyPrint(Document original) {
    DOMResult result = new DOMResult();
    prettyPrint(original, result);

    return (Document) result.getNode();
  }

  @Override
  public String prettyPrintToString(Document original) {
    StringWriter writer = new StringWriter();
    StreamResult result = new StreamResult(writer);
    prettyPrint(original, result);

    return writer.toString();
  }

  /**
   * Method converts given document using transformer into result
   *
   * @param document input document which should be pretty printed
   * @param result where pretty format is stored
   */
  private void prettyPrint(Document document, Result result) {
    // transformer in jdk 8+? behaves little bit weird as it inserts empty lines.
    // we need to copy original document and remove empty lines (nodes)

    Document copy = documentBuilder.newDocument();
    Node copiedNode = copy.importNode(document.getDocumentElement(), true);
    copy.appendChild(copiedNode);

    try {
      NodeList blankTextNodes = (NodeList) normalizeDocument.evaluate(copy, XPathConstants.NODESET);

      for (int i = 0; i < blankTextNodes.getLength(); i++) {
        blankTextNodes.item(i).getParentNode().removeChild(blankTextNodes.item(i));
      }

      transformer.transform(new DOMSource(copy), result);
    } catch (TransformerException | XPathExpressionException e) {
      throw new RuntimeException();
    }
  }
}
