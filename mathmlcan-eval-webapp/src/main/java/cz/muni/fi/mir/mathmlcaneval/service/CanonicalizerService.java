package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import java.util.List;

public interface CanonicalizerService {

  List<CanonicOutput> fireCanonicalizer(String revision, String configuration,
    List<Formula> formulas, ApplicationRun run);
}
