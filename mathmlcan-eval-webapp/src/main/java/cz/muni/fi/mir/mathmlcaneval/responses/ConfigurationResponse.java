package cz.muni.fi.mir.mathmlcaneval.responses;

import lombok.Data;

@Data
public class ConfigurationResponse {

  private Long id;
  private String content;
  private String note;
  private String name;
}
