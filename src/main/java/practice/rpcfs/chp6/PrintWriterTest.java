package practice.rpcfs.chp6;

import java.io.File;
import java.io.PrintWriter;

public class PrintWriterTest {
    public static void main(String[] args) throws  Exception {
        File file = new File("");
        PrintWriter pw = new PrintWriter(file);
        pw.format("Hello, %s %s %s %s", "java", "Blocking", "I/O", "!");
        pw.flush();
        pw.close();
    }
}
