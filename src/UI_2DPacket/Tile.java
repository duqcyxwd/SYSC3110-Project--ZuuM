package UI_2DPacket;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import CoreProgramPacket.Cell;
import CoreProgramPacket.Game;

/**
 * The Tile class has a position and an image. 
 * All squares on the game will be instances of Tile or one of its subclasses.
 * The tile will either be blank or hold an image.
 * @author 
 */
@SuppressWarnings("serial")
public class Tile extends JButton {
	
	
	public static final int HEIGHT_OF_IMG = 30;
	public static final int WIDTH_OF_IMG = 30;
	private ImageIcon image; 
	private Game game;
	private Cell cell;
	/**
	 * the blankTileImage represents an instance of Tile
	 */
	protected static ImageIcon blankTileImage = new ImageIcon("img/white-tile.png");
	
	
	
	/**
	 * The constructor will create a Tile at the positions of
	 * the row and column. The image of a standard Tile is white.
	 * @param position will set the position of the Tile
	 * @param game will set the Tile to the specified game
	 */
	public Tile(Cell cell, ImageIcon image){
		this.cell = cell;
		setBorderPainted(false);
		this.image = image;
		setIcon(image);
		setPreferredSize(new Dimension(WIDTH_OF_IMG,HEIGHT_OF_IMG));
		this.game = game;
	}
	
	/**
	 * The constructor will create a Tile at the positions of
	 * the row and column. The image of a standard Tile is white.
	 * @param position will set the position of the Tile
	 * @param game will set the Tile to the specified game
	 */
	public Tile(Cell cell){
		this.cell = cell;
		setBorderPainted(false);
		image = blankTileImage;
		setIcon(image);
		setPreferredSize(new Dimension(WIDTH_OF_IMG,HEIGHT_OF_IMG));
		this.game = game;
	}
	
	/**
	 * Sets the image of blank tiles to the given image
	 * 
	 * @param image
	 */
	public static void setBlankImage(ImageIcon image) {
		blankTileImage = image;
	}
	
	/**
	 * The method will return the Position of the Tile
	 * @return the position of the tile
	 */
	public Cell getCell(){
		return cell;
	}
	
	/**
	 * The method will set the position to the parameter given. 
	 * Throws an exception if the position given is out of bounds.
	 * @param position to set piece
	 */
	public void setCell(Cell cell) throws IndexOutOfBoundsException{
		if(cell.getPosition().getRow() < 0 || cell.getPosition().getRow() >= game.getCurrentRoom().getHeight())
			throw new IndexOutOfBoundsException("Row out of bounds.");
		else if(cell.getPosition().getCol() < 0 || cell.getPosition().getCol() >= game.getCurrentRoom().getWidth())
			throw new IndexOutOfBoundsException("Col out of bounds");
		else
			this.cell = cell;
	}
	
	/**
	 * The method will return the image used on the tile.
	 * @return the image used on the tile.
	 */
	public ImageIcon getImage(){
		return image;
	}
	
	/**
	 * The method will set the image on the tile.
	 * @param image The image that is to be used on the tile.
	 */
	public void setImage(ImageIcon image){
		this.image = image;
		setIcon(image);
	}
	
	
	public String toString() {
		return " "; // blank tile
	}

}
