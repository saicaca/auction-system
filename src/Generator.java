public class Generator {
    public static void main(String[] args) throws Exception {
        Account.register("admin", "admin", true);
        Item.add("Pillow", 100, "Just a pillow", 50);
        Account.register("ninel", "123456", false);
        Item.add("Nekopara", 10, "A galgame", 38);
        Item.add("Touhou 16", 10, "A STG", 60);
        Item.add("Pillow 40", 10, "Just a pillow again", 400);
        Account.save();
        Item.save();
    }
}
