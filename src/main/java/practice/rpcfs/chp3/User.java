package practice.rpcfs.chp3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private transient Integer age = 28;
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(age);
    }

    private void raedObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        age = in.readInt();
    }
}
