public interface Covers  {
    double get_cost();
}

class CoverA implements Covers{
    public double get_cost(){
        return 1.25;
    }
}

class CoverB implements Covers{
    public double get_cost(){
        return 3.25;
    }
}

class CoverC implements Covers{
    public double get_cost(){
        return 2.25;
    }
}

class CoverD implements Covers{
    public double get_cost(){
        return 0.75;
    }
}