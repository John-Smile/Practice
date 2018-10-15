package practice.rpcfs.chp6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;

public class FilesDemo {
    public static void main(String[] args) throws Exception {
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(""));
        for (Path path : directoryStream) {
            System.out.println(path);
        }
        Path file = Files.createDirectories(Paths.get(""));
        Charset charset = Charset.forName("UTF-8");
        String text = "Hello, java NIO2";
        try {
            BufferedWriter writer = Files.newBufferedWriter(file, charset, StandardOpenOption.APPEND);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(file, charset);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
