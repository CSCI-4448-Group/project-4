import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public abstract class Command {
    private Clerk receiver_;

    public Clerk get_receiver() {return receiver_;}
    public void set_receiver(Clerk receiver) {receiver_ = receiver;}

    public abstract void execute(Scanner reader);
}

class selectStoreCommand extends Command {
    private Clerk north_clerk_;
    private Clerk south_clerk_;
    private UserCustomer user_;

    public selectStoreCommand(Clerk receiver, Clerk north_clerk, Clerk south_clerk, UserCustomer user) {
        set_receiver(receiver);
        north_clerk_ = north_clerk;
        south_clerk_ = south_clerk;
        user_ = user;
    }
    public void execute(Scanner reader) {
        // Used from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        System.out.println("Enter either North Store as '1' or South Store as '2': ");
        int choice = 0;
        boolean valid = false;
        do {
            valid = false;
            try {
                choice = Integer.parseInt(reader.nextLine());
                if (choice != 1 && choice != 2) {
                    System.out.println("Must enter '1' for North Store, or '2' for South Store.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer number as input.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (valid == false);
        if (choice == 1) {
            System.out.println("You are now in the North Store");
            if (get_receiver().get_name() != north_clerk_.get_name()) {
                user_.set_receiver(north_clerk_);
            }
        } else {
            System.out.println("You are now in the South Store");
            if (get_receiver().get_name() != south_clerk_.get_name()) {
                user_.set_receiver(south_clerk_);
            }
        }
        

        // Need to implement option to set store
        // get_receiver().set_store(choice); 
    }
}

class buyGuitarKitCommand extends Command {
    public buyGuitarKitCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute(Scanner reader) {
        System.out.println("You are now buying a guitar kit");
        // receiver_.buy_guitar_kit();
    }
}

class printClerkNameCommand extends Command {
    public printClerkNameCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute(Scanner reader) {
        System.out.println("The clerk's name is " + get_receiver().get_name());
    }
}

class getCurrentTimeCommand extends Command {
    public getCurrentTimeCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute(Scanner reader) {
        // Used from https://stackabuse.com/how-to-get-current-date-and-time-in-java/
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        System.out.println("The current time is " + formatter.format(Calendar.getInstance().getTime()));
    }
}

class sellItemCommand extends Command {
    public sellItemCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute(Scanner reader) {
        Random rand = new Random();
        ArrayList<String> types = Inventory.get_item_types();
        String type = types.get(rand.nextInt(types.size()));
        try {
            Item item = Item.generate_item(type);
            get_receiver().user_sells_item(item, reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class buyItemCommand extends Command {
    public buyItemCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute(Scanner reader) {
        get_receiver().user_buys_item(reader);
    }
}