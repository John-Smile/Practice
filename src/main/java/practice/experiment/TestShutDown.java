package practice.experiment;

import sun.net.util.IPAddressUtil;

public class TestShutDown {

    public static void main(String[] args) throws InterruptedException{
        String ip1 = "127.0.0.1";
        String ip2 = "255.255.255.0";
        String ip3 = "unknown";
        System.out.println(ip1 + " " + IPAddressUtil.isIPv4LiteralAddress(ip1));
        System.out.println(ip2 + " " + IPAddressUtil.isIPv4LiteralAddress(ip2));
        System.out.println(ip3 + " " + IPAddressUtil.isIPv4LiteralAddress(ip3));
    }

}