package cz.muni.fi.mir.mathmlcaneval.service.support;

import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;

public interface CanonicalizationPostProcessor extends Comparable<CanonicalizationPostProcessor> {

  void process(CanonicOutput canonicOutput);

  String name();
}
