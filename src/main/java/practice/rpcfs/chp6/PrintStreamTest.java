package practice.rpcfs.chp6;

import java.io.File;
import java.io.PrintStream;

public class PrintStreamTest {
    public static void main(String[] args) throws Exception {
        File file = new File("");
        PrintStream printStream = new PrintStream(file);
        printStream.println("Hello, java Blocking I/O");
        printStream.close();
    }
}
