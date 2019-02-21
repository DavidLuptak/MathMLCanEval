package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.DeployService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DeployServiceImpl implements DeployService {

  @Override
  public void deploy(InputStream jarFile, String revision) throws IOException {
    final var location = Paths.get(System.getProperty("java.io.tmpdir"), "math-builds");
    final var jarLocation = location.resolve(revision + ".jar");

    if(Files.exists(jarLocation)) {
      Files.delete(jarLocation);
    }

    Files.createFile(jarLocation);

    try(InputStream is  = jarFile;
      OutputStream out = new FileOutputStream(jarLocation.toFile())) {

      int read = 0;
      byte[] bytes = new byte[1024];

      while ((read = is.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
    }
  }

  @Override
  public boolean deploymentExists(String revision) {
    return false;
  }
}
