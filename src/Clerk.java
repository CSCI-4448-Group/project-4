import java.util.*;

public class Clerk extends Employee implements Subject {

    private TuneBehavior tuneBehavior_;
    private ArrayList<Observer> observersList_ = new ArrayList<Observer>(); // Track an observers list of event consumers who are tracking publishes by the Clerk
    public String announcement_; // Define an announcement to hold the announcements from the Clerk which are sent to notifyObservers
    private int damagedCounter = 0; // Track the number of items damaged by the clerk

    // Construct clerk with name, store, TuneBehavior (Strategy Pattern)
    public Clerk(String name, Store s, TuneBehavior tuneBehavior) {
        super(name,s);
        tuneBehavior_ = tuneBehavior;
    }

    // Implement register observers which just appends the observer to the observer list
    @Override
    public void registerObserver(Observer o) {
        observersList_.add(o);
    }

    // Remove observers by clearing observers list
    @Override
    public void removeObserver() {
        observersList_.clear();
    }

    // Go through each observer in observers list and call update function which will notify event consumers
    @Override
    public void notifyObservers(String announcement) {
        for (Observer o : observersList_) {
            o.update(announcement);
        }
    }

    public int getRandomNumber(int min, int max) //https://www.baeldung.com/java-generating-random-numbers-in-range
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Strategy pattern setter
    public void set_tune_behavior(TuneBehavior tuneBehavior){
        tuneBehavior_ = tuneBehavior;
    }

    public void perform_tune(Item item){
        tuneBehavior_.tune(item);
    }

    //Set all items arriving today to have currDay arrival date, add all items to inventory
    private void process_incoming_items(int currDay) {
        Store s = get_store();
        ArrayList<Item> incoming = s.get_ordered().get(currDay); //Get all the items from the map
        incoming.forEach((item)->item.set_day_arrived(currDay));  //Set all their arrival dates to current day

        // Observer pattern for logger
        announcement_ = incoming.size() + " number of items arrived at the store on Day " + currDay;
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        s.get_inventory().put_items(incoming); //Add all the items to the inventory
        s.get_ordered().remove(currDay); //Remove items from orderedItems_
    }

    //Arrive at store and check if items need to be processed
    public void arrive_at_store(){
        int currDay = get_store().get_calendar().get_current_day();
        System.out.println(get_name() + " arrives at the store on Day " + currDay);

        // Observer pattern for logger
        announcement_ = get_name() + " arrives at the store on Day " + currDay;
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        if(get_store().get_ordered().containsKey(currDay)){ //If there are ordered items that arrive today
            process_incoming_items(currDay);
        }
    }


    //Check amount in register, go_to_bank if less than 75 (REMOVE MAGIC NUMBERS)
    public void check_register(){
        double currentAmount = get_store().get_register().get_amount();
        System.out.println(get_name() + " is checking the register and there is $" + String.format("%.2f",currentAmount));

        // Observer pattern for logger
        announcement_ = get_name() + " is checking the register and there is $" + String.format("%.2f",currentAmount);
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        if(currentAmount < 75) {
            go_to_bank();
        }
    }

    //Go to the bank, withdrawal 1000 dollars, add it to the register, tally the withdrawal (REMOVE MAGIC NUMBERS)
    public void go_to_bank(){
        CashRegister reg = get_store().get_register();
        reg.set_amount(reg.get_amount() + 1000);
        reg.set_bank_withdrawal(reg.get_bank_withdrawals() + 1000);
        System.out.println(get_name() + " withdrew 1000 dollars from the bank and the new balance in the register is " + String.format("%.2f",reg.get_amount()) + " dollars");

        // Observer pattern for logger
        announcement_ = get_name() + " withdrew 1000 dollars from the bank and the new balance in the register is " + String.format("%.2f",reg.get_amount()) + " dollars";
        notifyObservers("logger: " + announcement_);
        announcement_ = "";
    }

