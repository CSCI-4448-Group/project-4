public class Invoker {
    private Command command_store_slot;
    private Command command_buy_guitar_kit_slot;
    private Command command_get_clerk_name_slot;
    private Command command_get_time_slot;
    private Command command_sell_item_slot;
    private Command command_buy_item_slot;
    private Command command_end_slot;

    public Invoker() {}

    public void set_store_slot(Command command) {
        command_store_slot = command;
    }
    public void set_guitar_kit_slot(Command command) {
        command_buy_guitar_kit_slot = command;
    }
    public void set_get_clerk_name_slot(Command command) {
        command_get_clerk_name_slot = command;
    }
    public void set_get_time_slot(Command command) {
        command_get_time_slot = command;
    }
    public void set_sell_item_slot(Command command) {
        command_sell_item_slot = command;
    }
    public void set_buy_item_slot(Command command) {
        command_buy_item_slot = command;
    }
    public void set_end_slot(Command command) {
        command_end_slot = command;
    }

    public void press_store() {
        command_store_slot.execute();
    }
    public void press_buy_guitar_kit() {
        command_buy_guitar_kit_slot.execute();
    }
    public void press_get_clerk_name() {
        command_get_clerk_name_slot.execute();
    }
    public void press_get_time() {
        command_get_time_slot.execute();
    }
    public void press_sell_item() {
        command_sell_item_slot.execute();
    }
    public void press_buy_item() {
        command_buy_item_slot.execute();
    }
    public void press_end() {
        command_end_slot.execute();
    }


    

}
