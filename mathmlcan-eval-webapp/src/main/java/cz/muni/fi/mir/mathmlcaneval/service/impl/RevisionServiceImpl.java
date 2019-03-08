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

import com.github.fge.jsonpatch.JsonPatch;
import cz.muni.fi.mir.mathmlcaneval.domain.Revision;
import cz.muni.fi.mir.mathmlcaneval.mappers.RevisionMapper;
import cz.muni.fi.mir.mathmlcaneval.repository.RevisionRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.SyncRevisionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.RevisionResponse;
import cz.muni.fi.mir.mathmlcaneval.service.PatchingService;
import cz.muni.fi.mir.mathmlcaneval.service.RevisionService;
import cz.muni.fi.mir.mathmlcaneval.support.ReadOnly;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RevisionServiceImpl implements RevisionService {

  private final RevisionRepository revisionRepository;
  private final RevisionMapper revisionMapper;
  private final PatchingService patchingService;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  @ReadOnly
  public List<RevisionResponse> findAll() {
    return revisionMapper.map(revisionRepository.findAll());
  }

  @Override
  public void syncRevisions(SyncRevisionRequest request) {
    this.applicationEventPublisher.publishEvent(revisionMapper.map(request));
    //jobServiceDecorator.scheduleRevisionTask(request);
  }

  @Override
  @ReadOnly
  public Optional<RevisionResponse> findById(Long id) {
    return revisionRepository
      .findById(id)
      .map(revisionMapper::map);
  }

  @Override
  @Transactional
  public RevisionResponse update(Long id, JsonPatch patch) {
    // todo this can be done better

    Revision revision = revisionRepository
      .findById(id)
      .orElseThrow();

    Revision result = patchingService.patch(patch, revision, Revision.class);


    return revisionMapper.map(revisionRepository.save(result));
  }
}
