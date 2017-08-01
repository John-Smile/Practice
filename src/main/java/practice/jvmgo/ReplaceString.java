package practice.jvmgo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Hulk on 2017/7/26.
 */
public class ReplaceString {
    public static void main(String[] args) {
        String dir = "E:\\Go\\workspace\\src\\jvmgo\\ch07"; //args[1];
        String regex = "ch06";
        String replacement = "ch07";
        File file = new File(dir);
        walkThroughDirectory(file, regex, replacement);
    }

    private static void walkThroughDirectory(File file, String regex, String replacement) {
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                walkThroughDirectory(childFile, regex, replacement);
            } else {
                replaceFile(childFile, regex, replacement);
            }
        }
    }

    private static void replaceFile(File file, String regex, String replacement) {
        String charset = "UTF-8";
        Path path = file.toPath();
        try {
            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll(regex, replacement);
            Files.write(path, content.getBytes(charset));
        } catch (Throwable e) {
            throw new RuntimeException("替换文件失败", e);
        }
    }
}
