package practice.rpcfs.chp6;

import java.io.*;

public class BufferedStreamTest {
    public static void main(String[] args) throws Exception {
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        File srcFile = new File("");
        File targetFile = new File("");
        try {
            inputStream = new FileInputStream(srcFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
            outputStream = new FileOutputStream(targetFile);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[1024];
            int byt;
            while ((byt = bufferedInputStream.read(buff, 0, buff.length)) != -1) {
                bufferedOutputStream.write(buff, 0, byt);
            }
            bufferedOutputStream.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
