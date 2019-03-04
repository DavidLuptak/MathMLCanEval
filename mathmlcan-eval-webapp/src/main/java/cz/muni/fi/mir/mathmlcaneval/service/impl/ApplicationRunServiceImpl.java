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

import cz.muni.fi.mir.mathmlcaneval.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlcaneval.domain.User;
import cz.muni.fi.mir.mathmlcaneval.events.CanonicalizationEvent;
import cz.muni.fi.mir.mathmlcaneval.repository.ApplicationRunRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.InputConfigurationRepository;
import cz.muni.fi.mir.mathmlcaneval.repository.RevisionRepository;
import cz.muni.fi.mir.mathmlcaneval.requests.CanonicalizationRequest;
import cz.muni.fi.mir.mathmlcaneval.service.ApplicationRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ApplicationRunServiceImpl implements ApplicationRunService {
  private final ApplicationRunRepository applicationRunRepository;
  private final RevisionRepository revisionRepository;
  private final InputConfigurationRepository inputConfigurationRepository;

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  @Transactional
  public String save(CanonicalizationRequest request) {
    ApplicationRun run = new ApplicationRun();
    run.setInputConfiguration(inputConfigurationRepository.findById(request.getConfigurationId()).orElseThrow());
    run.setRevision(revisionRepository.findById(request.getRevisionId()).orElseThrow());
    run.setStartedBy(new User(2L));

    applicationRunRepository.save(run);

    // todo verify if collection id exists
    this.applicationEventPublisher.publishEvent(new CanonicalizationEvent(this,run.getId(), request.getCollectionId(),
      request.getPostProcessors()));

    return run.getId().toString();

  }
}
