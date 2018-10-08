package practice.rpcfs.chp6;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOEchoService {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        int port = 8082;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                executor.submit(new BIOEchoServerHandler(socket));
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
