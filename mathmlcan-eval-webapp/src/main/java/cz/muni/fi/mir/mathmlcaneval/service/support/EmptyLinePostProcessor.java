package cz.muni.fi.mir.mathmlcaneval.service.support;

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EmptyLinePostProcessor extends AbstractPostProcessor {

  public EmptyLinePostProcessor() {
    super("emptyLine");
  }

  @Override
  public void process(CanonicOutput canonicOutput) {
    log.debug("empty lines will be deleted");
    canonicOutput.setXml(canonicOutput.getXml().replaceAll("(?m)^[ \t]*\r?\n", ""));
  }
}
