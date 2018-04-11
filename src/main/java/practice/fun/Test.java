package practice.fun;

public class Test {
    public static void main(String[] args) {
        try {
        } catch (Throwable e) {
            System.err.println("catch Exception " + e.getStackTrace());
        }
    }
}
