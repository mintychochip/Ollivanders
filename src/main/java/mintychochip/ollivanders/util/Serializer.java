package mintychochip.ollivanders.util;

import mintychochip.ollivanders.betterwand.WandBoost;

import java.io.*;

public class Serializer {

    public static byte[] serializeObject(WandBoost boost) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(boost);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static WandBoost deserializeBoost(byte[] boost) throws IOException, ClassNotFoundException {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(boost);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (WandBoost) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
