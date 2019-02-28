package cz.muni.fi.mir.mathmlcaneval.handlers.impl;

import cz.muni.fi.mir.mathmlcaneval.events.CanonicalizationEvent;
import cz.muni.fi.mir.mathmlcaneval.handlers.CanonicalizationHandler;
import cz.muni.fi.mir.mathmlcaneval.scheduling.JobService;
import cz.muni.fi.mir.mathmlcaneval.scheduling.jobs.CanonicalizationJob;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.CustomJobBuilder;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobGroup;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CanonicalizationHandlerImpl implements CanonicalizationHandler {
  private final JobService jobService;

  @Override
  public void handleCanonicalization(CanonicalizationEvent canonicalizationEvent) {
    // todo verify input if collection is our, if config is ours etc..
    Trigger trigger = TriggerBuilder
      .newTrigger().withIdentity(UUID.randomUUID().toString(), JobGroup.RUN.getGroup())
      .startNow().build();

    JobDetail job = CustomJobBuilder.builder(CanonicalizationJob.class)
      .group(JobGroup.RUN)
      .data(CanonicalizationJob.COLLECTION, canonicalizationEvent.getCollectionId())
      .data(CanonicalizationJob.CONFIG, canonicalizationEvent.getConfigurationId())
      .data(CanonicalizationJob.REVISION, canonicalizationEvent.getRevisionId())
      .build();

    jobService.createJob(job, JobGroup.RUN, trigger);

    //return job.getKey().getName();
  }
}
