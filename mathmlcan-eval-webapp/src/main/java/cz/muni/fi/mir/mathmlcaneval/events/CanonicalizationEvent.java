package cz.muni.fi.mir.mathmlcaneval.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CanonicalizationEvent extends ApplicationEvent {
  private final Long appRunId;
  private final Long configurationId;
  private final Long revisionId;
  private final Long collectionId;

  public CanonicalizationEvent(Object source, Long appRunId, Long configurationId,
    Long revisionId, Long collectionId) {
    super(source);
    this.appRunId = appRunId;
    this.configurationId = configurationId;
    this.revisionId = revisionId;
    this.collectionId = collectionId;
  }
}
