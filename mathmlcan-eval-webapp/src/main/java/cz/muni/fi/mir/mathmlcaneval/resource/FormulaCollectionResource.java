package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.requests.FormulaCollectionRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaCollectionResponse;
import cz.muni.fi.mir.mathmlcaneval.responses.FormulaResponse;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaCollectionService;
import cz.muni.fi.mir.mathmlcaneval.service.FormulaService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class FormulaCollectionResource {

  private final FormulaCollectionService formulaCollectionService;
  private final FormulaService formulaService;

  @GetMapping
  public List<FormulaCollectionResponse> listAll() {
    return formulaCollectionService.findAll();
  }

  @PostMapping
  public ResponseEntity<FormulaCollectionResponse> save(@Valid @RequestBody
    FormulaCollectionRequest request) {

    FormulaCollectionResponse response = formulaCollectionService.save(request);

    return ResponseEntity.created(URI.create("asd")).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FormulaCollectionResponse> getById(@PathVariable Long id) {
    return ResponseEntity.of(formulaCollectionService.findById(id));
  }

  @GetMapping("/{id}/formulas")
  public ResponseEntity<List<FormulaResponse>> formulasInCollection(@PathVariable Long id) {
    return ResponseEntity.ok(formulaService.getFormulasForCollection(id));
  }
}
