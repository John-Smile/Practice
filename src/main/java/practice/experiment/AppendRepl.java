package practice.experiment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendRepl {



  public static void main(String[] argv) throws Exception {
    CharSequence input = "ab12 cd efg34 asdf 123";
    String pattStr = "([a-zA-Z]+[0-9]+)";
    Pattern p = Pattern.compile(pattStr);
    Matcher m = p.matcher(input);
    StringBuffer bufStr = new StringBuffer();
    boolean flag = false;
    while ((flag = m.find())) {
      String rep = m.group();
      m.appendReplacement(bufStr, "found<" + rep + ">");
    }
    m.appendTail(bufStr);
    String result = bufStr.toString();
    System.out.println(result);
  }

}
