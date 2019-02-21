package cz.muni.fi.mir.mathmlcaneval.scheduling;

import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

import cz.muni.fi.mir.mathmlcaneval.exceptions.JobAlreadyExistsException;
import cz.muni.fi.mir.mathmlcaneval.exceptions.JobCannotBeSavedException;
import cz.muni.fi.mir.mathmlcaneval.exceptions.JobDeleteFailedException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;

@Log4j2
@RequiredArgsConstructor
public abstract class AbstractJobService implements JobService {
  protected final Scheduler scheduler;

  protected abstract JobListener getJobListener();

  @Override
  public void createJob(JobDetail jobDetail, Trigger trigger) {
    createJob(jobDetail, getGroup(), trigger);
  }

  @Override
  public void createJob(JobDetail jobDetail, JobGroup jobGroup, Trigger trigger) {
    final var name = jobDetail.getKey().getName();

    log.info("Creating job {} for group {}", () -> name, () -> jobGroup);
    try {
      log.debug("Checking if job {} already exists.", () -> name);
      if (scheduler.checkExists(jobDetail.getKey())) {
        throw new JobAlreadyExistsException();
      }

      log.debug("About to schedule job {}", () -> name);
      scheduler.scheduleJob(jobDetail, trigger);
      log.debug("Job {} scheduled", () -> name);
      if (getJobListener() != null) {
        scheduler.getListenerManager()
          .addJobListener(getJobListener(), keyEquals(jobDetail.getKey()));
        log.debug("Job listener added for {}", () -> name);
      } else {
        log.debug("No job listener specified for {}", () -> name);
      }

    } catch (SchedulerException ex) {
      log.info("could not save job");
      throw new JobCannotBeSavedException();
    }
  }

  @Override
  public void deleteJob(String jobId, JobGroup jobGroup) {
    log.info("About to delete job {} for group {}", () -> jobId, () -> jobGroup);
    try {
      scheduler.deleteJob(JobKey.jobKey(jobId, jobGroup.getGroup()));
      log.info("Job {} for group {} deleted", () -> jobId, () -> jobGroup);
    } catch (SchedulerException ex) {
      log.error("Could not delete job {} for group {} of reason {}", () -> jobId, () -> jobGroup,
        ex::getLocalizedMessage);

      // todo fire event
      throw new JobDeleteFailedException();
    }
  }

  @Override
  public List<JobDescriptor> getJobs() {
    log.info("Fetching all jobs");
    List<JobDescriptor> jobs = new ArrayList<>();
    try {
      for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.anyJobGroup())) {
        jobs.add(convert(jobKey));
      }
    } catch (SchedulerException ex) {
      log.warn("Could not fetch all jobs. Because of {}", ex::getMessage);
    }

    log.info("{} jobs fetched", jobs::size);
    return jobs;
  }

  @Override
  public List<JobDescriptor> getJobsByGroup(JobGroup jobGroup) {
    List<JobDescriptor> jobs = new ArrayList<>();
    try {
      for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup.getGroup()))) {
        jobs.add(convert(jobKey));
      }
    } catch (SchedulerException ex) {
      log.warn("Could not fetch all jobs for group {}. Because of {}", () -> jobGroup,
        ex::getMessage);
    }

    log.info("{} jobs fetched for group {}", jobs::size, () -> jobGroup);
    return jobs;
  }

  @Override
  public Optional<JobDescriptor> getJobById(String jobId, JobGroup jobGroup) {
    log.info("Fetching job {} for group {}", () -> jobId, () -> jobGroup);
    try {
      return Optional.ofNullable(convert(JobKey.jobKey(jobId, jobGroup.getGroup())));
    } catch (SchedulerException ex) {
      log.warn("Failed to fetch job {} for group {}.", () -> jobId, () -> jobGroup);
    }

    log.info("Job {} for group {} not found.", () -> jobId, () -> jobGroup);
    return Optional.empty();
  }


  @SuppressWarnings("unchecked")
  private JobDescriptor convert(JobKey jobKey) throws SchedulerException {
    JobDescriptor jobDescriptor = new JobDescriptor();
    jobDescriptor.setJobId(jobKey.getName());
    jobDescriptor.setJobGroup(jobKey.getGroup());
    if (scheduler.getTriggersOfJob(jobKey) != null && !scheduler.getTriggersOfJob(jobKey)
      .isEmpty()) {
      Trigger trigger = ((List<Trigger>) scheduler.getTriggersOfJob(jobKey)).get(0);
      jobDescriptor.setLastExecutionDate(toLocalDate(trigger.getPreviousFireTime()));
      jobDescriptor.setNextExceutionDate(toLocalDate(trigger.getNextFireTime()));
    } else {
      // if job has no trigger it was never stored so job does not exist
      return null;
    }

    if (scheduler.getJobDetail(jobKey) != null) {
      jobDescriptor.setData(scheduler.getJobDetail(jobKey).getJobDataMap());
    }

    return jobDescriptor;
  }


  private LocalDateTime toLocalDate(Date date) {
    if (date == null) {
      return null;
    }
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
