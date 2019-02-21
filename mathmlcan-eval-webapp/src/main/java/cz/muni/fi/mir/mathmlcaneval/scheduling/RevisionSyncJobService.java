package cz.muni.fi.mir.mathmlcaneval.scheduling;

import cz.muni.fi.mir.mathmlcaneval.requests.SyncRevisionRequest;
import java.util.UUID;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RevisionSyncJobService extends AbstractJobService {

  public RevisionSyncJobService(Scheduler scheduler) {
    super(scheduler);
  }

  public void scheduleTask(SyncRevisionRequest request) {
    Trigger trigger = TriggerBuilder.newTrigger()
      .withIdentity(UUID.randomUUID().toString(), getGroup().getGroup())
      .startNow().build();

    JobDetail job = CustomJobBuilder.builder(RevisionSyncJob.class)
      .group(getGroup())
      .build();

    createJob(job, trigger);
  }

  @Override
  protected JobListener getJobListener() {
    return null;
  }

  @Override
  public JobGroup getGroup() {
    return JobGroup.SYNC;
  }
}
