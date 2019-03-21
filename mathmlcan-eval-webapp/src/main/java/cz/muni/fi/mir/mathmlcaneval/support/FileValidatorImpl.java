package cz.muni.fi.mir.mathmlcaneval.support;

import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class FileValidatorImpl implements FileValidator {
  //50 4B 03 04
  private static final byte[] ZIP_HEADER = {(byte) 0x50, (byte) 0x4b, (byte) 0x03, (byte) 0x04};
  private static final byte[] XML_HEADER = {(byte) 0x3c, (byte) 0x3f, (byte) 0x78, (byte) 0x6d};

  @Override
  public boolean isValid(byte[] header, FileType fileType) {
    if(fileType == FileType.ZIP) {
      return Arrays.equals(header, ZIP_HEADER);
    } else {
      return Arrays.equals(header, XML_HEADER);
    }
  }
}
