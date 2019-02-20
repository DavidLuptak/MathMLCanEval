package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.requests.CreateConfigurationRequest;
import cz.muni.fi.mir.mathmlcaneval.responses.ConfigurationResponse;
import cz.muni.fi.mir.mathmlcaneval.service.InputConfigurationService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/configurations")
@RequiredArgsConstructor
public class ConfigurationResource {

  private final InputConfigurationService inputConfigurationService;


  @GetMapping
  public List<ConfigurationResponse> findAll() {
    return inputConfigurationService.findAll();
  }

  @PostMapping
  public ResponseEntity<ConfigurationResponse> create(
    @Valid @RequestBody CreateConfigurationRequest request) {
    ConfigurationResponse result = inputConfigurationService.save(request);
    return ResponseEntity.created(URI.create("asd")).body(result);
  }
}
