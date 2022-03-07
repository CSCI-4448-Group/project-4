public interface Knobset{
    public double get_cost();
}

class KnobsetA implements Knobset{
    public double get_cost(){
        return 2.00;
    }
}

class KnobsetB implements Knobset{
    public double get_cost(){
        return 2.75;
    }
}

class KnobsetC implements Knobset{
    public double get_cost(){
        return 3.75;
    }
}

class KnobsetD implements Knobset{
    public double get_cost(){
        return 3.55;
    }
}