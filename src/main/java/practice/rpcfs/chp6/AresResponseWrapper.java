package practice.rpcfs.chp6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AresResponseWrapper {
    private BlockingQueue<AresResponse> responseQueue = new ArrayBlockingQueue<>(1);
    private long responseTime;
    public boolean isExpire() {
        AresResponse response = responseQueue.peek();
        if (response == null) {
            return false;
        }
        long timeout = response.getInvokeTimeout();
        if ((System.currentTimeMillis() - responseTime) > timeout) {
            return true;
        }
        return false;
    }
    public static AresResponseWrapper of() {
        return new AresResponseWrapper();
    }

    public BlockingQueue<AresResponse> getResponseQueue() {
        return responseQueue;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
