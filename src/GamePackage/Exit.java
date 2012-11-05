package GamePackage;

import javax.swing.ImageIcon;

public class Exit extends Tile {
	
	public Room nextRoom;
	public String name;
	

	protected static final ImageIcon northImage = new ImageIcon("img/northExit.png");
	protected static final ImageIcon southImage = new ImageIcon("img/southExit.png");
	protected static final ImageIcon westImage = new ImageIcon("img/westExit.png");
	protected static final ImageIcon eastImage = new ImageIcon("img/eastExit.png");
	protected static final ImageIcon upImage = new ImageIcon("img/upExit.png");
	protected static final ImageIcon downImage = new ImageIcon("img/downExit.png");
	
	public Exit(Position position, Game game, String name, Room nextRoom){
		super(position, game);
		this.name = name;
		this.nextRoom = nextRoom;
		setExitImage();
	}
	
	public void setExitImage(){
		if(name.equals("north")){
			setIcon(northImage);
		}else if(name.equals("south")){
			setIcon(southImage);
		}else if(name.equals("west")){
			setIcon(westImage);
		}else if(name.equals("east")){
			setIcon(eastImage);
		}else if(name.equals("up")){
			setIcon(upImage);
		}else if(name.equals("down")){
			setIcon(downImage);
		}
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
