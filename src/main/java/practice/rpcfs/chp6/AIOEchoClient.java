package practice.rpcfs.chp6;

public class AIOEchoClient {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        new Thread(new AsyncEchoClientHandler("127.0.0.1", port)).start();
    }
}
