package cz.muni.fi.mir.mathmlcaneval.resource;

import cz.muni.fi.mir.mathmlcaneval.service.support.CanonicalizationPostProcessorRegistry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post-processsors")
public class PostProcessorsResource {

  private final CanonicalizationPostProcessorRegistry postProcessorRegistry;
  
  @GetMapping
  public List<String> getPostProcessors() {
    return postProcessorRegistry.getPostProcessors();
  }
}
