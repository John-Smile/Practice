package practice.rpcfs.chp4;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiServer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring/rpcfs/chp4/rmi-rpc-server.xml");
    }
}
