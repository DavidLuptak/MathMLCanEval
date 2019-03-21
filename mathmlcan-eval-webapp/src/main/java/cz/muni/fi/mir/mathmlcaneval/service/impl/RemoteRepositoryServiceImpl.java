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
import cz.muni.fi.mir.mathmlcaneval.service.RemoteRepositoryService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class RemoteRepositoryServiceImpl implements RemoteRepositoryService {

  private final LocationProperties locationProperties;

  @Override
  public Path cloneAndCheckout(String revision) {
    Git git = null;
    final var location = locationProperties.getBuildFolder().resolve(revision);
    log.info("Location {} chosen for revision {}", () -> location, () -> revision);
    try {
      if (!Files.exists(location)) {
        log.debug("Git repo does not exist at location {} calling git clone.", () -> location);
        Files.createDirectories(location);
        git = Git.cloneRepository()
          .setURI("https://github.com/MIR-MU/MathMLCan")
          .setDirectory(location.toFile())
          .call();
      } else {
        log.debug("Git repo found at {} calling reset hard", () -> location);
        git = Git.open(location.toFile());
        git.reset().setMode(ResetType.HARD).call();
      }

      git.checkout().setName(revision).call();
      log.info("Revision '{}' checked out", () -> revision);

      if (!git.getRepository().resolve("HEAD").name().equals(revision)) {
        throw new RuntimeException("invalid checkout");
      }

      return location;
    } catch (Exception e) {
      log.fatal(e);
    } finally {
      if (git != null) {
        log.trace("Closing git location {}", location);
        git.close();
      }
    }

    return null;
  }

  @Override
  public void clean(String revision) {
    final var location = locationProperties.getBuildFolder().resolve(revision);
    try {
      if (!Files.deleteIfExists(location)) {
        log.warn("Non Existing revision passed for delete");
      }
    } catch (IOException ex) {
      log.error(ex);
    }
  }
}
