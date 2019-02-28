package cz.muni.fi.mir.mathmlcaneval.service;

import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import java.util.List;

public interface FormulaCollectionService extends
  BaseService<FormulaCollectionResponse, FormulaCollectionRequest> {

  List<FormulaCollectionResponse> collectionsWithFormula(Long formulaId);
}
