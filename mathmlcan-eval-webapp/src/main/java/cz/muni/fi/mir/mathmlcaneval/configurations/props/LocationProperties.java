package cz.muni.fi.mir.mathmlcaneval.configurations.props;

import java.nio.file.Path;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.location")
public class LocationProperties {
  private Path buildFolder;
  private Path repositoryFolder;
  private Path m2Home;
}
