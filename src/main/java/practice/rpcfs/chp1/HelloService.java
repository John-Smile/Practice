package practice.rpcfs.chp1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloService extends Remote {
    String sayHello(String someOne) throws RemoteException;
}