    //Scan the current inventory, if we have 0 count of any type of item, order 3 of them
    public void do_inventory() throws Exception{
        int tuneDamagedItems = 0;
        Store s = get_store();
        Inventory inv = s.get_inventory();
        for(Map.Entry<String, ArrayList<Item>> entry : inv.get_mapping().entrySet()) { //For each entry in our inventory map
            if (entry.getValue().isEmpty() && !s.search_ordered_item_type(entry.getKey()) && !s.is_discontinued(entry.getKey())) { //If we have 0 count of any items in our current inventory && they have not already been ordered
                place_order(entry.getKey()); //Order that item
            }
        }

        for(Item item : inv.flatten_inventory()){
            if(item instanceof Instrument || item instanceof Players) {
                boolean prev_tune_state = item.get_tuned();
                perform_tune(item);
                boolean post_tune_state = item.get_tuned();
                boolean tuneDamage = check_tune_damage(prev_tune_state, post_tune_state, item);
                if (tuneDamage) {
                    tuneDamagedItems += 1;
                }
            }
        }

        System.out.println("The sum of today's inventory is $" + String.format("%.2f",inv.get_purch_price_sum())); //Display the list price sum of all items in inventory

        // Observer pattern for logger
        announcement_ = "The total number of items in the inventory is " + inv.flatten_inventory().size();
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        // Observer pattern for logger
        announcement_ = "The sum of today's inventory is $" + String.format("%.2f",inv.get_purch_price_sum());
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        // Observer pattern for logger
        announcement_ = "The total number of items damaged in tuning is " + tuneDamagedItems;
        notifyObservers("logger: " + announcement_);
        announcement_ = "";
    }

    private boolean check_tune_damage(boolean old_state, boolean new_state, Item item){
        boolean isDamaged = false;
        Random rand = new Random();
        //if (the item was tuned and now it isn't) and (we win the damage roll)
        if((old_state && !new_state) && (rand.nextInt(100) < 10)){
            System.out.println(get_name() + " damaged " + item.get_name() + " when attempting to tune");
            isDamaged = true;
            damage_item(item); //damage the item
        }
        return isDamaged;
    }

    private void damage_item(Item item){
        if(item.get_condition().get_condition() == "poor"){ //If the item breaks
            System.out.println(get_name() + " damaged " + item.get_name() + " and broke it.");
            get_store().get_inventory().remove_item(item);
        }
        else{ //Reduce the items condition by one level, reduce the items listPrice by 20%
            item.get_condition().decreaseCondition(); //Decrease the items condition
            System.out.println(get_name() + " damaged " + item.get_name() + " and its condition is now " + item.get_condition().get_condition());
            System.out.println("The price of the item will be reduced from $" + String.format("%.2f",item.get_list_price()) + " to $" + String.format("%.2f",item.get_list_price() * .8));
            item.set_list_price(item.get_list_price() * .8);
        }
        // Observer pattern for tracker for name of employee with new item damaged
        notifyObservers("tracker: " + get_name() + ",0,0,1");
    }

    //Adds 3 items of type passed to orderedItems_ map in form of <Day Arriving, List Of Items>
    public void place_order(String type) throws Exception{
        Random rand = new Random();
        Store s = get_store();
        CashRegister reg = s.get_register();
        double total_spent_on_order = 0;
        ArrayList<Item> items = generate_items(type.toLowerCase(), 3); //Generate 3 of the type of items asked for

        // Observer pattern for logger
        announcement_ = "The total number of items ordered is " + items.size();
        notifyObservers("logger: " + announcement_);
        // Observer pattern for tracker with three items purchased
        notifyObservers("tracker: " + get_name() + ",0,3,0");
        announcement_ = "";

        // Updates the register with the 
        for (Item item : items) {
            reg.set_amount(reg.get_amount() - item.get_purch_price());
            total_spent_on_order += item.get_purch_price();
        }
        int arrivalDay = s.get_calendar().get_current_day() + rand.nextInt(3) + 1; //Generate random arrival day
        if ((arrivalDay) % 7 == 0) {
            arrivalDay++;
        }

        if(s.get_ordered().containsKey(arrivalDay)){ //If there is already an entry for that arrival day
            s.get_ordered().get(arrivalDay).addAll(items); //Add to items arriving that day
        }
        else{
            s.get_ordered().put(arrivalDay, items); //map the ordered items from (day Arriving) -> (the items created)
        }
        System.out.println(get_name() + " spent $" + String.format("%.2f",total_spent_on_order) + " to place an order for 3 " + type + ", arriving on day " + Integer.toString(arrivalDay) + ".");
    }

