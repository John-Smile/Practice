package practice.rpcfs.chp6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DataStreamTest {
    public static void main(String[] args) throws Exception {
        String fileName = "";
        FileOutputStream fout = new FileOutputStream(fileName);
        DataOutputStream dos = new DataOutputStream(fout);
        dos.writeInt(2017);
        dos.writeUTF("Hello, java Blocking I/O");
        dos.writeBoolean(true);
        dos.close();
        fout.close();

        FileInputStream fin = new FileInputStream(fileName);
        DataInputStream dis = new DataInputStream(fin);
        System.out.println(dis.readInt());
        System.out.println(dis.readUTF());
        System.out.println(dis.readBoolean());
        dis.close();
        fin.close();
    }
}
