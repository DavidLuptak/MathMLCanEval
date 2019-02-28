package cz.muni.fi.mir.mathmlcaneval.events;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SyncRevisionEvent extends ApplicationEvent {
  private final LocalDateTime from;
  private final LocalDateTime to;
  private final String sha1;

  public SyncRevisionEvent(Object source, LocalDateTime from, LocalDateTime to, String sha1) {
    super(source);
    this.from = from;
    this.to = to;
    this.sha1 = sha1;
  }
}
