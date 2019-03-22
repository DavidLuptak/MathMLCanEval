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
package cz.muni.fi.mir.mathmlcaneval.support;

import java.util.Arrays;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FileValidatorImpl implements FileValidator {
  //50 4B 03 04
  private static final byte[] ZIP_HEADER = {(byte) 0x50, (byte) 0x4b, (byte) 0x03, (byte) 0x04};
  private static final byte[] XML_HEADER = {(byte) 0x3c, (byte) 0x3f, (byte) 0x78, (byte) 0x6d};

  @Override
  public boolean isValid(byte[] header, FileType fileType) {
    log.trace("Checking if {} is valid header for {}", () -> Arrays.toString(header), () -> fileType);
    if(fileType == FileType.ZIP) {
      return Arrays.equals(header, ZIP_HEADER);
    } else {
      return Arrays.equals(header, XML_HEADER);
    }
  }
}
