package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.requests.SyncRevisionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.RevisionResponse;
import java.util.List;

public interface RevisionService {

  List<RevisionResponse> findAll();

  void syncRevisions(SyncRevisionRequest request);
}
