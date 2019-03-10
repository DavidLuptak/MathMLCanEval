package cz.muni.fi.mir.mathmlcaneval.requests;

import lombok.Data;

@Data
public class TokenRevocationRequest {
  private String hint;
  private String token;
}
