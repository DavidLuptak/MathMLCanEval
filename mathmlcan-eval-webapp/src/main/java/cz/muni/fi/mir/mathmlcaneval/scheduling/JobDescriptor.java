package cz.muni.fi.mir.mathmlcaneval.scheduling;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class JobDescriptor {

  private String jobId;
  private String jobGroup;
  private LocalDateTime lastExecutionDate;
  private LocalDateTime createdDate;
  private LocalDateTime nextExceutionDate;
  private Map<String, Object> data;
}
