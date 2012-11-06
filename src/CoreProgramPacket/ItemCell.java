package CoreProgramPacket;

import javax.swing.ImageIcon;

import DataPacket.Position;



/**
 * The item class represents items which have a description and a wait
 * a room can contain a few items
 * @author Bruno Colantonio, Nishant Bhasin, Mohamed Ahmed, Yongquinchuan Du 
 * @version Oct 23rd, 2012
 */
public class ItemCell extends Cell{
    // instance variables - replace the example below with your own
    private String description;
    private int weight;

    /**
     * Constructor for objects of class Items
     */
    public ItemCell(Position position, Game game, String description){
    	super(position, game);
        this.description = description;
        //this.weight = weight;
    }

    /**
     * @return the weight of the Item
     */
    public int getWeight(){
       return weight;
    }
    
    /**
     * return the description of the Item
     */
    public String getDescription(){
        return description;
    }
    
    public String getName(){
        return description;
    }
}
