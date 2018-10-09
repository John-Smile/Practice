package practice.rpcfs.chp6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelCopyTest {
    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream("");
        FileChannel finChannel = fin.getChannel();
        FileOutputStream fout = new FileOutputStream("");
        FileChannel foutChannel = fout.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        int bytesRead = finChannel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                foutChannel.write(buf);
            }
            buf.clear();
            bytesRead = finChannel.read(buf);
        }
        fin.close();
        finChannel.close();
        fout.close();
        foutChannel.close();
    }
}
