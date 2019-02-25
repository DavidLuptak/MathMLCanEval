package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.CanonicalizerService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class CanonicalizerServiceImpl implements CanonicalizerService {

  @Override
  public void fireCanonicalizer(String revision, Long configurationId, List<Long> formulas) {
    final String FORMULA = "";
    try {
      Path jar1 = Paths.get("C:\\Users\\emptak\\AppData\\Local\\Temp\\math-builds",
        "3d66b66bb73b0c6193df6871c51d2b1a3dabad9c.jar");
      Path config = Paths.get("C:\\Users\\emptak\\Desktop\\sample.xml");
      URL[] urls = {jar1.toUri().toURL()};
      URLClassLoader loader = new URLClassLoader(urls);
      Class<?> cls = loader.loadClass("cz.muni.fi.mir.mathmlcanonicalization.MathMLCanonicalizer");

      Constructor constructor = null;
      Method canonicalize = null;
      Object canonicalizer = null;

      constructor = cls.getConstructor(InputStream.class);
      canonicalize = cls.getMethod("canonicalize", InputStream.class, OutputStream.class);

      if (constructor != null) {
        try (InputStream c = Files.newInputStream(config)) {

          canonicalizer = constructor.newInstance(c);

          InputStream input = new ByteArrayInputStream(FORMULA.getBytes());
          OutputStream out = new ByteArrayOutputStream();
          canonicalize.invoke(canonicalizer, input, out);

          System.out.println(out.toString());

        }
      }
    } catch (IOException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
      log.error(ex.getMessage(), ex);
    }
  }

  @Override
  public void fireCanonicalizer(String revision, Long configurationId, Long collectionId) {

  }
}
