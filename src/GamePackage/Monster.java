package GamePackage;
import java.util.List;
import java.util.Random;

import javax.swing.*;

/**
 * The class Monster tells current location of each monsters in 
 * the game. * 
 * @author Nishant Bhasin
 * @version Oct 23rd, 2012
 */

public class Monster extends Avatar{
	
	
	 /**
     * Constructor of the initialises location and place
     */
	public Monster(Position position, Game game, ImageIcon image){ 
		super(position, game, image, 1);
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
