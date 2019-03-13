/*
 * Copyright © 2013 the original author or authors (webmias@fi.muni.cz)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

