package cz.muni.fi.mir.mathmlcaneval.support;

public class Utils {
  private Utils() {
    // prevent new
  }

  public static boolean deployedOnLinux() {
    return !System.getProperty("os.name").toLowerCase().contains("win");
  }
}
