package practice.rpcfs.chp3;

public interface ISerializer {
    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
