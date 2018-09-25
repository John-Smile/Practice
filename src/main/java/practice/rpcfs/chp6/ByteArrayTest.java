package practice.rpcfs.chp6;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class ByteArrayTest {
    public static void main(String[] args) throws Exception {
        String content = "java Blocking I/O";
        byte[] inputBytes = content.getBytes(Charset.forName("utf-8"));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBytes);
        byte[] bytes = new byte[1024];
        int size = 0;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((size = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, size);
        }
        System.out.println(outputStream.toString("utf-8"));
    }
}
