package practice.rpcfs.chp6;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;

public class CharArrayTest {
    public static void main(String[] args) throws Exception {
        String content = "Hello, java Blocking I/O";
        CharArrayReader charArrayReader = new CharArrayReader(content.toCharArray());
        char[] chars = new char[1024];
        int size = 0;
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        while ((size = charArrayReader.read(chars)) != -1) {
            charArrayWriter.write(chars, 0, size);
        }
        System.out.println(charArrayWriter.toString());
        char[] charArray = charArrayWriter.toCharArray();
        for (char c : charArray) {
            System.out.println(c);
        }
    }
}
