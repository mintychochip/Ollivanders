package mintychochip.ollivanders.wand;

import mintychochip.ollivanders.wand.container.Data;
import mintychochip.ollivanders.wand.container.Data;

import java.io.*;

public class Serializer {
    public static <T extends Data> byte[] serialize(T data) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Object deserialize(byte[] data) throws IOException {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
