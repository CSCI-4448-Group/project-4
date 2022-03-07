public interface Bridge {
    double get_cost();
}

class BridgeA implements Bridge{
    public double get_cost(){
        return 1.25;
    }
}

class BridgeB implements Bridge{
    public double get_cost(){
        return 3.25;
    }
}

class BridgeC implements Bridge{
    public double get_cost(){
        return 2.25;
    }
}

class BridgeD implements Bridge{
    public double get_cost(){
        return .75;
    }
}