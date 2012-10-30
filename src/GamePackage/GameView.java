package GamePackage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.util.Observable;
import java.util.Observer;


import javax.swing.JFrame;
import javax.swing.JOptionPane;



/**
 * The GameView displays a window with the graphical representation of a game.
 * The user interacts with the game by clicking tiles visible in the window.
 * @author 
 */
public class GameView implements Observer {
	
	private JFrame frame = null;
	protected Game game = null;
	private Component[] components = null;
	private boolean frameIsSized = false;
	
	/**
	 * Sets up the JFrame by defining properties and position on the screen.
	 * It also runs updateView() once.
	 * 
	 * @param game the game game which contains the tile grid and the playTurn fn.
	 */
	public GameView(Game game) {
		super();
		this.game = game;
		game.addObserver(this);
		frame = new JFrame(game.getClass().getSimpleName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
		updateView();
	}
	
	/**
	 * Allows specific game to have additional components on the game,
	 * other than the graphical representation of the playing field.
	 * @param components a list of components to add to the top of the playing
	 * field
	 */
	public void addMoreComponents(Component[] components) {
		this.components = components;
		// frame needs a resize
		frameIsSized = false;
		updateView();
	}

	/**
	 * The view gets updated with the tiles on game.playingField, and with the components,
	 * if any, set in addMoreComponents(...)
	 */
	private void updateView() {
		// wraps the additional components and the game
		Container wrapper = new Container();
		wrapper.setLayout(new BorderLayout());
		if (components != null) {
			updateComponents();
			// additionalComponent stored on top of gameField
			Container additionalComponent = new Container();
			additionalComponent.setLayout(new GridLayout(1, components.length));
			for (int i = 0; i < components.length; i++) {
				additionalComponent.add(components[i]);
			}
			wrapper.add(additionalComponent,BorderLayout.NORTH);
		}
		// gameField contains the tiles
		Container gameField = new Container();
		gameField.setLayout(new GridLayout(game.getHeight(),game.getWidth()));
		wrapper.add(gameField,BorderLayout.CENTER);
		// Place playingField tiles on the game.
		Tile tile;
		GameController handler = new GameController(game);
		for (int row = 0; row < game.getHeight(); row++) {
			for (int col = 0; col < game.getWidth(); col++) {
				tile = game.getTile(new Position(row,col));
				// if this tile has no listeners, add one
				System.out.println(tile);
				if (tile.getActionListeners().length == 0) {
					tile.addActionListener(handler);
				}
				gameField.add(tile);
			}
		}
		
		frame.setContentPane(wrapper);
		if (!frameIsSized) {
			frame.pack(); // wrap window around content.
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (dim.width-frame.getSize().width)/2;
			int y = (dim.height-frame.getSize().height)/2;
			frame.setLocation(x, y);
			// frame has been sized once, no need to do it again
			frameIsSized = true;
		}
		frame.setVisible(true);
	}
	
	private void handleGameOver(String str) {
		// either the game is won, or the game is lost
		updateView();
		// ask the user if he wants to play again, or quit this game.
		String[] options = {"Play again","Quit"};
		int result = JOptionPane.showOptionDialog(null, str, "End of Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0]);
		if (result == JOptionPane.CLOSED_OPTION) {
			// if the user closes the YOU WIN window, show game select menu
			frame.dispose();
			GameLauncher.main(null);
		} else if (result == 0) {
			// if option 0 is chosen, start a new game
			game.restartGame();
			updateView();
			game.play();
		} else {
			// if any other option is chosen, show game select menu
			System.exit(0);
		}
	}
	
	/**
	 * This function will change the value of any additional components that have
	 * been added to the game, e.g. updating lives remaining on JTextArea.
	 */
	public void updateComponents(){
		
	}
	
	public void update(Observable o, Object arg) {
		if (!(arg instanceof String)) return; // do nothing
		String str = (String) arg;
		// either an update or a game over
		if (str.equals("update")) updateView();
		else handleGameOver(str);
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		@SuppressWarnings("unused")
		GameView game = new GameView(g);
	}
}
