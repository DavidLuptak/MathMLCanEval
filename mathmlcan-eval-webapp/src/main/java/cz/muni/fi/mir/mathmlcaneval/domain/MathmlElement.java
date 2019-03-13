package cz.muni.fi.mir.mathmlcaneval.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mathml_elements")
public class MathmlElement extends BaseEntity{

  @Column
  private  String element;
  @Column
  private int vectorPosition;
}
