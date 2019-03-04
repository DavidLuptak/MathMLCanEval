package cz.muni.fi.mir.mathmlcaneval.service.support;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPostProcessor implements CanonicalizationPostProcessor{
  private final String name;

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public int compareTo(CanonicalizationPostProcessor o) {
    return 0;
  }
}
