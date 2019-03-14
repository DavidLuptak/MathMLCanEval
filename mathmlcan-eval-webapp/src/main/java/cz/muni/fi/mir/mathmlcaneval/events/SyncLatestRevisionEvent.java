package cz.muni.fi.mir.mathmlcaneval.events;

import org.springframework.context.ApplicationEvent;

public class SyncLatestRevisionEvent extends ApplicationEvent {

  public SyncLatestRevisionEvent(Object source) {
    super(source);
  }
}
