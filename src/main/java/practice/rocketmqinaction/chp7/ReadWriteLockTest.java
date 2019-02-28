package practice.rocketmqinaction.chp7;

import java.util.Random;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        final ReadWrite readWrite = new ReadWrite();
        for (int i = 0; i < 3; ++i) {
            new Thread(() -> readWrite.get()).start();
        }
        for (int i = 0; i < 3; ++i) {
            new Thread(() -> readWrite.put(new Random().nextInt(8))).start();
        }
    }
}