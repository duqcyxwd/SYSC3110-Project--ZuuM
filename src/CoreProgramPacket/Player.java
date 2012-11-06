package CoreProgramPacket;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;



/**
 * 
 * 
 * @author 
 * @version Oct 23rd, 2012
 */

public class Player extends Avatar {
	
	protected static ImageIcon playerImage = new ImageIcon("img/red-tile.png");
	
	private List<Item> itemList;
	
	/**
     * Constructor of the initialises location and place
     */
	public Player(Position position, Game game, int lives){ 
		super(position, game, lives);
		itemList = new ArrayList<Item>();
	}
	
	

	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addItem(Item i)
	{
		itemList.add(i);
	}
	
	@Override
	public String toString()
	{
		String s = " | ";
		
		for(Item i : itemList)
		{
			s = s + i.toString() + " | ";
		}
		return s;
	}

}

