package cz.muni.fi.mir.mathmlcaneval.handlers;

import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobGroup;
import java.util.UUID;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public interface JobHandler {

  default Trigger defaultTrigger(JobGroup group) {
    return TriggerBuilder.newTrigger()
      .withIdentity(UUID.randomUUID().toString(), group.getGroup())
      .startNow().build();
  }
}