    //Generate numItems items of type provided, return generated ArrayList
    private ArrayList<Item> generate_items(String type, int numItems) throws Exception{
        ArrayList<Item> items = new ArrayList<Item>();
        for(int i = 0; i < numItems; i++){ //For the number of items we need
            items.add(Item.generate_item(type)); //Call the item generator for the type of item we need
        }
        return items;
    }

    // https://stackoverflow.com/questions/9832919/generate-poisson-arrival-in-java
    // This function generates a poisson distribution around the parameter mean and returns a value for the number of buying customers
    private static int getPoissonRandom(double mean)
    {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do
        {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    private ArrayList<buyingCustomer> generateBuyingCustomers(){
        ArrayList<buyingCustomer> buyCustomers = new ArrayList<buyingCustomer>();

        // getPoisson returns buying customers from a poisson distribution centered at mean 3
        int randBuyers = 2 + getPoissonRandom(3);
        System.out.println("The number of random buyers (from a Poisson Distribution is): " + randBuyers);

        for (int i = 1; i < randBuyers + 1; i++)
        {
            buyCustomers.add(new buyingCustomer("Buying Customer " + i));
        }
        return buyCustomers;
    }

    private ArrayList<sellingCustomer> generateSellingCustomers() throws Exception{
        ArrayList<sellingCustomer> sellCustomers = new ArrayList<sellingCustomer>();

        // getRandomNumber is exclusive to the maximum so this returns a number between [1, 4] inclusive.
        int randSellers = getRandomNumber(1, 5);

        for (int i = 1; i < randSellers + 1; i++)
        {
            sellCustomers.add(new sellingCustomer("Selling Customer " + i));
        }
        return sellCustomers;
    }


    public void open_store() throws Exception{
        int soldItemsCounter = 0;
        int boughtItemsCounter = 0;
        if (get_store().get_calendar().get_current_day() == 15) {
            UserCustomer user = new UserCustomer(this, new Invoker());
            user.begin_options();
        } else {
            ArrayList<buyingCustomer> buyCustomers = generateBuyingCustomers();
            for(buyingCustomer buyer : buyCustomers){ //For each buying customer
                if(get_store().get_inventory().get_items_of_type(buyer.get_wanted_type()).isEmpty()){ //If there are no items of type that customer wants
                    System.out.println(buyer.get_name() + " tried to buy a " + buyer.get_wanted_type() + " but none were available");
                    continue;
                }
                boolean didSell = attempt_sale(buyer,get_store().get_inventory().get_items_of_type(buyer.get_wanted_type()).get(0)); //Attempt to sell the first item of appropriate type
                if (didSell) {
                    soldItemsCounter += 1;
                }
            }

            ArrayList<sellingCustomer> sellCustomers = generateSellingCustomers();
            for(sellingCustomer seller : sellCustomers){ //For each selling customer
                boolean didBuy = attempt_purchase(seller, seller.get_item()); //Attempt to buy their item
                if (didBuy) {
                    boughtItemsCounter += 1;
                }
            }
        }

        // Observer pattern for logger
        announcement_ = "The total number of items sold by " + get_name() + " on day " + get_store().get_calendar().get_current_day() + " is " + soldItemsCounter;
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        // Observer pattern for logger
        announcement_ = "The total number of items bought by "+ get_name() + " on day " + get_store().get_calendar().get_current_day() + " is " + boughtItemsCounter;
        notifyObservers("logger: " + announcement_);
        announcement_ = "";
    }

    //If the item is a tunable type, and it is tuned, return a bonus chance of sale
    int calc_bonus_chance(Item item){
        if(item instanceof Players && item.get_tuned()){
            return 10;
        }
        if(item instanceof Stringed && item.get_tuned()){
            return 15;
        }
        if(item instanceof Wind && item.get_tuned()){
            return 20;
        }
        return 0;
    }

    private boolean user_input(Scanner reader, Double price) {
        String choice = "";
        while (!choice.equals("y") && !choice.equals("n")) {
            try {
                System.out.println("Will you accept an offer of " + String.format("%.2f",price) + " for your item?");
                choice = reader.nextLine();
                if (!choice.equals("y") && !choice.equals("n")) {
                    System.out.println("You must enter 'y' for yes, or 'n' for no.");
                }
            } catch (Exception e) {
                System.out.println("Error: You must enter 'y' for yes, or 'n' for no.");
            }
        }
        return choice.equals("y");
    }

    public boolean sell_user_item(Item toBuyItem, Scanner reader) {
        double purchPrice = evaluate_item(toBuyItem);
        if(get_store().is_discontinued(toBuyItem.get_item_type())){
            System.out.println("You tried to sell a " + toBuyItem.get_name() + " but the store no longer purchases these");
            return false;
        }
        System.out.println("You want to sell your " + toBuyItem.get_name() + " to " + get_name() + ".");
        if(user_input(reader, purchPrice)){
            System.out.println(get_name() + " bought a " + toBuyItem.get_condition().get_condition() + " condition " + toBuyItem.get_new_or_used() + " " + toBuyItem.get_name() + " from you for $" + String.format("%.2f",purchPrice));
            purch_item(toBuyItem,purchPrice);
            return true;
        }
        else if (user_input(reader, purchPrice*1.1)) {
            System.out.println(get_name() + " bought a " + toBuyItem.get_condition().get_condition() + " condition " + toBuyItem.get_new_or_used() + " " + toBuyItem.get_name() + " from you for $" + String.format("%.2f",purchPrice*1.1) + " after a 10% offer increase.");
            purch_item(toBuyItem, purchPrice*1.1);
            return true;
        }
        else{
            System.out.println(get_name() + " tried buying a " + toBuyItem.get_condition().get_condition() + " condition " + toBuyItem.get_new_or_used() + " " + toBuyItem.get_name() + " from you for $" + String.format("%.2f",purchPrice*1.1) + " but customer refused.");
            return false;
        }
    }

    private boolean attempt_sale(buyingCustomer buyer, Item toSellItem){
        int bonus_sell_chance = calc_bonus_chance(toSellItem); //Check if the item has a bonus_sell_chance
        if (buyer.haggle_roll(50 + bonus_sell_chance)){ //If we roll 50% chance and win, sell full price
            sell_item(toSellItem, toSellItem.get_list_price());
            System.out.println(get_name() + " sold a " + toSellItem.get_name() + " to " + buyer.get_name() + " for $" + String.format("%.2f",toSellItem.get_sale_price()));

            // If the item is a subclass of 'Stringed' we want to decorate it with addons
            if (Stringed.class.isAssignableFrom(toSellItem.getClass())) {
                toSellItem = decorate_sale(toSellItem);
            }
            return true;
        }
        else if(buyer.haggle_roll(75 + bonus_sell_chance)){ //else if we roll 75% chance and win, sell 90% full price
            sell_item(toSellItem, toSellItem.get_list_price()*.9);
            System.out.println(get_name() + " sold a " + toSellItem.get_name() + " to " + buyer.get_name() + " for $" + String.format("%.2f",toSellItem.get_sale_price()) + " after a 10% discount.");
                        
            // If the item is a subclass of 'Stringed' we want to decorate it with addons
            if (Stringed.class.isAssignableFrom(toSellItem.getClass())) {
                toSellItem = decorate_sale(toSellItem);
            }
            return true;
        }
        else{
            System.out.println(get_name() + " tried selling a " + toSellItem.get_condition().get_condition() + " condition " + toSellItem.get_new_or_used() + " " + toSellItem.get_name() + " to " + toSellItem.get_name() + " for $" + String.format("%.2f",toSellItem.get_list_price()) + " but customer refused.");
            return false;
        }
    }

    private boolean attempt_purchase(sellingCustomer seller, Item toBuyItem) {
        double purchPrice = evaluate_item(toBuyItem);
        if(get_store().is_discontinued(toBuyItem.get_item_type())){
            System.out.println(seller.get_name() + " tried to sell a " + toBuyItem.get_name() + " but the store no longer purchases these");
            return false;
        }
        if(seller.haggle_roll(50)){
            System.out.println(get_name() + " bought a " + toBuyItem.get_condition().get_condition() + " condition " + toBuyItem.get_new_or_used() + " " + toBuyItem.get_name() + " from " + seller.get_name() + " for $" + String.format("%.2f",purchPrice));
            purch_item(toBuyItem,purchPrice);
            return true;
        }
        else if(seller.haggle_roll(75)){
            System.out.println(get_name() + " bought a " + toBuyItem.get_condition().get_condition() + " condition " + toBuyItem.get_new_or_used() + " " + toBuyItem.get_name() + " from " + seller.get_name() + " for $" + String.format("%.2f",purchPrice) + " after a 10% offer increase.");
            purch_item(toBuyItem, purchPrice*1.1);
            return true;
        }
        else{
            System.out.println(get_name() + " tried buying a " + toBuyItem.get_condition().get_condition() + " condition " + toBuyItem.get_new_or_used() + " " + toBuyItem.get_name() + " from " + seller.get_name() + " for $" + String.format("%.2f",purchPrice) + " but customer refused.");
            return false;
        }
    }

    // Only applies to stringed items right now, but can be modified later to others. 
    // Sells addon items depending on random chances, using decorator pattern
    private Item decorate_sale(Item soldItem) {
        Store s = get_store();
        if (Stringed.class.isAssignableFrom(soldItem.getClass())) {
            Decorator dec = new Decorator();
            soldItem = dec.run((Stringed)soldItem, s, this);
        }
        return soldItem;
    }

    protected void sell_item(Item soldItem, double soldPrice){
        Store s = get_store();
        s.add_to_sold(soldItem);
        s.remove_from_inventory(soldItem);
        s.get_register().set_amount(s.get_register().get_amount() + soldPrice); //Set register amount
        soldItem.set_day_sold(s.get_calendar().get_current_day()); //Set items sold date to current day
        soldItem.set_sale_price(soldPrice); //Set items sold price
        notifyObservers("tracker: " + get_name() + ",1,0,0");
    }

    private void purch_item(Item purchItem, double purchPrice){
        Store s = get_store();
        s.add_to_inventory(purchItem);
        purchItem.set_purch_price(purchPrice);
        s.get_register().set_amount(s.get_register().get_amount() - purchPrice); //Set register amount
        notifyObservers("tracker: " + get_name() + ",0,1,0");
    }

    private double evaluate_item(Item item){
        Random rand = new Random(); //Too many new randoms
        Condition cond = Condition.randomCondition(); //Get a random condition for item
        item.set_condition(cond); //Set the items condition
        item.set_is_new(rand.nextBoolean()); //Set the items isNew to random
        return calculate_condition_price(cond); //Return random price based on items condition
    }

    private double calculate_condition_price(Condition cond){
        Random rand = new Random();
        switch(cond.get_condition().toLowerCase()){ //Based on the string representation of condition
            case("excellent"):
                return rand.nextInt(11) + 40; //rand number from 40-50
            case("very good"):
                return rand.nextInt(11) + 30;//rand number from 30-40
            case("good"):
                return rand.nextInt(11) + 20;//rand number from 20-30
            case("fair"):
                return rand.nextInt(11) + 10;//rand number from 10-20
            case("poor"):
                return rand.nextInt(10) + 1; //rand number from 1-10
            default:
                throw new IllegalArgumentException("Invalid condition type passed to generate_price" + cond.get_condition());
        }
    }

    public void clean_store() { //The size of this function needs to be reduced!
        Random rand = new Random();
        String name = get_name();
        Inventory inv = get_store().get_inventory();
        double damage_chance;

        if (name == "Shaggy") {
            damage_chance = 20;
        } else if (name == "Velma") {
            damage_chance = 5;
        } else {
            damage_chance = 50;
        }
        //Increment calendar day

        //If the roll for a damaging an item fails, finish cleaning the store and return from fxn
        if (rand.nextInt(100) > damage_chance) {
            System.out.println(name + " finished cleaning the store.");
            return;
        }

        //Otherwise proceed with damaging an item
        Item damagedItem = inv.flatten_inventory().get(rand.nextInt(inv.flatten_inventory().size()));//Flatten the inventory into a list of items and pick a random item to damage
        damagedCounter += 1;
        damage_item(damagedItem);
        System.out.println(name + " finished cleaning the store.");

        // Observer pattern for logger
        announcement_ = "The total number of items damaged in cleaning is " + damagedCounter;
        notifyObservers("logger: " + announcement_);
        announcement_ = "";
    }


    // Pack up the store for the day. Increase days worked and increment current day. Announce that the store is closed
    public void leave_store(){
        get_store().get_calendar().incr_current_day();
        incr_days_worked();
        System.out.println(get_name() + " locked up the store and went home for the day");

        // Observer pattern for logger
        announcement_ = get_name() + " locked up the store and went home for the day";
        notifyObservers("logger: " + announcement_);
        announcement_ = "";

        // Observer pattern for tracker (EOD print statement)
        notifyObservers("print:");
        removeObserver();
    }
}