package cz.muni.fi.mir.mathmlcaneval.service;

import java.util.List;

public interface CanonicalizerService {

  void fireCanonicalizer(String revision, Long configurationId, List<Long> formulas);

  void fireCanonicalizer(String revision, Long configurationId, Long collectionId);
}
