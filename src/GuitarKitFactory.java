import java.util.ArrayList;
//The issues I believe there are is:
    //1.] the ArrayList<Integer> itemtypes may lead to tight coupling between the command and the factory
    //2.] a lot of repeated code in each if(type ==1) else if etc.
    //3.] we create objects, but we only ever use their cost and nothing else
    //4.] This heavily relies on input itemtypes being correct size


//Questions:
    //1.] Should I have a base interface called guitarKitPiece that is the root interface instead of Covers,Neck,Pickguard etc.

public interface GuitarKitFactory {
    //double createGuitarKit(ArrayList<Integer> itemtypes);
    Bridge createBridge(int type);
    Knobset createKnobset(int type);
    Covers createCovers(int type);
    Neck createNeck(int type);
    Pickguard createPickguard(int type);
    Pickups createPickups(int type);
}

class NorthGuitarKitFactory implements GuitarKitFactory {
    public Bridge createBridge(int type) {
        if (type == 1) {
            return new BridgeA();
        } else if (type == 2) {
            return new BridgeB();
        } else if (type == 3) {
            return new BridgeD();
        }
        return () -> 0;
    }

    public Knobset createKnobset(int type) {
        if (type == 1) {
            return new KnobsetA();
        } else if (type == 2) {
            return new KnobsetB();
        } else if (type == 3) {
            return new KnobsetD();
        }
        return () -> 0;
    }

    public Covers createCovers(int type){
        if(type == 1){
            return new CoverA();
        } else if(type==2){
            return new CoverB();
        } else if(type==3){
            return new CoverD();
        }
        return () -> 0;
    }

    public Neck createNeck(int type){
        if(type == 1){
            return new NeckA();
        } else if(type == 2){
            return new NeckB();
        } else if(type == 3){
            return new NeckD();
        }
        return () -> 0;
    }

    public Pickguard createPickguard(int type){
        if(type == 1){
            return new PickguardA();
        } else if(type == 2){
            return new PickguardB();
        } else if(type == 3){
            return new PickguardD();
        }
        return () -> 0;
    }

    public Pickups createPickups(int type){
        if(type == 1){
            return new PickupA();
        } else if(type == 2){
            return new PickupB();
        } else if(type == 3){
            return new PickupD();
        }
        return () -> 0;
    }
}

class SouthGuitarKitFactory implements GuitarKitFactory{
    public Bridge createBridge(int type) {
        if (type == 1) {
            return new BridgeA();
        } else if (type == 2) {
            return new BridgeB();
        } else if (type == 3) {
            return new BridgeD();
        }
        return () -> 0;
    }

    public Knobset createKnobset(int type) {
        if (type == 1) {
            return new KnobsetA();
        } else if (type == 2) {
            return new KnobsetB();
        } else if (type == 3) {
            return new KnobsetD();
        }
        return () -> 0;
    }

    public Covers createCovers(int type){
        if(type == 1){
            return new CoverA();
        } else if(type==2){
            return new CoverB();
        } else if(type==3){
            return new CoverD();
        }
        return () -> 0;
    }

    public Neck createNeck(int type){
        if(type == 1){
            return new NeckA();
        } else if(type == 2){
            return new NeckB();
        } else if(type == 3){
            return new NeckD();
        }
        return () -> 0;
    }

    public Pickguard createPickguard(int type){
        if(type == 1){
            return new PickguardA();
        } else if(type == 2){
            return new PickguardB();
        } else if(type == 3){
            return new PickguardD();
        }
        return () -> 0;
    }

    public Pickups createPickups(int type){
        if(type == 1){
            return new PickupA();
        } else if(type == 2){
            return new PickupB();
        } else if(type == 3){
            return new PickupD();
        }
        return () -> 0;
    }
}