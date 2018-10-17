package practice.rpcfs.chp6;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RevokerResponseHolder {
    private static final Map<String, AresResponseWrapper> responseMap = Maps.newConcurrentMap();
    private static final ExecutorService removeExpireKeyExecutor = Executors.newSingleThreadExecutor();
    static {
        removeExpireKeyExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        for (Map.Entry<String, AresResponseWrapper> entry : responseMap.entrySet()) {
                            boolean isExpire = entry.getValue().isExpire();
                            if (isExpire) {
                                responseMap.remove(entry.getKey());
                            }
                            Thread.sleep(10);
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }
    public static void initResponseData(String requestUniqueKey) {
        responseMap.put(requestUniqueKey, AresResponseWrapper.of());
    }
    public static void putResultValue(AresResponse response) {
        long currentTime = System.currentTimeMillis();
        AresResponseWrapper responseWrapper = responseMap.get(response.getUniqueKey());
        responseWrapper.setResponseTime(currentTime);
        responseWrapper.getResponseQueue().add(response);
        responseMap.put(response.getUniqueKey(), responseWrapper);
    }

    public static AresResponse getValue(String requestUniqueKey, long timeout) {
        AresResponseWrapper responseWrapper = responseMap.get(requestUniqueKey);
        try {
            return responseWrapper.getResponseQueue().poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            responseMap.remove(requestUniqueKey);
        }
    }
}
