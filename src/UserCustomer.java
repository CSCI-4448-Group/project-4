import java.util.Scanner;

public class UserCustomer {
    private Invoker invoker_;
    private Clerk receiver_;
    
    public UserCustomer(Clerk receiver, Invoker invoker) {
        invoker_ = invoker;
        receiver_ = receiver;
    }

    private void print_options() {
        System.out.println("Select option by entering the desired number:");
        System.out.println("1. Select a store");
        System.out.println("2. Buy a custom guitar kit from the store");
        System.out.println("3. Get the name of the clerk you are talking to");
        System.out.println("4. Get the current time");
        System.out.println("5. Sell an item to the store");
        System.out.println("6. Buy an item from the store");
        System.out.println("7. End interactions and leave the store\n");
        System.out.println("What would you like to do?");
    }

    public void begin_options() {
        boolean running = true;
        Scanner reader = new Scanner(System.in);
        while (running) {
            print_options();
            int choice = 0;
            while (choice < 0 || choice > 7) {
                try {
                    choice = reader.nextInt();
                } catch (Exception e) {
                    System.out.println("Must enter a number 1-7, or 0 to print the options again.");
                }
            }
            reader.close();

            switch(choice) {
                case 0:
                    print_options();
                case 1: 
                    invoker_.set_slot(new selectStoreCommand(receiver_));
                    invoker_.press_button();
                case 2:
                    invoker_.set_slot(new buyGuitarKitCommand(receiver_));
                    invoker_.press_button();
                case 3:
                    invoker_.set_slot(new printClerkNameCommand(receiver_));
                    invoker_.press_button();
                case 4: 
                    invoker_.set_slot(new getCurrentTimeCommand(receiver_));
                    invoker_.press_button();
                case 5: 
                    invoker_.set_slot(new sellItemCommand(receiver_));
                    invoker_.press_button();
                case 6: 
                    invoker_.set_slot(new buyItemCommand(receiver_));
                    invoker_.press_button();
                case 7: 
                    System.out.println("You left the store");
                    running = false;
                default:
                    System.out.println("There is an error in the logic, " + choice + " should not be possible.");
            }
        }
    }
}
