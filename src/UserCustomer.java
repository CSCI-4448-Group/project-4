import java.util.Scanner;

public class UserCustomer {
    private Invoker invoker_;
    private Clerk receiver_;
    
    public UserCustomer(Clerk receiver, Invoker invoker) {
        invoker_ = invoker;
        receiver_ = receiver;
    }

    private void print_options() {
        System.out.println("\nSelect option by entering the desired number:");
        System.out.println("1. Select a store");
        System.out.println("2. Buy a custom guitar kit from the store");
        System.out.println("3. Get the name of the clerk you are talking to");
        System.out.println("4. Get the current time");
        System.out.println("5. Sell an item to the store");
        System.out.println("6. Buy an item from the store");
        System.out.println("7. End interactions and leave the store\n");
    }

    public void set_receiver(Clerk receiver) {
        receiver_ = receiver;
    }

    public void begin_options(Clerk north_clerk, Clerk south_clerk) {
        boolean running = true;
        Scanner reader = new Scanner(System.in);
        print_options();
        while (running) {
            System.out.println("What would you like to do?");
            int choice = -1;
            boolean valid = false;
            do {
                valid = false;
                try {
                    choice = Integer.parseInt(reader.nextLine());
                    if (choice < 0 || choice > 7) {
                        System.out.println("Must enter a number 1-7, or 0 to print the options again.");
                    } else {
                        valid = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter an integer number as input.");
                } 
            } while (valid == false);

            switch(choice) {
                case 0:
                    print_options();
                    break;
                case 1: 
                    invoker_.set_slot(new selectStoreCommand(receiver_, north_clerk, south_clerk, this));
                    invoker_.press_button(reader);
                    break;
                case 2:
                    invoker_.set_slot(new buyGuitarKitCommand(receiver_));
                    invoker_.press_button(reader);
                    break;
                case 3:
                    invoker_.set_slot(new printClerkNameCommand(receiver_));
                    invoker_.press_button(reader);
                    break;
                case 4: 
                    invoker_.set_slot(new getCurrentTimeCommand(receiver_));
                    invoker_.press_button(reader);
                    break;
                case 5: 
                    invoker_.set_slot(new sellItemCommand(receiver_));
                    invoker_.press_button(reader);
                    break;
                case 6: 
                    invoker_.set_slot(new buyItemCommand(receiver_));
                    invoker_.press_button(reader);
                    break;
                case 7: 
                    System.out.println("You left the store");
                    running = false;
                    break;
                default:
                    System.out.println("There is an error in the logic, " + choice + " should not be possible.");
                    break;
            }
        }
        reader.close();
    }
}
