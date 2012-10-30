package GamePackage;
import java.util.List;

import javax.swing.ImageIcon;


/**
 * The class Monster tells current location of each monsters in 
 * the game. * 
 * @author Nishant Bhasin
 * @version Oct 23rd, 2012
 */

public class Monster extends Avatar {
	
	
	 /**
     * Constructor of the initialises location and place
     */
	public Monster(Position position, Game game, ImageIcon image, int lives){ 
		super(position, game, image, lives);
	}
	
	

	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}

}
