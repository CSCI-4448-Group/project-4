public class Invoker {
    
    private Command slot;

    public Invoker() {}

    public void set_slot(Command command) {
        slot = command;
    }

    public void press_button() {
        slot.execute();
    }
}
