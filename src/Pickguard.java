public interface Pickguard{
    public abstract double get_cost();
}

class PickguardA implements Pickguard{
    public double get_cost(){
        return 1.50;
    }
}

class PickguardB implements Pickguard{
    public double get_cost(){
        return 2.50;
    }
}

class PickguardC implements Pickguard{
    public double get_cost(){
        return 2.00;
    }
}

class PickguardD implements Pickguard{
    public double get_cost(){
        return 1.40;
    }
}