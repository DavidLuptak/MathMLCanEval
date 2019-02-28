package cz.muni.fi.mir.mathmlcaneval.domain;

import javax.persistence.Column;
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
@Table(name = "canonic_outputs")
public class CanonicOutput extends BaseEntity {

  @Column(columnDefinition = "xml")
  private String xml;
  private String hash;
  private String error;
  private byte[] thumbnail;
  private boolean ocrChanged;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "formula")
  private Formula formula;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_run")
  private ApplicationRun applicationRun;
}
