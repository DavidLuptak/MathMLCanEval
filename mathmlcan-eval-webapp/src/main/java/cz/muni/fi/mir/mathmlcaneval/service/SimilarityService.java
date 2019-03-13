package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.domain.SimilarityForm;
import org.w3c.dom.Document;

public interface SimilarityService {

  SimilarityForm generateSimilarity(Document document);
}
