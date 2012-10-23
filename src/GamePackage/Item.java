package GamePackage;

/**
 * The item class represents items which have a description and a wait
 * a room can contain a few items
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Items
     */
    public Item(String description, int weight)
    {
        this.description = description;
        this.weight = weight;
    }

    /**
     * @return the weight 
     */
    public int getWeight()
    {
       return weight;
    }
    
    /**
     * return the description
     */
    public String getDescription(){
        return description;
    }
}
