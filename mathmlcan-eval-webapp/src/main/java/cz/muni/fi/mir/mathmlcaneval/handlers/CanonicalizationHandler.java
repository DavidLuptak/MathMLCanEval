package cz.muni.fi.mir.mathmlcaneval.handlers;

import cz.muni.fi.mir.mathmlcaneval.events.CanonicalizationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

public interface CanonicalizationHandler {

  @TransactionalEventListener
  @Async
  void handleCanonicalization(CanonicalizationEvent canonicalizationEvent);
}
