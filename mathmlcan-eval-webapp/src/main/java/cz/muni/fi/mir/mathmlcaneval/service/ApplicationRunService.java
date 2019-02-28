package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.requests.CanonicalizationRequest;

public interface ApplicationRunService {
  String save(CanonicalizationRequest request);
}
