package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.scheduling.JobService;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobDescriptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/running-tasks")
@RequiredArgsConstructor
public class RunningJobsResource {

  private final JobService jobService;


  @GetMapping
  public List<JobDescriptor> tasks() {
    return jobService.getJobs();
  }
}
