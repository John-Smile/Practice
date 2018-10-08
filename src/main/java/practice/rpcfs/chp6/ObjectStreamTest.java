package practice.rpcfs.chp6;

import practice.rpcfs.chp4.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectStreamTest {
    public static void main(String[] args) throws Exception {
        User user = new User("John", "john@qq.com");

        FileOutputStream fout = new FileOutputStream("");
        ObjectOutputStream oout = new ObjectOutputStream(fout);
        oout.writeObject(user);
        oout.close();
        fout.close();

        FileInputStream fin = new FileInputStream("");
        ObjectInputStream oin = new ObjectInputStream(fin);
        User newUser = (User) oin.readObject();
        System.out.println(newUser);
        oin.close();
        fin.close();
    }
}
