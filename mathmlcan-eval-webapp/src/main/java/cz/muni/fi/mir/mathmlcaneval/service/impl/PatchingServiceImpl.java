package cz.muni.fi.mir.mathmlcaneval.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import cz.muni.fi.mir.mathmlcaneval.service.PatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatchingServiceImpl implements PatchingService {

  private final ObjectMapper objectMapper;


  @Override
  public <S, T> T patch(JsonPatch jsonPatch, S source, Class<T> target) {
    try {
      final var patched = jsonPatch.apply(this.objectMapper.convertValue(source, JsonNode.class));
      return objectMapper.treeToValue(patched, target);
    } catch (JsonPatchException | JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
