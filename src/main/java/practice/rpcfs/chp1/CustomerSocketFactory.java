package practice.rpcfs.chp1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class CustomerSocketFactory extends RMISocketFactory {
    public Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        if (port == 0) {
            port = 8501;
        }
        System.out.println("rmi notify port:" + port);
        return new ServerSocket(port);
    }
}
