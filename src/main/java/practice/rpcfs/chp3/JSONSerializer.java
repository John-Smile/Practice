package practice.rpcfs.chp3;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer implements ISerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public <T> byte[] serialize(T obj) {
        return new byte[0];
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return null;
    }
}
