package practice.rpcfs.chp6;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
//        ServerSocketChannel channel;
//        channel.configureBlocking(false);
//        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {

                } else if (key.isConnectable()) {

                } else if (key.isReadable()) {

                } else if (key.isWritable()) {

                }
                keyIterator.remove();
            }
        }
    }
}
