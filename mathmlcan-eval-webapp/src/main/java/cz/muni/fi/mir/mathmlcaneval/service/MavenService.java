package cz.muni.fi.mir.mathmlcaneval.service;

import java.io.InputStream;
import java.nio.file.Path;

public interface MavenService {

  InputStream invokeMavenBuild(Path revision);
}
