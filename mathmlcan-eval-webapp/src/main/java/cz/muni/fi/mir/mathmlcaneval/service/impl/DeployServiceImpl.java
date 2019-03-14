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
import cz.muni.fi.mir.mathmlcaneval.service.DeployService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class DeployServiceImpl implements DeployService {
  private final LocationProperties locationProperties;

  @Override
  public void deploy(InputStream jarFile, String revision) throws IOException {
    final var jarLocation = locationProperties.getRepositoryFolder().resolve(revision + ".jar");

    if(Files.exists(jarLocation)) {
      Files.delete(jarLocation);
    }

    Files.createFile(jarLocation);

    try(InputStream is  = jarFile;
      OutputStream out = new FileOutputStream(jarLocation.toFile())) {

      var read = 0;
      final var bytes = new byte[1024];

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
