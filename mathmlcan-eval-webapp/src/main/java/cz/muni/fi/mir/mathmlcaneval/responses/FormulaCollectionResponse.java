package cz.muni.fi.mir.mathmlcaneval.responses;

import lombok.Data;

@Data
public class FormulaCollectionResponse {
  private Long id;
  private String name;
  private String note;
  private Boolean visibleToPublic;
  private Long creatorId;
}
