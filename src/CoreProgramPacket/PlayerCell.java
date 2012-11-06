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

public class PlayerCell extends Avatar {
	
	protected static ImageIcon playerImage = new ImageIcon("img/red-tile.png");
	
	private List<ItemCell> itemList;
	
	/**
     * Constructor of the initialises location and place
     */
	public PlayerCell(Position position, Game game, int lives){ 
		super(position, game, lives);
		itemList = new ArrayList<ItemCell>();
	}
	
	

	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addItem(ItemCell i)
	{
		itemList.add(i);
	}
	
	@Override
	public String toString()
	{
		String s = " | ";
		
		for(ItemCell i : itemList)
		{
			s = s + i.toString() + " | ";
		}
		return s;
	}

}
