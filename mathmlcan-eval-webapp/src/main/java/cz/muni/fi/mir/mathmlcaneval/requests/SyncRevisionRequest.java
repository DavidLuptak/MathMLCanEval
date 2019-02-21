package cz.muni.fi.mir.mathmlcaneval.requests;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SyncRevisionRequest {

  private LocalDateTime from;
  private LocalDateTime to;
  private String sha1;
}
