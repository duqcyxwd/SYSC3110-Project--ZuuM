import java.util.List;

/**
 * The class Monster tells current location of each monsters in 
 * the game. * 
 * @author (Nishant Bhasin)
 * @version (V1)
 */

public class Monster {
	
	private String location; // current location of monster 
	private Room place; // Room in which the monster is present 
	
	public Monster(Room r, String l) //Constructor of the 
	{
		this.location = l;
		this.place = r;
	}
	
	public String location() //returns the current location of the monster
	{
		return location;
	}
	
	public Room placement() //returns the room in which the monster is present
	{
		return place;
	}

}
