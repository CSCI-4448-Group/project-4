public interface Pickups{
    double get_cost();
}

class PickupA implements Pickups{
    public double get_cost(){
        return 3.25;
    }
}

class PickupB implements Pickups{
    public double get_cost(){
        return 5.25;
    }
}

class PickupC implements Pickups{
    public double get_cost(){
        return 3.00;
    }
}

class PickupD implements Pickups{
    public double get_cost(){
        return 2.20;
    }
}