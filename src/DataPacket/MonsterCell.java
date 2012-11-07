package DataPacket;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import CoreProgramPacket.Avatar;
import CoreProgramPacket.Game;


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
     *
	 *
	 * @param position the initial position of the monster
	 * @param game the game the monster is in
	 * @param name the name of the monster
	 */
	public MonsterCell(Position position, Game game, String name){ 
		super(position, game, 1);
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Cell#getName()
	 */
	public String getName(){
		return name;
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Cell#setName(java.lang.String)
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Avatar#collidesWith(CoreProgramPacket.Avatar)
	 */
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
