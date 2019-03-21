package cz.muni.fi.mir.mathmlcaneval.support;

public interface FileValidator {

  enum FileType {
    ZIP, XML
  }

  boolean isValid(byte[] header, FileType fileType);
}
