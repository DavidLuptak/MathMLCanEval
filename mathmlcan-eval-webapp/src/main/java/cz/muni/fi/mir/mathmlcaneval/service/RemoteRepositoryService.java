package cz.muni.fi.mir.mathmlcaneval.service;

import java.nio.file.Path;

public interface RemoteRepositoryService {
  Path cloneAndCheckout(String revision);
  void clean(String revision);
}
