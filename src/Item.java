import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Item implements Serializable {
    private boolean Empty;
    private String name;
    private int newness;
    private String description;
    private int value;
    private Account owner;

    private Item() {

    }

    private Item(String name, int newness, String description, int value) {
        this.name = name;
        this.newness = newness;
        this.description = description;
        this.value = value;
        owner = Account.getCurrentUser();
        Data.data.items.add(this);
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
        return Data.data.items.get(id);
    }

    public static ArrayList<Item> getList() {
        return Data.data.items;
    }

    public static void clear() {
        Data.data.items = new ArrayList<>();
    }

    private void checkUser() throws Exception {
        if (Account.getCurrentUser() == null)
            throw new NoLoginException();
        else if (!(Account.getCurrentUser() == owner) && !Account.getCurrentUser().isAdmin()) {
            throw new NoPermissionException();
        }
    }

    @Override
    public String toString() {
        return "id: " + Data.data.items.indexOf(this) +
                "\nname: " + name +
                "\nownerId: " + owner.getUsername() +
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
        return owner.getId();
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
