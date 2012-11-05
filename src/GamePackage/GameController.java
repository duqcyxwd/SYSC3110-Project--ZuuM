package GamePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * This is the controller for the GameView. When a Tile is clicked, this calls
 * actionPerformed(...).
 */
class GameController implements ActionListener {

	private Game game;

	/**
	 * 
	 * @param game game to use as reference.
	 */
	public GameController(Game game) {
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tile tile = (Tile) e.getSource();
		try {
			// when a button is clicked, call playTurn on the clicked position
			game.play(tile.getCell().getPosition());
			//game.play();
		} catch (IllegalArgumentException ex) {
			// do nothing to avoid annoying popups.
		}
	}

}
