package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.support.XmlContent;
import org.w3c.dom.Document;

public interface XmlDocumentService {
  Document buildDocument(XmlContent xmlContent);
  Document prettyPrint(Document original);
  String prettyPrintToString(Document original);

}
