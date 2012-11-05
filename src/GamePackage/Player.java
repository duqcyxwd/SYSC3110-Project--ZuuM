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
	
	protected static ImageIcon playerImage = new ImageIcon("img/red-tile.png");
	
	
	 /**
     * Constructor of the initialises location and place
     */
	public Player(Position position, Game game, int lives){ 
		super(position, game, playerImage, lives);
	}
	
	

	@Override
	public boolean collidesWith(Avatar avatar) {
		// TODO Auto-generated method stub
		return false;
	}

}

