package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.ImageComparisonService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
    try (InputStream is1 = new ByteArrayInputStream(firstImage);
      InputStream is2 = new ByteArrayInputStream(secondImage)) {
      BufferedImage pic1 = ImageIO.read(is1);
      BufferedImage pic2 = ImageIO.read(is2);

      Path tmp = Paths.get(System.getProperty("java.io.tmpdir"), "mathml-compares")
        .resolve(UUID.randomUUID().toString() + "png");
      Files.createFile(tmp);

      ImageComparison comp = new ImageComparison(pic1, pic2, tmp.toFile());
      // todo this does not work when pictures have different dimensions
      comp.compareImages();

      InputStream result = Files.newInputStream(tmp);
      return result.readAllBytes();

    } catch (IOException ex) {
      log.error(ex);

      throw new RuntimeException(ex);
    }
  }
}
