package cz.muni.fi.mir.mathmlcaneval.scheduling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobGroup {
  DEFAULT("job.group.default"),
  SYNC("job.group.sync"),
  RUN("job.group.run");

  private final String group;
}
