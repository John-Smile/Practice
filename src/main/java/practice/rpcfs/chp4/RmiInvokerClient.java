package practice.rpcfs.chp4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiInvokerClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/rpcfs/chp4/rmi-rpc-client.xml");
        UserService userService = (UserService) context.getBean("userRmiServiceProxy");
        User user = userService.findByName("John");
        System.out.println(user);
    }
}
