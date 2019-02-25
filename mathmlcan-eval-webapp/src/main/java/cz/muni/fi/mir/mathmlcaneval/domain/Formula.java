package cz.muni.fi.mir.mathmlcaneval.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formulas")
public class Formula extends BaseEntity {
  @Column(columnDefinition = "xml")
  private String xml;
  private String note;
  private String hashValue;
  private LocalDateTime insertTime;
  private byte[] thumbnail;

  @OneToMany(mappedBy = "formula")
  private Set<CanonicOutput> canonicOutputs = new HashSet<>();
}
