public interface Neck{
    public double get_cost();
}

class NeckA implements Neck{
    public double get_cost(){
        return 1.50;
    }
}

class NeckB implements Neck{
    public double get_cost(){
        return 3.50;
    }
}

class NeckC implements Neck{
    public double get_cost(){
        return 5.50;
    }
}

class NeckD implements Neck{
    public double get_cost(){
        return 2.50;
    }
}