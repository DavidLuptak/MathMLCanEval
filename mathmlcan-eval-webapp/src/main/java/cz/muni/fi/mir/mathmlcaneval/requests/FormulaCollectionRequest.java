package cz.muni.fi.mir.mathmlcaneval.requests;

import java.util.List;
import lombok.Data;

@Data
public class FormulaCollectionRequest {

  private String name;
  private String note;
  private Boolean visibleToPublic;

  private List<Long> formulas;
}
