package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.RemoteRepositoryService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RemoteRepositoryServiceImpl implements RemoteRepositoryService {

  @Override
  public Path cloneAndCheckout(String revision) {
    try {
      final var location = Paths.get(System.getProperty("java.io.tmpdir"), "git-test", revision);
      final Git git;

      if (!Files.exists(location)) {
        Files.createDirectories(location);
        git = Git.cloneRepository()
          .setURI("https://github.com/MIR-MU/MathMLCan")
          .setDirectory(location.toFile())
          .call();
      } else {
        git = Git.open(location.toFile());
        git.reset().setMode(ResetType.HARD).call();
      }

      git.checkout().setName(revision).call();

      if (!git.getRepository().resolve("HEAD").name().equals(revision)) {
        throw new RuntimeException("invalid checkout");
      }

      return location;
    } catch (Exception e) {
      log.fatal(e);
    }

    return null;
  }

  @Override
  public void clean(String revision) {
    final var location = Paths.get(System.getProperty("java.io.tmpdir"), "git-test", revision);
    try {
      if (!Files.deleteIfExists(location)) {
        log.warn("Non Existing revision passed for delete");
      }
    } catch (IOException ex) {
      log.error(ex);
    }
  }
}
