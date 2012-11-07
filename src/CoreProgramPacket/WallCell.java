package CoreProgramPacket;


import java.awt.Color;

import javax.swing.ImageIcon;

import DataPacket.Position;


/**
 * The wall is a tile which blocks the path of an Avatar. It is pictorially represented
 * by a Wall, and it simply a Tile that is not accessible (generally).
 * @author 
 */
@SuppressWarnings("serial")
public class WallCell extends Cell{
	
	protected static ImageIcon wallImage = new ImageIcon("img/black-tile.png");

	/**
	 * The constructor will create a wall, or in other words a tile that is not accessible
	 */
	public WallCell(Position position, Game game) {
		super(position, game);
		setAccessible(false);
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Cell#toString()
	 */
	public String toString() {
		return "X";
	}
}
