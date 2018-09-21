package practice.rpcfs.chp5;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZKClientDemo {
    public static void main(String[] args) throws Exception {
        String zkServers = "127.0.0.1:2181";
        int connectionTimeout = 3000;
        ZkClient zkClient = new ZkClient(zkServers, connectionTimeout);

        String path = "/zk-data";
        if (zkClient.exists(path)) {
            zkClient.delete(path);
        }
        zkClient.createPersistent(path);
        zkClient.writeData(path, "test_data_1");

        String data = zkClient.readData(path, true);
        System.out.println(data);

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("handleDataChange, dataPath:" + dataPath);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("handleDataDeleted, dataPath:" + dataPath);
            }
        });

        zkClient.writeData(path, "test_data_2");
//        Thread.sleep(5000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
