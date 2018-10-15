package practice.rpcfs.chp6;

import java.io.IOException;
import java.nio.file.*;

public class WatchServiceDemo {
    public void watchDir(Path path) throws IOException, InterruptedException {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            while (true) {
                final WatchKey key = watchService.take();
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    final WatchEvent.Kind<?> kind = watchEvent.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                    final Path fileName = watchEventPath.context();
                    System.out.println(kind + ": " + fileName);
                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final Path path = Paths.get("D://");
        WatchServiceDemo watchServiceDemo = new WatchServiceDemo();
        try {
            watchServiceDemo.watchDir(path);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }
}
