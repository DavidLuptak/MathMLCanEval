package cz.muni.fi.mir.mathmlcaneval.responses;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FormulaResponse {
  private Long id;
  private String xml;
  private String note;
  private String hashValue;
  private LocalDateTime insertTime;
}
