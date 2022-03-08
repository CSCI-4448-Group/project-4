import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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
    }
}

class buyGuitarKitCommand extends Command {
    public buyGuitarKitCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute(Scanner reader) {
        GuitarKitFactory factory = get_receiver().get_store().get_factory();
        String[] types = {"Bridge", "Knobset", "Covers", "Neck", "Pickguard", "Pickups"};
        String choice = "Z";

        ArrayList<Integer> choices = new ArrayList<>();
        String[] north_chars = {"A", "B", "C"};
        String[] south_chars = {"A", "B", "D"};
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 3);

        String[] chars = get_receiver().get_store().get_name() == "FNMSNorth" ? north_chars : south_chars;

        String item_name = "Guitar kit containing:";
        Double total_cost = 0.0;

        System.out.println("You are buying a Guitar Kit.");

        while (choices.size() < types.length) {
            int current = choices.size();
            choice = "";
            while (!chars[0].equals(choice) && !chars[1].equals(choice) && !chars[2].equals(choice)) {
                try {
                    chars = get_receiver().get_store().get_name() == "FNMSNorth" ? north_chars : south_chars;
                    System.out.println("Would you like " + types[current] + chars[0] + ", " + types[current] + chars[1] + ", or " + types[current] + chars[2] + "?");
                    choice = reader.nextLine();
                    if (!chars[0].equals(choice) && !chars[1].equals(choice) && !chars[2].equals(choice)) {
                        System.out.println("You must enter " + chars[0] + ", " + chars[1] + ", or " + chars[2] + ".");
                    }
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
            item_name += " " + types[current] + choice + ",";
            choices.add(map.get(choice));
        }

        total_cost += factory.createBridge(choices.get(0)).get_cost();
        total_cost += factory.createKnobset(choices.get(1)).get_cost();
        total_cost += factory.createCovers(choices.get(2)).get_cost();
        total_cost += factory.createNeck(choices.get(3)).get_cost();
        total_cost += factory.createPickguard(choices.get(4)).get_cost();
        total_cost += factory.createPickups(choices.get(5)).get_cost();


        Item kit = new GuitarKitItem(item_name, total_cost/2, total_cost, true, get_receiver().get_store().get_calendar().get_current_day(), Condition.randomCondition(), total_cost);
        get_receiver().sell_item(kit, total_cost);

        System.out.println(get_receiver().get_name() + " sold a " + kit.get_name() + " to you for $" + String.format("%.2f",kit.get_sale_price()));
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
        ArrayList<String> types = get_receiver().get_store().get_inventory().get_item_types();
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