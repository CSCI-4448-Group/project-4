import java.util.Scanner;

public class Invoker {
    
    private Command slot;

    public Invoker() {}

    public void set_slot(Command command) {
        slot = command;
    }

    public void press_button(Scanner reader) {
        slot.execute(reader);
    }
}
