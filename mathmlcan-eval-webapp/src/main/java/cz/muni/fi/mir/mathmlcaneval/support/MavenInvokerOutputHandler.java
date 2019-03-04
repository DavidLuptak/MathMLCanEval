package cz.muni.fi.mir.mathmlcaneval.support;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.maven.shared.invoker.InvocationOutputHandler;

@Log4j2
@RequiredArgsConstructor
public class MavenInvokerOutputHandler implements InvocationOutputHandler {

  private final Level level;

  @Override
  public void consumeLine(String line) throws IOException {
    log.log(level, () -> line);
  }
}
