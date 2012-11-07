package CoreProgramPacket;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import DataPacket.Position;


/**
 * The Tile class has a position and an image. 
 * All squares on the game will be instances of Tile or one of its subclasses.
 * The tile will either be blank or hold an image.
 * @author 
 */
@SuppressWarnings("serial")
public class Cell {
	
	private boolean accessible;
	private Position position;
	private Game game;
	
	private String name;


    /**
	 * The constructor will create a Tile at the positions of
	 * the row and column. The image of a standard Tile is white.
	 * @param position will set the position of the Tile
	 * @param game will set the Tile to the specified game
	 */
	public Cell(Position position, Game game){
		accessible = true;
		this.game = game;
        setPosition(new Position(position.getRow(), position.getCol()));
	}
	
	
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
	public void setPosition(Position position){
			this.position = position;
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
	public Cell[] getAdjacentTiles(){
		Cell[] adj = new Cell[8];
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
				else if((thisRow + i) >= game.getCurrentRoom().getHeight() || (thisCol + j) >= game.getCurrentRoom().getWidth())
					adj[adjIndex] = null;
				//add Tile to appropriate index
				else
					adj[adjIndex] = game.getCell(new Position(thisRow+i,thisCol+j));
				
				adjIndex++;
			}
		}
		return adj;
	}
	
	public String toString() {
		return " "; // blank tile
	}

}
