package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaCollectionService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/formulas")
@RestController
@RequiredArgsConstructor
public class FormulaResource {

  private final FormulaService formulaService;
  private final FormulaCollectionService formulaCollectionService;
  @GetMapping
  public List<FormulaResponse> findAll(Pageable pageable) {
    return formulaService.findAll(pageable);
  }

  @GetMapping("/{id}/collections")
  public List<FormulaCollectionResponse> collections(@PathVariable Long formulaId) {
    return formulaCollectionService.collectionsWithFormula(formulaId);
  }
}
