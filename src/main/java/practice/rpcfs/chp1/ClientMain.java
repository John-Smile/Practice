package practice.rpcfs.chp1;

import java.rmi.Naming;

public class ClientMain {
    public static void main(String[] args) throws Throwable {
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8801/helloService");

        System.out.println("RMI 服务器返回的结果是: " + helloService.sayHello("John"));
    }
}
