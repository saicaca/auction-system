import java.io.*;
import java.util.ArrayList;

public class Item implements Serializable {
    private static ArrayList<Item> list = new ArrayList<>();
    private boolean Empty;
    private String name;
    private int newness;
    private String description;
    private int value;
    private int ownerId;

    private Item() {

    }

    private Item(String name, int newness, String description, int value) {
        this.name = name;
        this.newness = newness;
        this.description = description;
        this.value = value;
        ownerId = Account.getCurrentUser().getId();
        list.add(this);
    }

    public boolean isEmpty() {
        return Empty;
    }

    public static void add(String name, int newness, String description, int value) throws NoLoginException {
        if (Account.getCurrentUser() == null)
            throw new NoLoginException();
        new Item(name, newness, description, value);
    }

    public static Item get(int id) {
        return list.get(id);
    }

    public static ArrayList<Item> getList() {
        return list;
    }

    public static void clear() {
        list = new ArrayList<>();
    }

    public static void save() throws Exception {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("items.dat"));
        for (Item item : list) {
            output.writeObject(item);
        }
    }

    public static void read() throws Exception {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("items.dat"));
        try {
            while (true) {
                Item.list.add((Item) input.readObject());
            }
        } catch (EOFException ex) {

        }
    }



    private void checkUser() throws Exception {
        if (Account.getCurrentUser() == null)
            throw new NoLoginException();
        else if (!(Account.getCurrentUser().getId() == ownerId) && !Account.getCurrentUser().isAdmin()) {
            throw new NoPermissionException();
        }
    }

    @Override
    public String toString() {
        return "id: " + list.indexOf(this) +
                "\nname: " + name +
                "\nowner: " + Account.get(ownerId).getUsername() +
                "\nnewness: " + newness +
                "\ndescription: " + description +
                "\nvalue: " + value + "\n";
    }

    public void delete() throws Exception {
        checkUser();
        Empty = true;
    }

    public String getName() {
        return name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setName(String name) throws Exception {
        checkUser();
        this.name = name;
    }

    public int getNewness() {
        return newness;
    }

    public void setNewness(int newness) throws Exception {
        checkUser();
        this.newness = newness;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        checkUser();
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) throws Exception {
        checkUser();
        this.value = value;
    }
}

class NoLoginException extends Exception {
    NoLoginException() {
        super("No login");
    }
}

class NoPermissionException extends Exception {
    NoPermissionException() {
        super("No Permission");
    }
}
