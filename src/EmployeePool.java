import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
public class EmployeePool{
    private ArrayList<Employee> employees_;
    private static EmployeePool instance;

    private EmployeePool(){
        initializeEmployees();
    }
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

    public void reset_days(){
        for(Employee emp : employees_){
            emp.set_days_worked(0);
        }
    }

    public ArrayList<Clerk> get_clerks() {
        ArrayList<Clerk> clerks = new ArrayList<Clerk>();
        for(Employee emp : employees_){
            if(emp instanceof Clerk){
                clerks.add((Clerk)emp);
            }
        }
        return clerks;
    }
    public ArrayList<Clerk> get_available_clerks() {
        ArrayList<Clerk> clerks = new ArrayList<Clerk>();
        for(Employee emp : employees_){
            if(emp instanceof Clerk && emp.get_days_worked() < 3){
                clerks.add((Clerk)emp);
            }
        }
        return clerks;
    }

    public ArrayList<Clerk> get_clerks_of_day(int numClerks, ArrayList<Store> stores) {
        Random rand = new Random();
        int rand_num;
        ArrayList<Clerk> result = new ArrayList<>();

        // Modified from Bruce Montgomery's 'Spring22OOADProj2' example code in class.
        ArrayList<Clerk> clerks = get_available_clerks();
        while(result.size() < numClerks) {
            rand_num = rand.nextInt(clerks.size());
            Clerk clerk = clerks.get(rand_num);
            int sick_chance = rand.nextInt(100);
            System.out.println();
            if (sick_chance >= 10) {
                clerk.incr_days_worked();
                result.add(clerk);
                clerks.remove(clerk);
            }
            // if they are not ok to work, set their days worked to 0 and get another clerk
            else {
                Clerk old_clerk = clerk;
                for (Clerk other : clerks) {
                    if (other != clerk) {
                        clerk = other;
                        break;
                    }
                }
                if (sick_chance < 10) {
                    System.out.println(old_clerk.get_name() + " was sick, so " + clerk.get_name() + " covered for them.");
                    old_clerk.set_days_worked(0);
                }
                clerk.incr_days_worked();
                clerks.remove(clerk); //Remove the clerk who was selected to work
                clerks.remove(old_clerk);
                result.add(clerk);
            }
            clerk.set_store(stores.get(result.size()-1));
        }
        for(Clerk cl: get_clerks()){
            if(!result.contains(cl))
                cl.set_days_worked(0);
        }
        return result;
    }
}