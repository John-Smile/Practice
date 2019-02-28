package practice.experiment;

public class Parent {
    private static int s = parentStaticFiled();

    private static int parentStaticFiled() {
        System.out.println("Parent static field");
        return 0;
    }

    private int d = parentInstanceFiled();

    private int parentInstanceFiled() {
        System.out.println("Parent instance field");
        return 0;
    }

    static {
        System.out.println("Parent static statement");
    }

    public Parent() {
        System.out.println("Parent construct");
    }
}