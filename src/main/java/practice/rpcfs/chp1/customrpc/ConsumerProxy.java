package practice.rpcfs.chp1.customrpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class ConsumerProxy {
    public static <T> T consume(final Class<T> interfaceClass, final String host, final int port) throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host, port);
                try {
                    ObjectOutputStream ouput = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        ouput.writeUTF(method.getName());
                        ouput.writeObject(args);
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try {
                            Object result = input.readObject();
                            if (result instanceof Throwable) {
                                throw (Throwable) result;
                            }
                            return result;
                        } finally {
                            input.close();
                        }
                    } finally {
                        ouput.close();
                    }
                } finally {
                    socket.close();
                }
            }
        });
    }
 }
