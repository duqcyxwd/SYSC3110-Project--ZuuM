package GamePackage;
import java.util.List;

/**
 * The class Monster tells current location of each monsters in 
 * the game. * 
 * @author Nishant Bhasin
 * @version Oct 23rd, 2012
 */

public class Monster {
	
	private String location; // current location of monster 
	private Room place; // Room in which the monster is present 
	
	 /**
     * Constructor of the initialises location and place
     */
	public Monster(Room r, String l){ 
		this.location = l;
		this.place = r;
	}
	
	 /**
     * returns the current location of the monster
     */
	public String location(){ 
		return location;
	}
	
	 /**
     * returns the room in which the monster is present
     */
	public Room placement(){ 
		return place;
	}

}
