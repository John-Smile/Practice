package practice.rpcfs.chp4;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static final Map<String, User> userMap = new HashMap<>();
    static {
        userMap.put("John", new User("John", "John@126.com"));
        userMap.put("Smile", new User("Smile", "Smile@126.com"));
    }
    @Override
    public User findByName(String userName) {
        System.out.println("findByName start");
        return userMap.get(userName);
    }
}
