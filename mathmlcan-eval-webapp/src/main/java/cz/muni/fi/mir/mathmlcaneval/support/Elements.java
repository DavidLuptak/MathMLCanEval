package cz.muni.fi.mir.mathmlcaneval.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Elements {

  MI("mi", 0),
  MN("mn", 1),
  MO("mo", 2),
  MTEXT("mtext", 3),
  MSPACE("mspace", 4),
  MS("ms", 5),
  MGLPYH("mglyph", 6),
  MROW("mrow", 7),
  MFRAC("mfrac", 8),
  MSQRT("msqrt", 9),
  MROOT("mroot", 10),
  MSTYLE("mstyle", 11),
  MERROR("merror", 12),
  MPADDED("mpadded", 13),
  MPHATOM("mphantom", 14),
  MFENCED("mfenced", 15),
  MENCLOSE("menclose", 16),
  MSUB("msub", 17),
  MSUP("msup", 18),
  MSUBSUP("msubsup", 19),
  MUNDER("munder", 20),
  MOVER("mover", 21),
  MUNDEROVER("munderover", 22),
  MMULTISCRIPTS("mmultiscripts", 23),
  MTABLE("mtable", 24),
  MLABELEDTR("mlabeledtr", 25),
  MTR("mtr", 26),
  MTD("mtd", 27),
  MALIGNGROUP("maligngroup", 28),
  MALIGNMARK("malignmark", 29),
  MACTION("maction", 30),
  MPRESCRIPTS("mprescripts", 31),
  NONE("none", 32),
  MLONGDIV("mlongdiv", 33),
  MSCARRIES("mscarries", 34),
  MSCARRY("mscarry", 35),
  MSGROUP("msgroup", 36),
  MSLINE("msline", 37),
  MSROW("msrow", 38),
  MSTACK("mstack", 39)
  ;
  private final String element;
  private final int position;

  public static Elements convert(String element) {
    for(Elements e : values()) {
      if(e.getElement().equals(element)) {
        return e;
      }
    }

    return null;
  }
}
