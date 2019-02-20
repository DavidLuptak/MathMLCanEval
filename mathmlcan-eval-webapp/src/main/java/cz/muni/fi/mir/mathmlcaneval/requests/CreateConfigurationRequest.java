package cz.muni.fi.mir.mathmlcaneval.requests;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateConfigurationRequest {

  @NotEmpty
  private String name;
  @NotEmpty
  private String content;
  private String note;
}
