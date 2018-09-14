package practice.fun;

public class Test {
    public static void main(String[] args) throws Exception {
        int COUNT_BITS = Integer.SIZE - 3;
        int CAPACITY = (1 << COUNT_BITS) - 1;

        int RUNNING = -1 << COUNT_BITS;

        System.out.println("RUNNING :" + RUNNING);
        System.out.println("RUNNING & ~CAPACITY :" + (RUNNING & ~CAPACITY));
    }

    private static int runStateOf(int c) {
        int COUNT_BITS = Integer.SIZE - 3;
        int CAPACITY = (1 << COUNT_BITS) - 1;
        return c & ~CAPACITY;
    }
}
