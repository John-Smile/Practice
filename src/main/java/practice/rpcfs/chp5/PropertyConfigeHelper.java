package practice.rpcfs.chp5;

import practice.rpcfs.chp6.SerializeType;

public class PropertyConfigeHelper {
    private static int channelConnectSize;
    private static SerializeType serializeType;
    public static String getZkService() {
        return null;
    }

    public static int getZkConnectTimeout() {
        return 0;
    }

    public static String getAppName() {
        return null;
    }

    public static int getZkSessionTimeout() {
        return 0;
    }

    public static int getChannelConnectSize() {
        return channelConnectSize;
    }

    public static SerializeType getSerializeType() {
        return serializeType;
    }
}
