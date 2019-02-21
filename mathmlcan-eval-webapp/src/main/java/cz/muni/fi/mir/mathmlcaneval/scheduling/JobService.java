package cz.muni.fi.mir.mathmlcaneval.scheduling;

import java.util.List;
import java.util.Optional;
import org.quartz.JobDetail;
import org.quartz.Trigger;

public interface JobService {

  void createJob(JobDetail jobDetail, Trigger trigger);

  void createJob(JobDetail jobDetail, JobGroup jobGroup, Trigger trigger);


  /**
   * Method deletes job with requested id and in the requested group from execution
   *
   * @param jobId job identifier
   * @param jobGroup group where job belongs
   */
  void deleteJob(String jobId, JobGroup jobGroup);

  /**
   * Method fetches list of all jobs inside scheduler
   *
   * @return list of all jobs
   */
  List<JobDescriptor> getJobs();

  /**
   * Method fetches list of all jobs inside scheduler that belong to same group
   *
   * @param jobGroup group which filters the jobs
   * @return list of jobs filtered by group
   */
  List<JobDescriptor> getJobsByGroup(JobGroup jobGroup);

  /**
   * Method tries to fetch job by its id and group
   *
   * @param jobId of job
   * @param jobGroup where job belongs
   * @return job fetched according to requested params, empty optional if there is none
   */
  Optional<JobDescriptor> getJobById(String jobId, JobGroup jobGroup);

  /**
   * Method used to determine group to which job belongs
   *
   * @return group
   */
  JobGroup getGroup();
}
