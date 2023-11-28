package mintychochip.ollivanders.util;

import mintychochip.ollivanders.betterwand.container.ComponentData;
import mintychochip.ollivanders.betterwand.container.WandData;

import java.io.*;

public class Serializer {

    public static byte[] serializeComponent(ComponentData data) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ComponentData deserializeComponent(byte[] data) throws IOException, ClassNotFoundException {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (ComponentData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] serializeWand(WandData data) throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static WandData deserializeWand(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (WandData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
