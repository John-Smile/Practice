package practice.rpcfs.chp6;

public class NIOClient {
    public static void main(String[] args) {
        new Thread(new NIOClientHandler("127.0.0.1", 8080)).run();
    }
}
