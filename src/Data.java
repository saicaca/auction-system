import java.io.*;
import java.util.ArrayList;

public class Data implements Serializable{
    static Data data;
    public ArrayList<Account> accounts = new ArrayList<>();
    public ArrayList<Item> items = new ArrayList<>();

    Data() {
        accounts.add(new Account("Test", "Test", true));
    }

    public static void save() throws Exception{
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("save.dat"));
        output.writeObject(data);
    }

    public static void load() throws Exception{
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("save.dat"));
        data = (Data)input.readObject();
    }
}
