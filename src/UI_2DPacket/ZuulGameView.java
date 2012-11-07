package UI_2DPacket;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import CoreProgramPacket.Game;
import DataPacket.PlayerCell;

public class ZuulGameView extends GameView {
	
	private JTextArea lives;
	private JTextArea roomDescription;
	private JTextArea inventory;
	private JButton undoButton;
	private JButton redoButton;
	

	public ZuulGameView(Game game) {
		super(game);
		lives = new JTextArea();
		roomDescription = new JTextArea();
		roomDescription.setPreferredSize(new Dimension(1,1));
		inventory = new JTextArea();
		inventory.setPreferredSize(new Dimension(1,1));
		
	    undoButton = new JButton("Undo");
	    undoButton.setPreferredSize(new Dimension(1,1));
	    undoButton.addActionListener(new UndoButtonHandler(game));
	    
	    redoButton = new JButton("Redo");
	    redoButton.setPreferredSize(new Dimension(1,1));
	    redoButton.addActionListener(new RedoButtonHandler(game));
	    
		updateComponents();
		Component[] more = {undoButton, redoButton, lives, inventory, roomDescription};
		addMoreComponents(more);
	}
	
	public void updateComponents() {
		PlayerCell player = game.getUser();
		lives.setText("Lives: " + player.getLives());
		roomDescription.setText("Room: " + game.getCurrentRoom().getDescription());
		inventory.setText("Inventory: " + player.getItem());
	}
	
	private class UndoButtonHandler implements ActionListener {

		Game game;;

		public UndoButtonHandler(Game game) {
			this.game = game;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			game.undo();
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
			game.redo();
		}
		
	}

}