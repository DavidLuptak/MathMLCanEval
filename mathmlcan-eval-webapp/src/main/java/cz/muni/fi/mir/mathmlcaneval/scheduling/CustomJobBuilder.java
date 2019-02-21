package cz.muni.fi.mir.mathmlcaneval.scheduling;

import static org.quartz.JobBuilder.newJob;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomJobBuilder {

  private final Class<? extends Job> type;

  private Map<String, Object> data;
  private String name;
  private JobGroup group;


  public static CustomJobBuilder builder(Class<? extends Job> type) {
    return new CustomJobBuilder(type);
  }

  public CustomJobBuilder data(Map<String, Object> data) {
    this.data = data;
    return this;
  }

  public CustomJobBuilder name(String name) {
    this.name = name;
    return this;
  }

  public CustomJobBuilder group(JobGroup group) {
    this.group = group;
    return this;
  }

  public JobDetail build() {
    if (this.group == null) {
      throw new IllegalArgumentException();
    }

    return newJob(type)
      .withIdentity(nameOrDefault(), this.group.getGroup())
      .usingJobData(new JobDataMap(data()))
      .build();
  }

  private String nameOrDefault() {
    return this.name == null ? UUID.randomUUID().toString() : this.name;
  }

  private Map<String, Object> data() {
    return this.data == null ? new HashMap<>() : this.data;
  }

}
