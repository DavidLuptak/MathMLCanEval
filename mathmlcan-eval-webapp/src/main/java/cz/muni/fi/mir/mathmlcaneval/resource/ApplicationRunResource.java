package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.requests.CanonicalizationRequest;
import cz.muni.fi.mir.mathmlcaneval.service.ApplicationRunService;
import cz.muni.fi.mir.mathmlcaneval.support.Response;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app-runs")
@RequiredArgsConstructor
public class ApplicationRunResource {
  private final ApplicationRunService applicationRunService;


  @PostMapping
  public ResponseEntity<Response> submit(@Valid @RequestBody CanonicalizationRequest request) {
    applicationRunService.save(request);

    return ResponseEntity.accepted().build();
  }
}
