package DataPacket;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import CoreProgramPacket.Avatar;
import CoreProgramPacket.Game;
import CoreProgramPacket.Position;


/**
 * The class Monster tells current location of each monsters in 
 * the game. * 
 * @author Nishant Bhasin
 * @version Oct 23rd, 2012
 */

public class MonsterCell extends Avatar{
	
	private String name;
	 /**
     * Constructor of the initialises location and place
     */
	public MonsterCell(Position position, Game game, String name){ 
		super(position, game, 1);
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public boolean collidesWith(Avatar avatar) {
		if(avatar.getLives()>0 && avatar.getPosition()==this.getPosition())
		{
			avatar.removeLife();
		
			return true;
		}
		else
		{
			return false;
		}
	}
	

}
