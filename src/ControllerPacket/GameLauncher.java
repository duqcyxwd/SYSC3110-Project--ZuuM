package ControllerPacket;

import CoreProgramPacket.Game;
import UI_2DPacket.ZuulGameView;



/**
 * Asks the user which game he would like to play and then launches it.
 * @author 
 */
public class GameLauncher {

	/**
	 * execute
	 */
	public static void main(String[] args) {
	        //ZuulGameView.main(null);
			Game game = new Game();
			ZuulGameView newGameView = new ZuulGameView(game);
			
			
	}

}
