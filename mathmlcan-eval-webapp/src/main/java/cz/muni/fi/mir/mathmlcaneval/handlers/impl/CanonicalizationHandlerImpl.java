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

import cz.muni.fi.mir.mathmlcaneval.events.CanonicalizationEvent;
import cz.muni.fi.mir.mathmlcaneval.handlers.CanonicalizationHandler;
import cz.muni.fi.mir.mathmlcaneval.scheduling.JobService;
import cz.muni.fi.mir.mathmlcaneval.scheduling.jobs.CanonicalizationJob;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.CustomJobBuilder;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobGroup;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CanonicalizationHandlerImpl implements CanonicalizationHandler {
  private final JobService jobService;

  @Override
  public void handleCanonicalization(CanonicalizationEvent canonicalizationEvent) {
    // todo verify input if collection is our, if config is ours etc..
    Trigger trigger = defaultTrigger(JobGroup.RUN);

    JobDetail job = CustomJobBuilder.builder(CanonicalizationJob.class)
      .group(JobGroup.RUN)
      .data(CanonicalizationJob.COLLECTION, canonicalizationEvent.getCollectionId())
      .data(CanonicalizationJob.APP_RUN, canonicalizationEvent.getAppRunId())
      .build();

    jobService.createJob(job, JobGroup.RUN, trigger);

    //return job.getKey().getName();
  }
}
