package GamePackage;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * The Tile class has a position and an image. 
 * All squares on the game will be instances of Tile or one of its subclasses.
 * The tile will either be blank or hold an image.
 * @author 
 */
@SuppressWarnings("serial")
public class Tile extends JButton {
	
	public static final int TOP_LEFT=0; 
	public static final int TOP=1;
	public static final int TOP_RIGHT=2;
	public static final int LEFT=3;
	public static final int RIGHT=4;
	public static final int BOTTOM_LEFT=5;
	public static final int BOTTOM=6;
	public static final int BOTTOM_RIGHT=7;
	
	public static final int HEIGHT_OF_IMG = 30;

	public static final int WIDTH_OF_IMG = 30;
	
	private ImageIcon image; 
	private boolean accessible;
	public Avatar A;
	
	/**
	 * the blankTileImage represents an instance of Tile
	 */
	protected static ImageIcon blankTileImage = new ImageIcon("img/white-tile.png");
	
	protected Position position;
	
	protected Game game;
	
	/**
	 * The constructor will create a Tile at the positions of
	 * the row and column. The image of a standard Tile is white.
	 * @param position will set the position of the Tile
	 * @param game will set the Tile to the specified game
	 */
	public Tile(Position position, Game game, ImageIcon image){
		setBorderPainted(false);
		accessible = true;
		this.image = image;
		setIcon(image);
		setPreferredSize(new Dimension(WIDTH_OF_IMG,HEIGHT_OF_IMG));
		this.game = game;
		setPosition(position);
	}
	
	/**
	 * The constructor will create a Tile at the positions of
	 * the row and column. The image of a standard Tile is white.
	 * @param position will set the position of the Tile
	 * @param game will set the Tile to the specified game
	 */
	public Tile(Position position, Game game){
		setBorderPainted(false);
		accessible = true;
		image = blankTileImage;
		setIcon(image);
		setPreferredSize(new Dimension(WIDTH_OF_IMG,HEIGHT_OF_IMG));
		this.game = game;
		setPosition(position);
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
	public Position getPosition(){
		return position;
	}
	
	/**
	 * The method will set the position to the parameter given. 
	 * Throws an exception if the position given is out of bounds.
	 * @param position to set piece
	 */
	public void setPosition(Position position) throws IndexOutOfBoundsException{
		if(position.getRow() < 0 || position.getRow() >= game.getHeight())
			throw new IndexOutOfBoundsException("Row out of bounds.");
		else if(position.getCol() < 0 || position.getCol() >= game.getWidth())
			throw new IndexOutOfBoundsException("Col out of bounds");
		else
			this.position = position;
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
	
	/**
	 * @return true if Tile is generally accessible 
	 */
	public boolean getAccessible(){
		return accessible;
	}
	
	/**
	 * The method will make the tile accessible
	 * @param accessible Set whether the tile is accessible
	 * by the Avatars or not.
	 */
	public void setAccessible(boolean accessible){
		this.accessible = accessible;
	}
	
	/**
	 * The method will return an array to show the tiles surrounding the
	 * current tile. The indexes of the the array correspond to the constants
	 * defined at the top of class. Adjacent squares that are out of the bounds
	 * of the game will be set to null.
	 */
	public Tile[] getAdjacentTiles(){
		Tile[] adj = new Tile[8];
		int adjIndex = 0;
		int thisRow = position.getRow();
		int thisCol = position.getCol();
		for(int i = -1; i <= 1; i++)
		{			
			for(int j = -1; j <= 1; j++)
			{
				if(i==0 && j==0)//if the loop is at the centre Tile, skip it
				{
					j++;
				}
				
				//out of bounds checks
				if((thisRow + i) < 0 || (thisCol + j) < 0)
					adj[adjIndex] = null;
				else if((thisRow + i) >= game.getHeight() || (thisCol + j) >= game.getWidth())
					adj[adjIndex] = null;
				//add Tile to appropriate index
				else
					adj[adjIndex] = game.getTile(new Position(thisRow+i,thisCol+j));
				
				adjIndex++;
			}
		}
		return adj;
	}
	
	public String toString() {
		return " "; // blank tile
	}

}
