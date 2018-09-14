package practice.experiment;

import java.util.concurrent.Callable;

public class Task implements Callable {
    public Object call() throws Exception {
        System.out.println("普通任务");
        return null;
    }
}