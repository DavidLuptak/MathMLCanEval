package cz.muni.fi.mir.mathmlcaneval.service.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CanonicalizationPostProcessorRegistryImpl implements
  CanonicalizationPostProcessorRegistry {

  private final List<CanonicalizationPostProcessor> postProcessors;

  @Override
  public List<String> getPostProcessors() {
    return postProcessors
      .stream()
      .map(CanonicalizationPostProcessor::name)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<CanonicalizationPostProcessor> getProcessor(String processor) {
    for (CanonicalizationPostProcessor cpp : postProcessors) {
      if (cpp.name().equals(processor)) {
        return Optional.of(cpp);
      }
    }

    return Optional.empty();
  }

  @Override
  public List<CanonicalizationPostProcessor> getProcessors(List<String> processors) {
    final var result = new ArrayList<CanonicalizationPostProcessor>();

    for (CanonicalizationPostProcessor cpp : postProcessors) {
      if (processors.contains(cpp.name())) {
        result.add(cpp);
      }
    }

    return result;
  }
}
