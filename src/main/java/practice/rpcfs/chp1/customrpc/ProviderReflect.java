package practice.rpcfs.chp1.customrpc;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProviderReflect {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void provider(final Object service, int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            final Socket socket = serverSocket.accept();
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                        try {
                            try {
                                String methoName = input.readUTF();
                                Object[] arguments = (Object[]) input.readObject();
                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                try {
                                    Object result = MethodUtils.invokeExactMethod(service, methoName, arguments);
                                    output.writeObject(result);
                                } catch (Throwable t) {
                                    output.writeObject(t);
                                } finally {
                                    output.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                input.close();
                            }
                        } finally {
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
