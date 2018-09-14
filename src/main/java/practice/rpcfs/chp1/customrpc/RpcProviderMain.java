package practice.rpcfs.chp1.customrpc;

public class RpcProviderMain {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        ProviderReflect.provider(service, 8083);
    }
}
