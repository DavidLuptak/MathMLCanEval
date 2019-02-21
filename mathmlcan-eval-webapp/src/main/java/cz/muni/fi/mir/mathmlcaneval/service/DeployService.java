package cz.muni.fi.mir.mathmlcaneval.service;

import java.io.IOException;
import java.io.InputStream;

public interface DeployService {
  void deploy(InputStream jarFile, String revision) throws IOException;
  boolean deploymentExists(String revision);
}
