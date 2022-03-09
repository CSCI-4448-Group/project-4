import java.util.Random;
import java.util.ArrayList;
class main_class {

    public static void run_day(Store fnms, Clerk current_clerk) throws Exception
    {
        //Set logger to track current store and current clerk
        Logger.getInstance().Logger_set(fnms,current_clerk);

        //Set Tracker to track current store and current clerk
        Tracker.getInstance(fnms).registerClerk(current_clerk);

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

    public static void begin_user_day(Store FNMSNorth, Store FNMSSouth, Clerk north_clerk, Clerk south_clerk) throws Exception
    {
        Logger.getInstance().Logger_set(FNMSNorth, north_clerk);


        Tracker.getInstance(FNMSNorth).registerClerk(north_clerk);

        // Clerk arrives at store
        north_clerk.arrive_at_store();

        // Clerk checks the register, goes to bank as necessary
        north_clerk.check_register();

        // Clerk checks inventory, places orders for new items if none are present in the store
        north_clerk.do_inventory();

        Logger.getInstance().Logger_set(FNMSSouth, south_clerk);

        Tracker.getInstance(FNMSSouth).registerClerk(south_clerk);

        // Clerk arrives at store
        south_clerk.arrive_at_store();

        // Clerk checks the register, goes to bank as necessary
        south_clerk.check_register();

        // Clerk checks inventory, places orders for new items if none are present in the store
        south_clerk.do_inventory();

        UserCustomer user = new UserCustomer(north_clerk, new Invoker());
        user.begin_options(north_clerk, south_clerk);

        // Clerk cleans the store, possibly damages items in the process
        north_clerk.clean_store();

        // Clerk announces he leaves the store
        north_clerk.leave_store();

        // Clerk cleans the store, possibly damages items in the process
        south_clerk.clean_store();

        // Clerk announces he leaves the store
        south_clerk.leave_store();
    }

    // Possible way to handle announcements, may make it easier?

     public static void print_final_messages(Store fnms)
     {
         System.out.println("Following a 30 day simulation, the following summary of the " + fnms.get_name() + "'s activities is printed below: ");
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
         // get_register returns a register object, get_amount returns the amount as a double that's currently in the register
         System.out.println("The final amount of money in the cash register is: ");
         System.out.println("$" + Double.toString(fnms.get_register().get_amount()));
         System.out.println("\n");

         // How much money was added to the register from the GoToBank Action
         // get_bank_withdrawals returns the total amount as a double that was taken out of the bank.
         System.out.println("The amount of money added to the register form the GoToBank action was: ");
         System.out.println("$" + Double.toString(fnms.get_register().get_bank_withdrawals()));
         System.out.println("\n");
     }

    public static void runFnmsSimulation() throws Exception {
        // Initialize store and two clerk objects
        EmployeePool empPool = EmployeePool.getInstance();
        Store FNMSNorth = new Store("FNMSNorth", new NorthGuitarKitFactory());
        Store FNMSSouth = new Store("FNMSSouth", new SouthGuitarKitFactory());

        // Initialize list of stores
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(FNMSNorth);
        stores.add(FNMSSouth);

        //Set the random user_customer day
        Random rand = new Random();
        int randomDay = 10 + rand.nextInt(20);
        if (randomDay % 7 == 0) {randomDay++;}
        // Main program loop
        // Run loop for 30 days, calling begin_day each time, and print out a delineator between each day.
        for (int i = 0; i < 30; i++)
        {

            System.out.println("===========================================");
            
            if ((i + 1) % 7 == 0) {
                System.out.println("Day " + Integer.toString(i + 1) + " is a Sunday, so the store did not open.");
                FNMSNorth.get_calendar().incr_current_day();
                FNMSSouth.get_calendar().incr_current_day();
                empPool.reset_days();
            } else {
                ArrayList<Clerk> chosenClerks = empPool.get_clerks_of_day(2,stores);
                if (randomDay == FNMSNorth.get_calendar().get_current_day()) {
                    begin_user_day(FNMSNorth, FNMSSouth, chosenClerks.get(0), chosenClerks.get(1));
                } else {
                    run_day(FNMSNorth, chosenClerks.get(0));
                    run_day(FNMSSouth, chosenClerks.get(1));
                }
            }

            System.out.println("===========================================");
            System.out.println("\n");
        }
        // Prints the summary / final messages
        print_final_messages(FNMSNorth);
        print_final_messages(FNMSSouth);
    }

    public static void main(String[] args) throws Exception {


        // Run the store simulation
        runFnmsSimulation();



    }
}