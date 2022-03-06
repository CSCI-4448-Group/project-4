import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public abstract class Command {
    private Clerk receiver_;

    public Clerk get_receiver() {return receiver_;}
    public void set_receiver(Clerk receiver) {receiver_ = receiver;}

    public abstract void execute();
}

class selectStoreCommand extends Command {
    public selectStoreCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        // Used from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter either store 1 as '1' or store 2 as '2': ");
        int choice = 0;
        while (choice != 1 && choice != 2) {
            try {
                choice = reader.nextInt();
            } catch (Exception e) {
                System.out.println("Must enter '1' for store 1, or '2' for store 2.");
            }
        }
        // Scans the next token of the input as an int.
        reader.close();

        // Need to implement option to set store
        // get_receiver().set_store(choice); 
    }
}

class buyGuitarKitCommand extends Command {
    public buyGuitarKitCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        // receiver_.buy_guitar_kit();
    }
}

class printClerkNameCommand extends Command {
    public printClerkNameCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        System.out.println("The clerk's name is " + get_receiver().get_name());
    }
}

class getCurrentTimeCommand extends Command {
    public getCurrentTimeCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        // Used from https://stackabuse.com/how-to-get-current-date-and-time-in-java/
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("The current time is " + formatter.format(Calendar.getInstance().getTime()));
    }
}

class sellItemCommand extends Command {
    public sellItemCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        Random rand = new Random();
        ArrayList<String> types = Inventory.get_item_types();
        String type = types.get(rand.nextInt(types.size()));
        try {
            Item item = Item.generate_item(type);
            // receiver_.sell_user_item(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class buyItemCommand extends Command {
    public buyItemCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        // receiver_.buy_user_item();
    }
}