package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import java.util.List;

public interface FormulaService extends BaseService<FormulaResponse, Object> {

  List<FormulaResponse> getFormulasForCollection(Long collection);
}
