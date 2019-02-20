package cz.muni.fi.mir.mathmlcaneval.configurations.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.oauth")
public class OAuthProperties {
  private String keyPass;
  private int tokenValidity;
}
