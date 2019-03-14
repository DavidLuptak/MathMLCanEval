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

import cz.muni.fi.mir.mathmlcaneval.service.MathmlConverterService;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;
import net.sourceforge.jeuclid.DOMBuilder;
import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.converter.Converter;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MathmlConverterServiceImpl implements MathmlConverterService {

  private static final DOMBuilder BUILDER = DOMBuilder.getInstance();
  private static final LayoutContext LAYOUT_CONTEXT = LayoutContextImpl.getDefaultLayoutContext();

  @Override
  public byte[] toImage(String mathml) {
    byte[] bytes = null;
    try(ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
      final var stdDomNode = MathMLParserSupport.parseString(mathml);
      final var jEuclidDom = BUILDER.createJeuclidDom(stdDomNode, true, true);

      final var converter = Converter.getInstance();
      final var bi = converter.render(jEuclidDom, LAYOUT_CONTEXT);

      ImageIO.write(bi, "png", baos);

      bytes = baos.toByteArray();
    } catch (Exception e) {
      log.error(e);

      throw new RuntimeException(e);
    }

    return bytes;
  }
}
