package cz.muni.fi.mir.mathmlcaneval.responses;

import java.util.Set;
import lombok.Data;

@Data
public class ApplicationRunDetailedResponse extends ApplicationRunResponse {

  private String configurationXml;
  private String revisionHash;
  private Set<CanonicalizationContainer> canonicalizations;


  @Data
  public static class CanonicalizationContainer {

    private String formulaXml;
    private Long formulaId;
    private String canonicXml;
    private Long canonicId;
    private String canonicalizationError;
  }
}
