package cz.muni.fi.mir.mathmlcaneval.service.support;

import java.util.List;
import java.util.Optional;

public interface CanonicalizationPostProcessorRegistry {

  List<String> getPostProcessors();

  Optional<CanonicalizationPostProcessor> getProcessor(String processor);

  List<CanonicalizationPostProcessor> getProcessors(List<String> processors);
}
