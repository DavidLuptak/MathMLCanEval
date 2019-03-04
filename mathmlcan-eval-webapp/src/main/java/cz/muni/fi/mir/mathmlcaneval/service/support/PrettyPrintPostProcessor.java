package cz.muni.fi.mir.mathmlcaneval.service.support;

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import org.springframework.stereotype.Component;

@Component
public class PrettyPrintPostProcessor extends AbstractPostProcessor{

  public PrettyPrintPostProcessor() {
    super("prettyPrint");
  }

  @Override
  public void process(CanonicOutput canonicOutput) {

  }
}
