package practice.smartmvc.chp4;

public class HelloImpl implements Hello {
    @Override
    public void say(String name) {
        System.out.println("Hello" + name);
    }
}
