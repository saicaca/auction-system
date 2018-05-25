import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Auction implements Serializable {
    private int itemId;
    private int limit;
    static ArrayList<Auction> list = new ArrayList<>();
    private ArrayList<Bid> bids = new ArrayList<>();

    private Auction(){

    }

    public Auction(int itemId, int limit) {
        this.itemId = itemId;
        this.limit = limit;
    }
    
    public void bid(int price) {
        bids.add(new Bid(Account.get().getId(), price, new Date()));
    }

    public static void save() throws Exception {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("auctions.dat"));
        for (Auction auction : list) {
            output.writeObject(auction);
        }
    }

    public static void read() throws Exception {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("auctions.dat"));
        try {
            while (true) {
                list.add((Auction) input.readObject());
            }
        } catch (EOFException ex) {

        }
    }
}

class Bid {
    int userId;
    int price;
    Date time;

    private Bid() {

    }

    Bid(int userId, int price, Date time) {
        this.userId = userId;
        this.price = price;
        this.time = time;
    }
}