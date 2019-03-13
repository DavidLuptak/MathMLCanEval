package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.scheduling.JobService;
import cz.muni.fi.mir.mathmlcaneval.scheduling.support.JobDescriptor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/running-tasks")
@RequiredArgsConstructor
public class RunningJobsResource {

  private final JobService jobService;


  @GetMapping
  public Page<JobDescriptor> tasks() {
    return new PageImpl<>(jobService.getJobs());
  }
}
