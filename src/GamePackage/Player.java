package GamePackage;

import java.util.List;

import javax.swing.ImageIcon;


/**
 * 
 * 
 * @author 
 * @version Oct 23rd, 2012
 */

public class Player extends Avatar {
	
	
	 /**
     * Constructor of the initialises location and place
     */
	public Player(Position position, Game game, ImageIcon image, int lives){ 
		super(position, game, image, lives);
	}
	
	

	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}

}

