package cz.muni.fi.mir.mathmlcaneval.service;

import com.github.fge.jsonpatch.JsonPatch;

public interface PatchingService {

  /**
   * Method used to patch {@code source} using with help of {@code jsonPatch} into {@code target} type;
   * @param jsonPatch data used to patch {@code source} object
   * @param source object that should be patched
   * @param target type of patched result
   * @param <S> source type
   * @param <T> target type
   * @return patched {@code source} entity
   */
  <S, T> T patch(JsonPatch jsonPatch, S source, Class<T> target);
}

