package practice.rpcfs.chp4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpRpcClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/rpcfs/chp4/http-rpc-client.xml");
        UserService userService = (UserService) context.getBean("userServiceProxy");
        User user = userService.findByName("John");
        System.out.println(user);
    }
}
