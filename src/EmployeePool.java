import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
public class EmployeePool{
    private ArrayList<Employee> employees_;
    private static EmployeePool instance;

    private EmployeePool(){
        initializeEmployees();
    }
    //Lazy instantiation
    public static EmployeePool getInstance() {
        if (instance == null) {
            instance = new EmployeePool();
        }
        return instance;
    }

    public void initializeEmployees() {
        employees_ = new ArrayList<Employee>();
        employees_.add(new Clerk("Shaggy",null, new HaphazardTune()));
        employees_.add(new Clerk("Velma", null, new ManualTune()));
        employees_.add(new Clerk("Daphne", null, new ElectronicTune()));
        employees_.add(new Clerk("James",null, new HaphazardTune()));
        employees_.add(new Clerk("Bill", null, new ManualTune()));
        employees_.add(new Clerk("Fahad", null, new ElectronicTune()));
    }

    //Reset days for all employees in emloyee pool
    public void reset_days(){
        for(Employee emp : employees_){
            emp.set_days_worked(0);
        }
    }

    //Return all clerks in employee pool
    public ArrayList<Clerk> get_clerks() {
        ArrayList<Clerk> clerks = new ArrayList<Clerk>();
        for(Employee emp : employees_){
            if(emp instanceof Clerk){
                clerks.add((Clerk)emp);
            }
        }
        return clerks;
    }

    //Return all clerks who have a current days_worked less than 3
    public ArrayList<Clerk> get_available_clerks() {
        ArrayList<Clerk> clerks = new ArrayList<Clerk>();
        for(Employee emp : employees_){
            if(emp instanceof Clerk && emp.get_days_worked() < 3){
                clerks.add((Clerk)emp);
            }
        }
        return clerks;
    }

    //@
    public ArrayList<Clerk> get_clerks_of_day(int numClerks, ArrayList<Store> stores) {
        Random rand = new Random();
        ArrayList<Clerk> result = new ArrayList<>();

        // Modified from Bruce Montgomery's 'Spring22OOADProj2' example code in class.
        ArrayList<Clerk> clerks = get_available_clerks();
        while(result.size() < numClerks) {
            Clerk clerk = clerks.get(rand.nextInt(clerks.size())); //Get a random clerk
            // if they are not ok to work, set their days worked to 0 and get another clerk
            if(rand.nextInt(100) < 10){ //If the employee is sick
                Clerk old_clerk = clerk;
                for (Clerk other : clerks) { //Find another clerk
                    if (other != clerk) {
                        clerk = other;
                        break;
                    }
                }
                System.out.println(old_clerk.get_name() + " was sick, so " + clerk.get_name() + " covered for them.");
                old_clerk.set_days_worked(0);
                clerks.remove(old_clerk); //Remove the sick clerk from consideration
            }
            clerk.incr_days_worked(); //Increment days_worked for the clerk
            result.add(clerk); //Add the clerk to the return array
            clerks.remove(clerk); //Remove clerk from consideration
            clerk.set_store(stores.get(result.size()-1)); //Set the clerks store
        }
        for(Clerk cl: get_clerks()){ //Reset all the unchosen clerks days_worked to 0
            if(!result.contains(cl))
                cl.set_days_worked(0);
        }
        return result;
    }
}