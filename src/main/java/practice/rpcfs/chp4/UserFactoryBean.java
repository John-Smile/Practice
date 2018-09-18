package practice.rpcfs.chp4;

import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean<User> {
    private static final User user = new User("", "");
    private String name;
    private String email;

    @Override
    public User getObject() throws Exception {
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
