package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.domain.SimilarityForm;

public interface SimilarityService {

  SimilarityForm generateSimilarity(CanonicOutput canonicOutput);
}
