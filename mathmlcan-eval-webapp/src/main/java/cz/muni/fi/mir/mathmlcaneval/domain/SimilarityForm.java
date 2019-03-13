package cz.muni.fi.mir.mathmlcaneval.domain;

import cz.muni.fi.mir.mathmlcaneval.support.CustomDoubleArrayType;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

@Getter
@Setter
@Entity
@TypeDef(name = "double-array", typeClass = CustomDoubleArrayType.class, defaultForType = double[].class)
@Table(name = "canonic_outputs_similarity_forms")
public class SimilarityForm extends BaseEntity {

  private double[] vectorForm;
  private String textForm;
}
