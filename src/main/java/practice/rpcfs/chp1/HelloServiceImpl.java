package practice.rpcfs.chp1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    public HelloServiceImpl() throws RemoteException {
        super();
    }

    public String sayHello(String someOne) throws RemoteException {
        return "Hello," + someOne;
    }
}
