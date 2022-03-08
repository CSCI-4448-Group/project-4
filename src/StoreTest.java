import org.junit.Test;

import static org.junit.Assert.*;

// Potentially write assumption about using Junit 4.13 Junit 5
public class StoreTest {

    @Test
    public void test_set_register() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        CashRegister testCashRegister = new CashRegister();
        s.set_register(testCashRegister);
        assertSame(s.get_register(), testCashRegister);
    }

    @Test
    public void test_set_calendar() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        Calendar testCalendar = new Calendar();
        s.set_calendar(testCalendar);
        assertSame(s.get_calendar(), testCalendar);
    }

    @Test
    public void test_get_inventory() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        assertNotNull(s.get_inventory());
    }

    @Test
    public void test_get_register() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        assertNotNull(s.get_register());
    }

    @Test
    public void get_calendar() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        assertNotNull(s.get_calendar());
    }

    @Test
    public void test_is_discontinued() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        boolean discontinuedItem = s.is_discontinued("hat");
        assertTrue(discontinuedItem);
    }

    @Test
    public void test_get_name() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        assertEquals(s.get_name(), "testStore");
    }

    @Test
    public void test_set_name() {
        Store s = new Store("testStore", new NorthGuitarKitFactory());
        s.set_name("FNMSNorth");
        assertEquals(s.get_name(), "FNMSNorth");
    }
}