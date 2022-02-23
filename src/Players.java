public abstract class Players extends Item {
    // Players are a subclass of item

    private String brand_; // Each player has a brand
    private boolean equalized_ = false;
    Players(String name, double purchPrice, double listPrice, boolean isNew, int dayArriv, Condition condition, double salePrice, String brand)
    {
        super(name, purchPrice, listPrice, isNew, dayArriv, condition, salePrice);
        brand_ = brand;
    }

    // Getters and setters
    public String get_brand() {return brand_;}
    public void set_brand(String newBrand) {brand_ = newBrand;}
    public boolean get_tuned(){return equalized_;}
    public void set_tuned(boolean equalized){equalized_ = equalized;}
}

// CD_Player, RecordPlayer, MP3Player extend Players
class CD_Player extends Players
{
    CD_Player(String name, double purchPrice, double listPrice, boolean isNew, int dayArriv, Condition condition, double salePrice, String brand)
    {
        super(name, purchPrice, listPrice, isNew, dayArriv, condition, salePrice, brand);
    }
    public String toString(){
        return "CDPlayer: " + get_brand();
    }
}

class RecordPlayer extends Players
{
    RecordPlayer(String name, double purchPrice, double listPrice, boolean isNew, int dayArriv, Condition condition, double salePrice, String brand)
    {
        super(name, purchPrice, listPrice, isNew, dayArriv, condition, salePrice, brand);
    }
    public String toString(){
        return "RecordPlayer: " + get_brand();
    }
}

class MP3Player extends Players
{
    MP3Player(String name, double purchPrice, double listPrice, boolean isNew, int dayArriv, Condition condition, double salePrice, String brand)
    {
        super(name, purchPrice, listPrice, isNew, dayArriv, condition, salePrice, brand);
    }
    public String toString(){
        return "MP3Player: " + get_brand();
    }
}

class CassettePlayer extends Players
{
    CassettePlayer(String name, double purchPrice, double listPrice, boolean isNew, int dayArriv, Condition condition, double salePrice, String brand)
    {
        super(name, purchPrice, listPrice, isNew, dayArriv, condition, salePrice, brand);
    }
    public String toString(){
        return "CassettePlayer: " + get_brand();
    }
}