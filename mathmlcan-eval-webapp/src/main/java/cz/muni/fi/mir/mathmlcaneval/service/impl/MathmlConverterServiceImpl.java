package cz.muni.fi.mir.mathmlcaneval.service.impl;

import cz.muni.fi.mir.mathmlcaneval.service.MathmlConverterService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;
import net.sourceforge.jeuclid.DOMBuilder;
import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.converter.Converter;
import net.sourceforge.jeuclid.elements.generic.DocumentElement;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Log4j2
@Component
public class MathmlConverterServiceImpl implements MathmlConverterService {

  private static final DOMBuilder BUILDER = DOMBuilder.getInstance();
  private static final LayoutContext LAYOUT_CONTEXT = LayoutContextImpl.getDefaultLayoutContext();

  @Override
  public byte[] toImage(String mathml) {
    byte[] bytes = null;
    try(ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
      final Document stdDomNode = MathMLParserSupport.parseString(mathml);
      final DocumentElement jEuclidDom = BUILDER.createJeuclidDom(stdDomNode, true, true);

      Converter converter = Converter.getInstance();
      BufferedImage bi = converter.render(jEuclidDom, LAYOUT_CONTEXT);

      ImageIO.write(bi, "png", baos);

      bytes = baos.toByteArray();
    } catch (Exception e) {
      log.error(e);

      throw new RuntimeException(e);
    }

    return bytes;
  }
}
