package cz.muni.fi.mir.mathmlcaneval.requests;

import java.util.List;
import lombok.Data;

@Data
public class CanonicalizationRequest {
  private Long configurationId;
  private Long collectionId;
  private Long revisionId;
  private List<Long> formulas;
}
