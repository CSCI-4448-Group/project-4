import java.util.Random;
import java.util.ArrayList;
class main_class {

    public static void begin_day(Store fnms, Clerk current_clerk, Tracker tracker) throws Exception
    {
        Logger loggerGuy;
        loggerGuy = Logger.getInstance();
        loggerGuy.Logger_set(fnms, current_clerk);

        tracker.registerClerk(current_clerk);

        // Clerk arrives at store
        current_clerk.arrive_at_store();

        // Clerk checks the register, goes to bank as necessary
        current_clerk.check_register();

        // Clerk checks inventory, places orders for new items if none are present in the store
        current_clerk.do_inventory();

        // Clerk opens the store, handles all customer interactions
        current_clerk.open_store();

        // Clerk cleans the store, possibly damages items in the process
        current_clerk.clean_store();

        // Clerk announces he leaves the store
        current_clerk.leave_store();
    }

    // Possible way to handle announcements, may make it easier?

     public static void print_final_messages(Store fnms)
     {
         System.out.println("Following a 30 day simulation, the following summary of the store's activities is printed below: ");
         System.out.println();

         // Inventory print statement including items themselves and total value
         // Calls flatten inventory, which returns an ArrayList of all items still in inventory
         // get_inventory returns an inventory object, and we then flatten the object to get the ArrayList.
         System.out.println("Inventory still in store and value: ");
         ArrayList<Item> finalInventory = fnms.get_inventory().flatten_inventory();
         for (int i = 0; i < finalInventory.size(); i++)
         {
             // Prints the name of each item in inventory.
             System.out.println(finalInventory.get(i).get_name());
         }
         System.out.println("\n");

         // get_purchase_price_sum gets the sum of all item values in inventory.
         String inventoryValue = Double.toString(fnms.get_inventory().get_list_price_sum());
         System.out.println("The total value of inventory still in the store is: " + inventoryValue);
         System.out.println("\n");

         // Sold items print items themselves with daySold and salePrice as well as total of salePrice
         // get_sold_items returns an arrayList of items sold
         System.out.println("Sold items from the store: ");
         ArrayList<Item> finalSold = fnms.get_sold_items();
         System.out.println("\n");

         // Declare a tracker which maintains the total count of our sold item prices.
         double soldItemSum = 0.0;
         for (int i = 0; i < finalSold.size(); i++)
         {
             // get_day_sold returns the day as an integer which the particular item was sold
             // get_sale_price returns the sale price of the item
             System.out.println(finalSold.get(i).get_name() + " was sold on day " + Integer.toString(finalSold.get(i).get_day_sold()) + " for " + Double.toString(finalSold.get(i).get_sale_price()));
             soldItemSum += finalSold.get(i).get_sale_price();
         }
         System.out.println("\n");
         System.out.println("The total value of items sold is: " + Double.toString(soldItemSum));
         System.out.println("\n");

         // The final count of money in the cash register
         // get_register returns a register object, get_ammount returns the amount as a double that's currently in the register
         System.out.println("The final amount of money in the cash register is: ");
         System.out.println("$" + Double.toString(fnms.get_register().get_amount()));
         System.out.println("\n");

         // How much money was added to the register from the GoToBank Action
         // get_bank_withdrawals returns the total amount as a double that was taken out of the bank.
         System.out.println("The amount of money added to the register form the GoToBank action was: ");
         System.out.println("$" + Double.toString(fnms.get_register().get_bank_withdrawals()));
         System.out.println("\n");
     }

    public static void runFnmsSimulation(Store FNMS, Tracker tracker) throws Exception {
        // Main program loop
        // Run loop for 30 days, calling begin_day each time, and print out a delineator between each day.

        // Choose a random number (either 0 or 1) and assign the respective clerk to work for that day.

        for (int i = 0; i < 30; i ++)
        {

            System.out.println("===========================================");
            
            if ((i + 1) % 7 == 0) {
                System.out.println("Day " + Integer.toString(i + 1) + " is a Sunday, so the store did not open.");
                FNMS.get_calendar().incr_current_day();
            } else {
                Clerk current_clerk = FNMS.get_clerk_of_the_day();
                begin_day(FNMS, current_clerk, tracker);
            }

            if ((i+1) % 7 == 0) {
                for(Clerk clerk : FNMS.get_clerks()){
                    clerk.set_days_worked(0);
                }
            }

            System.out.println("===========================================");
            System.out.println("\n");
        }
    }

    public static void main(String[] args) throws Exception {
        // Initialize store and two clerk objects
        Store FNMS = new Store();

        Tracker tracker;
        tracker = Tracker.getInstance(FNMS);


        // Run the store simulation
        runFnmsSimulation(FNMS, tracker);

        // Prints the summary / final messages
        print_final_messages(FNMS);
    }
}