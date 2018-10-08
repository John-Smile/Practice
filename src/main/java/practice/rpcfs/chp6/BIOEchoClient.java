package practice.rpcfs.chp6;

import java.io.*;
import java.net.Socket;

public class BIOEchoClient {
    public static void main(String[] args) throws Exception {
        int port = 8082;
        String serverIp = "127.0.0.1";
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer;
        try {
            socket = new Socket(serverIp, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("Hello, Block IO.\n");
            writer.flush();
            String echo = reader.readLine();
            System.out.println("echo:" + echo);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
