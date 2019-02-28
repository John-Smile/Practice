package practice.experiment;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class EmptyStringTest {
    public static void main(String[] args) throws Exception {
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream("Z:\\test"));
//        dos.writeUTF("");
//        dos.close();
        DataInputStream dis = new DataInputStream(new FileInputStream("Z:\\test"));
        String s = dis.readUTF();
        System.out.println(s);
    }
}