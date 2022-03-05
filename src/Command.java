import java.util.Scanner;

public abstract class Command {
    private Clerk receiver_;

    public Clerk get_receiver() {return receiver_;}
    public void set_receiver(Clerk receiver) {receiver_ = receiver;}

    public abstract void execute();
}

class selectStoreCommand extends Command {
    public selectStoreCommand(Clerk receiver) {
        set_receiver(receiver);
    }
    public void execute() {
        // Used from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter either store 1 as '1' or store 2 as '2': ");
        int choice = 0;
        while (choice != 1 && choice != 2) {
            try {
                choice = reader.nextInt();
            } catch (Exception e) {
                System.out.println("Must enter '1' for store 1, or '2' for store 2.");
            }
        }
        // Scans the next token of the input as an int.
        reader.close();

        // Need to implement option to set store
        // get_receiver().set_store(choice); 
    }
}

class buyGuitarKitCommand extends Command {
    public void execute() {
        
    }
}
