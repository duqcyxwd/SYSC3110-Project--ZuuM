package DataPacket;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import CoreProgramPacket.Avatar;
import CoreProgramPacket.Game;
import CoreProgramPacket.ItemCell;



/**
 * 
 * 
 * @author 
 * @version Oct 23rd, 2012
 */

public class PlayerCell extends Avatar {
	
	private List<ItemCell> itemList;
	
	private String name;



    /**
     * Constructor of the initialises location and place
     *	
	 *
	 * @param position the player will be in
	 * @param game the player is playing in
	 * @param lives the player starts out with
	 */
	public PlayerCell(Position position, Game game, int lives){ 
		super(position, game, lives);
		itemList = new ArrayList<ItemCell>();
		name = "Player";
	}
	
	   
    /* (non-Javadoc)
     * @see CoreProgramPacket.Cell#getName()
     */
    public String getName() {
        return name;
    }
    /* (non-Javadoc)
     * @see CoreProgramPacket.Cell#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }


	/* (non-Javadoc)
	 * @see CoreProgramPacket.Avatar#collidesWith(CoreProgramPacket.Avatar)
	 */
	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @return ArrayList<ItemCell> items - an array list of the cell items
	 */
	public ArrayList<ItemCell> getItem(){
		return (ArrayList<ItemCell>) itemList;
	}
	
	/**
	 * @return a boolean value - if the player has a shiled or not
	 */
	public boolean haveShield(){
		for(ItemCell i : itemList){
			if(i.getName().equals("shield")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return a boolean value - if the player has food or not
	 */
	public boolean haveFood(){
		for(ItemCell i : itemList){
			if(i.getName().equals("food")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return a boolean value - if the player has a key or not
	 */
	public boolean haveKey(){
		for(ItemCell i : itemList){
			if(i.getName().equals("key")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return a boolean value - if the player has the trophy or not
	 */
	public boolean haveTrophy(){
		for(ItemCell i : itemList){
			if(i.getName().equals("trophy")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param ItemCell item - the item to add to the player's inventory
	 */
	public void addItem(ItemCell i)
	{
		itemList.add(i);
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Cell#toString()
	 */
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

