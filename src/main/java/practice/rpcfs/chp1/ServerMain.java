package practice.rpcfs.chp1;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(8801);
        RMISocketFactory.setSocketFactory(new CustomerSocketFactory());

        HelloService helloService = new HelloServiceImpl();
        Naming.bind("rmi://localhost:8801/helloService", helloService);
        System.out.println("ServerMain provide RPC service now");
    }
}
