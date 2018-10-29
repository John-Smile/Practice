package practice.smartmvc.chp4;

public class GreetingImpl implements Greeting {
    @Override
    public void sayHello(String name) {
        before();
        System.out.println("Hello " + name);
        after();
    }

    private void after() {
    }

    private void before() {
    }

}
