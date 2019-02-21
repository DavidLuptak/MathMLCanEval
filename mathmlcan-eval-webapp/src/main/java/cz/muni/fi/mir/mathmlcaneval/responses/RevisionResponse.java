package cz.muni.fi.mir.mathmlcaneval.responses;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RevisionResponse {
  private Long id;
  private String name;
  private String note;
  private String sha1;
  private LocalDateTime commitTime;
  private LocalDateTime syncTime;
}
