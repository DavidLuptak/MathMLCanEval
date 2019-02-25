package cz.muni.fi.mir.mathmlcaneval.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "application_runs")
public class ApplicationRun extends BaseEntity {

  private LocalDateTime start;
  private LocalDateTime end;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "configuration")
  private InputConfiguration inputConfiguration;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "revision")
  private Revision revision;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users")
  private User startedBy;
}
