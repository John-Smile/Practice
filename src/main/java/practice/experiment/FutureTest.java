package practice.experiment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class FutureTest {
    public static void main(String[] args) throws Exception {
//        CompletableFuture result = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
//            return "Knolders!";
//        }).thenApply(name -> "Hello " + name).thenApply(greeting -> greeting + " Welcome to Knoldus Inc!");
//        System.out.println(result.get());

        CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> "Knolders!");
        CompletableFuture result
                = completableFuture.thenAccept(value -> System.out.println("Hello " + value));
//        System.out.println(result.get());
    }
}
