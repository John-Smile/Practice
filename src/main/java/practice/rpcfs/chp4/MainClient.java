package practice.rpcfs.chp4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/rpcfs/chp4/spring.xml");
        User user = (User) context.getBean("user");
        System.out.println(user.getEmail() + "" + user.getName());
    }
}
