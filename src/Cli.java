import java.util.ArrayList;
import java.util.Scanner;

public class Cli {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws Exception{
        Account.read();
        Item.read();
        while (true){
            try{
                System.out.println(
                        "1. Manage items\n" +
                        "2. Manage accounts\n" +
                        "3. Save and exit");
                if (Account.getCurrentUser() == null)
                    System.out.println("Logged out");
                else
                    System.out.println("Logged in as "+Account.getCurrentUser().getUsername());
                int select = in.nextInt();
                switch(select){
                    case 1:
                        System.out.println(
                                "1. List items\n" +
                                "2. Add item\n" +
                                "3. Modify item\n" +
                                "4. Delete item");
                        select = in.nextInt();
                        switch (select){
                            case 1: listItems(); break;
                            case 2: addItem(); break;
                            case 3: modifyItem(); break;
                            case 4: deleteItem(); break;
                        }
                        break;
                    case 2:
                        System.out.println(
                                "1. Register\n" +
                                "2. Login\n" +
                                "3. Reset password\n" +
                                "4. Logout");
                        select = in.nextInt();
                        switch (select){
                            case 1: register(); break;
                            case 2: login(); break;
                            case 3: resetPassword(); break;
                            case 4: Account.logout(); break;
                        }
                        break;
                    case 3:
                        Item.save();
                        Account.save();
                        System.exit(0);
                }
            }
            catch (Exception ex){
                System.out.println(ex);
            }
        }
    }

    private static void listItems() throws Exception {
        System.out.println(
                "1. List all items\n" +
                "2. Filter by user");
        int select = in.nextInt();
        ArrayList<Item> items = Item.getList();
        switch (select) {
            case 1:
                for (Item item: items) {
                    if (item.isEmpty())
                        continue;
                    System.out.println(item.toString());
                }
                break;
            case 2:
                System.out.print("username: ");
                int userId = Account.get(in.next()).getId();
                for (Item item: items) {
                    if (item.isEmpty())
                        continue;
                    if (item.getOwnerId() == userId)
                        System.out.println(item.toString());
                }
        }
    }

    private static void addItem() throws Exception{
        System.out.print("name: ");
        in.nextLine();
        String name = in.nextLine();
        System.out.print("newness: ");
        int newness = in.nextInt();
        System.out.print("description: ");
        in.nextLine();
        String description = in.nextLine();
        System.out.print("value: ");
        int value = in.nextInt();
        Item.add(name, newness, description, value);
    }

    private static void modifyItem() throws Exception{
        System.out.print("item id: ");
        Item item = Item.get(in.nextInt());
        System.out.println(
                "1. name\n" +
                "2. newness\n" +
                "3. description\n" +
                "4. value");
        int select = in.nextInt();
        switch (select) {
            case 1:
                System.out.print("name: ");
                in.nextLine();
                item.setName(in.nextLine());
                break;
            case 2:
                System.out.print("newness: ");
                item.setNewness(in.nextInt());
                break;
            case 3:
                System.out.print("description: ");
                in.nextLine();
                item.setDescription(in.nextLine());
                break;
            case 4:
                System.out.print("value: ");
                item.setValue(in.nextInt());
                break;
        }
    }

    private static void deleteItem() throws Exception{
        System.out.print("Enter the id: ");
        int id = in.nextInt();
        Item.get(id).delete();
    }

    private static void register() throws Exception{
        System.out.print("username: ");
        String username = in.next();
        System.out.print("password: ");
        String password = in.next();
        Account.register(username, password, false);
    }

    private static void login() throws Exception{
        System.out.print("username: ");
        String username = in.next();
        System.out.print("password: ");
        String password = in.next();
        Account.login(username, password);
    }

    private static void resetPassword() throws Exception{
        if (Account.getCurrentUser() == null)
            throw new NoLoginException();
        System.out.print("new password: ");
        String newPassword = in.next();
        System.out.print("repeat: ");
        String repeat = in.next();
        if (newPassword.equals(repeat))
            Account.getCurrentUser().setPassword(newPassword);

    }
}
