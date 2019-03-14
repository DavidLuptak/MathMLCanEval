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
package cz.muni.fi.mir.mathmlcaneval.handlers.impl;

import cz.muni.fi.mir.mathmlcaneval.events.SyncLatestRevisionEvent;
import cz.muni.fi.mir.mathmlcaneval.events.SyncRevisionEvent;
import cz.muni.fi.mir.mathmlcaneval.handlers.RevisionHandler;
import cz.muni.fi.mir.mathmlcaneval.scheduling.JobService;
import cz.muni.fi.mir.mathmlcaneval.scheduling.jobs.LatestRevisionSyncJob;
import cz.muni.fi.mir.mathmlcaneval.scheduling.jobs.RevisionSyncJob;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.CustomJobBuilder;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevisionHandlerImpl implements RevisionHandler {

  private final JobService jobService;

  @Override
  public void handleRevisionSync(SyncRevisionEvent syncRevisionEvent) {
    final var trigger = defaultTrigger(JobGroup.SYNC);

    final var job = CustomJobBuilder.builder(RevisionSyncJob.class)
      .data(RevisionSyncJob.DATE_FROM, syncRevisionEvent.getFrom())
      .data(RevisionSyncJob.DATE_TO, syncRevisionEvent.getTo())
      .data(RevisionSyncJob.REVISION, syncRevisionEvent.getSha1())
      .group(JobGroup.SYNC)
      .build();

    jobService.createJob(job, JobGroup.SYNC, trigger);

    //return job.getKey().getName();
  }

  @Override
  public void handleLatestRevisionSync(SyncLatestRevisionEvent event) {
    final var trigger = defaultTrigger(JobGroup.SYNC);

    final var job = CustomJobBuilder
      .builder(LatestRevisionSyncJob.class)
      .group(JobGroup.SYNC)
      .build();

    jobService.createJob(job, JobGroup.SYNC, trigger);
  }
}
