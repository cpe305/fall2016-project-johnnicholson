package app;

public class Util {
  public static int getFinalId(String uri) {
    String[] sections = uri.split("/");
    return Integer.parseInt(sections[sections.length - 1]);
  }
}
