package practice.experiment;

public class ConstructorThrowsException {
    public ConstructorThrowsException() throws Exception {
        System.out.println("constructor start");
        throw new RuntimeException("For test");
    }

    public static void main(String[] args) {
        ConstructorThrowsException constructorThrowsException = null;
        try {
            constructorThrowsException = new ConstructorThrowsException();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(constructorThrowsException == null);
        }
    }
}
