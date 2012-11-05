package GamePackage;
import java.util. * ;

import javax.swing.ImageIcon;


/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Bruno Colantonio, Nishant Bhasin, Mohamed Ahmed, Yongquinchuan Du 
 * @version Oct 23rd, 2012
 */

public class Room {
    private String description;
    private ArrayList<Exit> exits;
    private ArrayList<Item> items;
    private ArrayList<Monster> mon_list; // List of monsters in the room.
    private String name;
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
    
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new ArrayList<Exit> ();
        items = new ArrayList<Item> ();
        mon_list = new ArrayList < Monster > (); // List of monsters in the room
    }


    /**
     * Function to add monster to this room
     */
    public void addMonster(Monster M) {
       mon_list.add(M); // add a single new monster
    }

    /**
     * Function to getMonsters in this room
     * It returns the number of monsters present in this
     * room.
     */
    public ArrayList<Monster> getMonster() {
        return mon_list; // returns number of monsters in room
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the Room object associated which a given exit string
     */

    /**
     * Add items to room
     *
     * @param  accepts an item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * returns the string object associated with a certain object name (string)
     */
    public ArrayList<Item> getItem(String item) {
        return items;
    }


    /**
     * Remove items from room
     * @param  accepts an item
     */
    public void removeItem(String description) {
        items.remove(description);
    }



    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(Exit exit) {
        exits.add(exit);
    }
    
    

    public ArrayList<Exit> getExit() {
        return exits;
    }
    
    /**
     * @return Returns the string representation of
     * the list of all exits for this room
     */
    public String getExitString() {
        String s = "";
        for(Object direction: exits)
        s += direction + " ";
        return s;
    }

    /**
     * Returns description of room (what room you`re in
     * what exits and the items in it )
     */
    public String getLongDescription() {
        String itemsString = "";
        String ld = "";
        if(items.size() > 0) for(Item item: items) //if items is not empty, it adds the items to a String
        	itemsString += item.getDescription() + " ";
        else itemsString = "none";

        ld = ld + "You are " + description + ".\n" + "Exits: " + getExitString() + ". The items in the room are : " + itemsString + "\n";
        
        if(!mon_list.isEmpty()) {
        	ld = "Monster Alert !! " + mon_list.size() + " Monsters in room." + "\n" + ld;
        } 
        return ld;
    }

    public String toString(){ // returns description of the room
    	return description;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getName(){
		return name;
    	
    }
    public int getWidth(){
  		return WIDTH;
  	}
  	
  	public int getHeight(){
  		return HEIGHT;
  	}
}