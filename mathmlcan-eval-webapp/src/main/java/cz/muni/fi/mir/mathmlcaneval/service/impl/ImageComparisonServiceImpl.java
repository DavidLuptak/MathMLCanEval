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

import cz.muni.fi.mir.mathmlcaneval.service.ImageComparisonService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ua.comparison.image.ImageComparison;

@Log4j2
@Component
public class ImageComparisonServiceImpl implements ImageComparisonService {

  @Override
  public byte[] compare(byte[] firstImage, byte[] secondImage) {
    try (var is1 = new ByteArrayInputStream(firstImage);
      final var is2 = new ByteArrayInputStream(secondImage)) {
      final var pic1 = ImageIO.read(is1);
      final var pic2 = ImageIO.read(is2);

      final var tmp = Paths.get(System.getProperty("java.io.tmpdir"), "mathml-compares")
        .resolve(UUID.randomUUID().toString() + "png");
      Files.createFile(tmp);

      final var comp = new ImageComparison(pic1, pic2, tmp.toFile());
      // todo this does not work when pictures have different dimensions
      comp.compareImages();

      return Files.newInputStream(tmp).readAllBytes();
    } catch (IOException ex) {
      log.error(ex);

      throw new RuntimeException(ex);
    }
  }
}
