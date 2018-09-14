package practice.rpcfs.chp3;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class XML2Serializer implements ISerializer {
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder xe = new XMLEncoder(out, "utf-8", true, 0);
        xe.writeObject(obj);

        xe.close();
        return out.toByteArray();
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        XMLDecoder xd = new XMLDecoder(new ByteArrayInputStream(data));
        Object obj = xd.readObject();
        xd.close();
        return (T) obj;
    }
}
