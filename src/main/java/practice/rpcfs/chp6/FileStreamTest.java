package practice.rpcfs.chp6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileStreamTest {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        File srcFile = new File("");
        File targetFile = new File("");
        try {
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(targetFile);
            int byt;
            while ((byt = inputStream.read()) != -1) {
                outputStream.write(byt);
            }
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
