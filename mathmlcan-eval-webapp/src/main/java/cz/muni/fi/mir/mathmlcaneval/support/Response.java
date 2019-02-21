package cz.muni.fi.mir.mathmlcaneval.support;

public interface Response {
  Response OK = new ResponseOk();

  String getStatus();
}
