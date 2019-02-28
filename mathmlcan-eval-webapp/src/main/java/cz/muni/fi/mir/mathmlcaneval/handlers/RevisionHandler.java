package cz.muni.fi.mir.mathmlcaneval.handlers;

import cz.muni.fi.mir.mathmlcaneval.events.SyncRevisionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface RevisionHandler {

  @EventListener
  @Async
  void handleRevisionSync(SyncRevisionEvent syncRevisionEvent);
}
