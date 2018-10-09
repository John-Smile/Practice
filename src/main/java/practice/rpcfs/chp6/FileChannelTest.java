package practice.rpcfs.chp6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream("");
        FileChannel finChannel = fin.getChannel();
        FileOutputStream fout = new FileOutputStream("");
        FileChannel foutChannel = fout.getChannel();

        finChannel.transferTo(0, finChannel.size(), foutChannel);
        fin.close();
        finChannel.close();
        fout.close();
        foutChannel.close();
    }
}
