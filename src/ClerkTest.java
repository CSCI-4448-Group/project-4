import static org.junit.Assert.*;

public class ClerkTest {

    @org.junit.Before
    public void setUp() throws Exception {
        System.out.println("Testing beginning...");
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.out.println("Testing ending...");
    }

    @org.junit.Test
    public void test_get_days_worked_equals() {
        String clerkName = "Jose";
        Store s = new Store("TestStore");
        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);
        assertEquals(testClerk.get_days_worked(), 0);
    }

    @org.junit.Test
    public void test_get_days_worked_not_equals() {
        String clerkName = "Jose";
        Store s = new Store("TestStore");
        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);
        assertNotEquals(testClerk.get_days_worked(), 100);
    }

    @org.junit.Test
    public void test_get_store() {
        String clerkName = "Jose";
        Store s  = new Store("TestStore");
        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);
        assertEquals(testClerk.get_store(), s);
    }

    @org.junit.Test
    public void test_get_name() {
        String clerkName = "Jose";
        Store s  = new Store("TestStore");
        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);
        assertEquals(testClerk.get_name(), clerkName);
    }

    @org.junit.Test
    public void test_set_tune_behavior() {
        String clerkName = "Jose";
        Store s = new Store("TestStore");
        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);

        TuneBehavior setTune = new ElectronicTune();
        testClerk.set_tune_behavior(setTune);

        assertSame(testClerk.get_tune_behavior(), setTune);
    }

    @org.junit.Test
    public void test_calc_bonus_chance() {
        String clerkName = "Jose";
        Store s = new Store("TestStore");

        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);

        Condition testCondition = new Condition("very good");
        Item guitar = new Guitar("Gibson Les Paul", 100, 150, true, 5, testCondition, 200, "Gibson", true);
        guitar.set_tuned(true);

        int calcBonusSellChance = testClerk.calc_bonus_chance(guitar);
        assertEquals(calcBonusSellChance, 15);
    }

    @org.junit.Test
    public void test_go_to_bank() {
        String clerkName = "Jose";
        Store s = new Store("TestStore");
        TuneBehavior testTune = new HaphazardTune();
        Clerk testClerk = new Clerk(clerkName, s, testTune);

        double actualCurrentAmount = s.get_register().get_amount();
        testClerk.go_to_bank();
        double bankCurrentAmount = s.get_register().get_amount();
        assertEquals(bankCurrentAmount, actualCurrentAmount + 1000, 2);
    }
}