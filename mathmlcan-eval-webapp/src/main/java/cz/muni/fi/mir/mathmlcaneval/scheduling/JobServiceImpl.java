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
package cz.muni.fi.mir.mathmlcaneval.scheduling;

import cz.muni.fi.mir.mathmlcaneval.exceptions.JobAlreadyExistsException;
import cz.muni.fi.mir.mathmlcaneval.exceptions.JobCannotBeSavedException;
import cz.muni.fi.mir.mathmlcaneval.exceptions.JobDeleteFailedException;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobDescriptor;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobGroup;
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
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

  private final Scheduler scheduler;

  //protected JobListener getJobListener();



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
/*      if (getJobListener() != null) {
        scheduler.getListenerManager()
          .addJobListener(getJobListener(), keyEquals(jobDetail.getKey()));
        log.debug("Job listener added for {}", () -> name);
      } else {
        log.debug("No job listener specified for {}", () -> name);
      }*/

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
    final var jobs = new ArrayList<JobDescriptor>();
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
    final var jobs = new ArrayList<JobDescriptor>();
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
    final var jobDescriptor = new JobDescriptor();
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
