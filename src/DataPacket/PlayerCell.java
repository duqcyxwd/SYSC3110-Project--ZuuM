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
     */
	public PlayerCell(Position position, Game game, int lives){ 
		super(position, game, lives);
		itemList = new ArrayList<ItemCell>();
		name = "Player";
	}
	
	   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArrayList<ItemCell> getItem(){
		return (ArrayList<ItemCell>) itemList;
	}
	
	public boolean haveShield(){
		for(ItemCell i : itemList){
			if(i.getName().equals("shield")){
				return true;
			}
		}
		return false;
	}
	
	public boolean haveFood(){
		for(ItemCell i : itemList){
			if(i.getName().equals("food")){
				return true;
			}
		}
		return false;
	}
	
	public boolean haveKey(){
		for(ItemCell i : itemList){
			if(i.getName().equals("key")){
				return true;
			}
		}
		return false;
	}
	
	public boolean haveTrophy(){
		for(ItemCell i : itemList){
			if(i.getName().equals("trophy")){
				return true;
			}
		}
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

