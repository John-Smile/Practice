package practice.experiment;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LongTask implements Callable {
    public Object call() throws Exception {
        System.out.println("长时间任务开始");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("长时间任务结束");
        return null;
    }
}