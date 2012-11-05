package GamePackage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class ZuulGameView extends GameView {
	
	private JTextArea lives;
	private JTextArea points;
	private JButton undoButton;
	private JButton redoButton;

	public ZuulGameView(Game game) {
		super(game);
		lives = new JTextArea();
		points = new JTextArea();
	    undoButton = new JButton("Undo");
	    undoButton.setPreferredSize(new Dimension(1,1));
	    undoButton.addActionListener(new UndoButtonHandler(game));
	    redoButton = new JButton("Redo");
	    redoButton.setPreferredSize(new Dimension(1,1));
	    redoButton.addActionListener(new RedoButtonHandler(game));
		updateComponents();
		Component[] more = {lives, undoButton, redoButton};
		addMoreComponents(more);
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		@SuppressWarnings("unused")
		ZuulGameView game = new ZuulGameView(g);
	}
	
	public void updateComponents() {
		Player player = game.getUser();
		lives.setText("Lives: " + player.getLives());
	}
	
	private class UndoButtonHandler implements ActionListener {

		Game game = null;

		public UndoButtonHandler(Game game) {
			super();
			this.game = game;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			game.undoMove();
		}
		
	}
	
	private class RedoButtonHandler implements ActionListener {

		Game game = null;

		public RedoButtonHandler(Game game) {
			super();
			this.game = game;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			game.redoMove();
		}
		
	}

}