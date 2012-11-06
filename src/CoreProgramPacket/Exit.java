package CoreProgramPacket;

import javax.swing.ImageIcon;


public class Exit extends Cell {
	
	public Room nextRoom;
	public String name;
	
	
	public Exit(Position position, Game game, String name, Room nextRoom){
		super(position, game);
		this.name = name;
		this.nextRoom = nextRoom;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Room getNextRoom(){
		return nextRoom;
	}
	
	public void setNextRoom(Room nextRoom){
		this.nextRoom = nextRoom;
	}
	
	
	
	
	
	
}
