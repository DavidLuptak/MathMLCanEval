package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.mappers.RevisionMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.RevisionRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.SyncRevisionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.RevisionResponse;
import cz.muni.fi.mir.mathmlcaneval.scheduling.RevisionSyncJobService;
import cz.muni.fi.mir.mathmlcaneval.service.RevisionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevisionServiceImpl implements RevisionService {

  private final RevisionRepository revisionRepository;
  private final RevisionMapper revisionMapper;
  private final RevisionSyncJobService revisionSyncJobService;

  @Override
  public List<RevisionResponse> findAll() {
    return revisionMapper.map(revisionRepository.findAll());
  }

  @Override
  public void syncRevisions(SyncRevisionRequest request) {
    revisionSyncJobService.scheduleTask(request);
  }
}
