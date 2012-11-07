package CoreProgramPacket;

import javax.swing.ImageIcon;

import DataPacket.Position;
import DataPacket.Room;


public class Exit extends Cell {
	
	public Room nextRoom;
	public String name;
	
	
	/**
	 * @param Positopn position - the positiion where the exit is in the room
	 * @param Game game - the game to which the exit refers to
	 * @param String name -the name of the exit 
	 * @param Room nextRoom - the room the exit takes you too
	 */
	public Exit(Position position, Game game, String name, Room nextRoom){
		super(position, game);
		this.name = name;
		this.nextRoom = nextRoom;
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Cell#getName()
	 */
	public String getName(){
		return name;
	}
	
	/* (non-Javadoc)
	 * @see CoreProgramPacket.Cell#setName(java.lang.String)
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @return Room room - the room the exit will bring you to 
	 */
	public Room getNextRoom(){
		return nextRoom;
	}
	
	/**
	 * @param nextRoom - the room the exit will bring you to 
	 */
	public void setNextRoom(Room nextRoom){
		this.nextRoom = nextRoom;
	}
	
	
	
	
	
	
}
