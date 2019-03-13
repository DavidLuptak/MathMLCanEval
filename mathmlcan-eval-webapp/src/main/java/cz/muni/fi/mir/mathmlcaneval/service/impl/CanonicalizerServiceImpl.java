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
package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.configurations.props.LocationProperties;
import cz.muni.fi.mir.mathmlcaneval.domain.ApplicationRun;
import cz.muni.fi.mir.mathmlcaneval.domain.CanonicOutput;
import cz.muni.fi.mir.mathmlcaneval.domain.Formula;
import cz.muni.fi.mir.mathmlcaneval.service.CanonicalizerService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CanonicalizerServiceImpl implements CanonicalizerService {
  private final LocationProperties locationProperties;

  @Override
  public List<CanonicOutput> fireCanonicalizer(String revision, String configuration,
    List<Formula> formulas, ApplicationRun run) {
    final var result = new ArrayList<CanonicOutput>();

    try {
      final var jar = locationProperties.getRepositoryFolder().resolve(revision + ".jar");
      // todo try reinit ?
      if(!Files.exists(jar)) {
        throw new RuntimeException();
      }

      try(URLClassLoader classLoader = new URLClassLoader(new URL[]{jar.toUri().toURL()})) {
        Class<?> cls = classLoader.loadClass("cz.muni.fi.mir.mathmlcanonicalization.MathMLCanonicalizer");

        final var constructor = cls.getConstructor(InputStream.class);
        final var canonicalize = cls.getMethod("canonicalize", InputStream.class, OutputStream.class);

        try(InputStream configStream = new ByteArrayInputStream(configuration.getBytes())) {
          final var canonicalizer = constructor.newInstance(configStream);

          for(Formula f : formulas) {
            result.add(canonicalize(f, run, canonicalizer, canonicalize));
          }
        }
      }

    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex ) {
      log.error(ex);
    }

    return result;
  }

  private CanonicOutput canonicalize(Formula f, ApplicationRun run, Object canonicalizer,
    Method canonicalize) {
    final var result = new CanonicOutput();
    final var start = System.currentTimeMillis();

    try (InputStream in = new ByteArrayInputStream(f.getRaw().getBytes());
      OutputStream out = new ByteArrayOutputStream()) {
      canonicalize.invoke(canonicalizer, in, out);
      result.setRaw(out.toString());
    } catch (IOException | IllegalAccessException | InvocationTargetException ex) {
      log.info(ex);
      result.setError(ex.getMessage());
    }

    final var stop = System.currentTimeMillis() - start; // todo
    result.setApplicationRun(run);
    result.setFormula(f);

    return result;
  }
}
