package cz.muni.fi.mir.mathmlcaneval.handlers.impl;

import cz.muni.fi.mir.mathmlcaneval.events.SyncRevisionEvent;
import cz.muni.fi.mir.mathmlcaneval.handlers.RevisionHandler;
import cz.muni.fi.mir.mathmlcaneval.scheduling.JobService;
import cz.muni.fi.mir.mathmlcaneval.scheduling.jobs.RevisionSyncJob;
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
public class RevisionHandlerImpl implements RevisionHandler {
  private final JobService jobService;

  @Override
  public void handleRevisionSync(SyncRevisionEvent syncRevisionEvent) {
    Trigger trigger = TriggerBuilder.newTrigger()
      .withIdentity(UUID.randomUUID().toString(), JobGroup.SYNC.getGroup())
      .startNow().build();

    JobDetail job = CustomJobBuilder.builder(RevisionSyncJob.class)
      .group(JobGroup.SYNC)
      .build();

    jobService.createJob(job, JobGroup.SYNC, trigger);

    //return job.getKey().getName();
  }
}
