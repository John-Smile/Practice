package practice.rpcfs.chp1.customrpc;

public class HelloServiceImpl implements HelloService {
    public String sayHello(String content) {
        return "Hello," + content;
    }
}
