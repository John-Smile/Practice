package practice.experiment;

public class Child extends Parent {
    private static int s = childStaticFiled();

    private static int childStaticFiled() {
        System.out.println("Child static field");
        return 0;
    }

    private int d = childInstanceFiled();

    private int childInstanceFiled() {
        System.out.println("Child instance field");
        return 0;
    }

    static {
        System.out.println("Child static statement");
    }

    private Child() {
        System.out.println("Child construct");
    }

    public static void main(String[] args) {
        new Child();
    }
}
