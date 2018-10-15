package practice.rpcfs.chp6;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {
    public static void main(String[] args) {
        Path path = Paths.get("");
        System.out.println("path1:" + path);
        path = Paths.get("", "", "", "", "");
        System.out.println("path2:" + path);

        File pathToFile = path.toFile();
        System.out.println("Path to file name: " + pathToFile.getName());
        URI pathToUri = path.toUri();
        System.out.println("Path to URI: " + pathToUri);
        Path pathToAbsolutePath = path.toAbsolutePath();
        System.out.println("Path to absolute path: " + pathToAbsolutePath);
    }
}
