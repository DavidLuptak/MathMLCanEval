package cz.muni.fi.mir.mathmlcaneval.service;

public interface ImageComparisonService {
  byte[] compare(byte[] firstImage, byte[] secondImage);
}
