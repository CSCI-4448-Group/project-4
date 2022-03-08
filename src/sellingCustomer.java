import java.util.ArrayList;
import java.util.Random;

public class sellingCustomer extends Customer {

    private Item sellingItem_; // Customer wants to sell an item

    // Initialize Selling Customer
    public sellingCustomer (String name, ArrayList<String> item_types) throws Exception {
        super(name);
        initializeSellingCustomer(item_types);
    }

    public void initializeSellingCustomer (ArrayList<String> item_types) throws Exception {
        // Get a list of strings of the types of items
        Random rand = new Random();

        // Gets a random type of item from list
        String type = item_types.get(rand.nextInt(item_types.size()));

        // Generates a random item of the type
        sellingItem_ = Item.generate_item(type);
    }


    //Returns item customer wants to sell
    public Item get_item() {return sellingItem_;}
}
