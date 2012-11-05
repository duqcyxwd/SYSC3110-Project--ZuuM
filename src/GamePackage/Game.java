package GamePackage;

import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import GamePackage.Position;

import GamePackage.Avatar;
import GamePackage.Tile;


/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author Bruno Colantonio, Nishant Bhasin, Mohamed Ahmed, Yongquinchuan Du 
 * @version Oct 23rd, 2012
 */

public class Game extends Observable{
	
    private Room currentRoom;
    private List<Item> inventory;
	public Room outside, theatre, pub, lab, office, cafe, basement, previousRoom, initialRoom;
	public Exit oEast, oWest, oSouth, oUp, oDown, tEast, pWest, lNorth, lWest, ofEast, cDown, bUp;
	public Item basementItem;
       
       
    //Monster Variables
	private HashMap<Room, Monster> monsters; // Will be used later
	public HashMap<Monster,Room> monster_map; 
	public Monster monTowards, monTowards2, monTowards3;
	
	protected Cell[][] playingField;
	protected Cell[][] itemMap;
	
	protected ArrayList<Avatar> movableTile;
	
	protected LinkedList<Tile[][]> prevItemMaps;
	protected LinkedList<ArrayList<Avatar>> prevMovableTiles;
	protected int undoIndex = 0;
	protected int undoCount = 0;
    protected static ImageIcon monsterimage = new ImageIcon("img/mon-tile.png");   
    protected static ImageIcon monstertowards = new ImageIcon("img/mon-towards.png");
	
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
    	createRooms();
       // parser = new Parser();
        inventory = new ArrayList<Item>();        
       // commands = new Stack<Command>();
       // undoCommands = new Stack<Command>();
		playingField = new Cell[currentRoom.getHeight()][currentRoom.getWidth()];	
		itemMap = new Cell[currentRoom.getHeight()][currentRoom.getWidth()];
		movableTile = new ArrayList<Avatar>();
		prevItemMaps = new LinkedList<Tile[][]>();
		prevMovableTiles = new LinkedList<ArrayList<Avatar>>();
		//updateUndoLists();
		populateItemMap();
		syncItemMapAndField(movableTile);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms(){
             
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cafe = new Room("in the cafe");
        basement = new Room("in the basement");
        
        monTowards = new Monster(new Position(1,1),this, "monster1");
        monTowards2 = new Monster(new Position(9,9), this, "monster2");
        
    	oEast = new Exit(new Position(5,9), this, "east", pub);
		oWest = new Exit(new Position(5,1), this, "west", theatre);
		oSouth = new Exit(new Position(9,5), this, "south", lab);
		oUp = new Exit(new Position(3,3), this, "up", cafe);
		oDown = new Exit(new Position(7,7), this, "down", basement);
		tEast = new Exit(new Position(5,9), this, "east", outside);
		pWest = new Exit(new Position(5,1), this, "west", outside);
		lNorth = new Exit(new Position(1,5), this, "north", outside);
		lWest = new Exit(new Position(5,1), this, "west", office);
		ofEast = new Exit(new Position(5,9), this, "east", lab);
		cDown = new Exit(new Position(7,7), this, "down", outside);
		bUp = new Exit(new Position(3,3), this, "up", outside);
		
		basementItem = new Item(new Position(3,3), this, "apple");
		
        // Initialise room exits
        outside.setExit(oEast);
        outside.setExit(oWest);
        outside.setExit(oSouth);
        outside.setExit(oUp);
        outside.setExit(oDown);
        theatre.setExit(tEast);
        pub.setExit(pWest);
        lab.setExit(lNorth);
        lab.setExit(lWest);
        office.setExit(ofEast);
        cafe.setExit(cDown);
        basement.setExit(bUp);
        //basement.addItem(basementItem);
        
        //adding monster    
        cafe.addMonster(monTowards);
      //  lab.addMonster(monTowards3);
        theatre.addMonster(monTowards2);
        monsters = new HashMap<Room, Monster>();
        monsters.put(cafe, monTowards);
       // monsters.put(lab, monTowards3);
        monsters.put(theatre, monTowards);
        initialRoom = outside;
       previousRoom =  currentRoom = outside;
        
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play(Position pos){  
		int exitCol,exitRow,heroCol,heroRow;
		Position roomChangePos = null;
    	JOptionPane Jpane;
		Jpane = new JOptionPane();
    	Avatar hero = movableTile.get(0);
		Position nextPos;
		nextPos = hero.getNextPosition(pos);
		
		for(Exit e : currentRoom.getExit()){
        	if(e.getPosition().equals(nextPos)){
        		
        		removeExits();
        		exitRow = e.getPosition().getRow();
        		exitCol = e.getPosition().getCol();
        		heroRow = currentRoom.getHeight()-1 - exitRow;
        		heroCol = currentRoom.getWidth()-1 - exitCol;
        		
        		roomChangePos = new Position(heroRow, heroCol);
        	    		
        		this.previousRoom = currentRoom;
        		currentRoom = e.getNextRoom();
        		System.out.println(currentRoom);
        	}
        }
		if(roomChangePos!=null){
			nextPos = roomChangePos;
		}
		hero.setPosition(nextPos);
			if(currentRoom.getMonster().size()!=0){
				Avatar mon = movableTile.get(1);
				Position monCurrent = mon.getPosition();			
				Position monNextPosition = mon.getNextPosition(hero.getPosition());
				for(Exit e : currentRoom.getExit()){
					if(e.getPosition().equals(monNextPosition)){
						monNextPosition = monCurrent;
						mon.setPosition(monNextPosition);
						removeMonster();
					}else{
						mon.setPosition(monNextPosition);
					}
				}		
					
				if(mon.collidesWith(movableTile.get(0))){
					hero.setPosition(new Position(1,1));
					currentRoom = initialRoom;
					System.out.println(currentRoom);
					Jpane.showMessageDialog(null, "You died. You have" + hero.getLives() + " lives left ");
					System.out.println("Game Over");
					
				}else if(movableTile.get(0).getLives()==0){
					Jpane.showMessageDialog(null, "Game Over. You have 0 lives LEFT !!");
					System.out.println("Game Over");
				}
			}
			else{
				removeMonster();
			}
	
		syncItemMapAndField(movableTile);
		if (checkWin()) {
			setChanged();
			notifyObservers("Congratulations: You win!");
		}

      
    }


     /**
      * "Pick was entered. Process to see what we can pick up"
      */
     private void pick(Command command){
       if(!command.hasSecondWord()){ // if user forgot to enter what to pick
            System.out.println("Pick what?");
            return;
        }
        else{
           String item = command.getSecondWord();
           if(currentRoom.getItem(item) == null){ // if there is no such item in the current room
                System.out.println("There is no such item in this room.");
           
           }else{
               // inventory.add(currentRoom.getItem(item)); //add item to inventory
           		System.out.println("You added " + item + " to your inventory");
                currentRoom.removeItem(item); //remove item from room
           }
        }
     }
     
     /**
      * unPick is called when you picked up an item and you undo,
      * removing the item from inventory and putting it back in the room
      */
     private void unPick(Room itemRoom){
    	 int invSize = inventory.size();
    	 if(invSize>=1){ // if inventory if greater than 1 (which it should always be)
        	 System.out.println("You unpicked " + inventory.get(invSize-1).getDescription() + " and returned " + itemRoom);
    		 currentRoom.addItem(inventory.get(invSize-1)); //return the item to the room
        	 inventory.remove(invSize-1); //remove item from inventory
    	 }
     }
     
 	
 	public Item getItem(Position position) throws IndexOutOfBoundsException{
		if(position.getRow() < 0 || position.getRow() >= currentRoom.getHeight())
			throw new IndexOutOfBoundsException("Row out of bounds."+position.getRow());
		else if(position.getCol() < 0 || position.getCol() >= currentRoom.getWidth())
			throw new IndexOutOfBoundsException("Col out of bounds"+position.getCol());
		else {
			if (itemMap[position.getRow()][position.getCol()] instanceof Item)
				return (Item) itemMap[position.getRow()][position.getCol()];
		} return null;
	}
 	/**
	 * Returns the tile at said position or throws an exception if the position is invalid.
	 * @param position The position of interest
	 * @return Tile at position, null if no item
	 */
	public Cell getCell(Position position) throws IndexOutOfBoundsException{
		if(position.getRow() < 0 || position.getRow() >= currentRoom.getHeight())
			throw new IndexOutOfBoundsException("Row out of bounds.");
		else if(position.getCol() < 0 || position.getCol() >= currentRoom.getWidth())
			throw new IndexOutOfBoundsException("Col out of bounds");
		else
			return playingField[position.getRow()][position.getCol()];
	}
	
	
	public void placeItem(Item item) {
		itemMap[item.getPosition().getRow()][item.getPosition().getCol()] = item;
	}
	
	public String toString(){
		String s = "";
		
		for(int i = 0; i < currentRoom.getWidth(); i++){
			for(int j = 0; j < currentRoom.getHeight(); j++){
				s += " " + playingField[i][j].toString();
			}
			s += "\n";
		}
		return s;
	}

	
	public void undoMove(){
	}
	
	public void redoMove(){
		
	}
	
	protected void populateItemMap(){
		
		for(int row = 0; row<currentRoom.getHeight(); row++){
			for(int col =0; col<currentRoom.getWidth(); col++){
				if(row == 0 || col == 0 || row == currentRoom.getHeight()-1 || col == currentRoom.getWidth()-1){
					itemMap[row][col] =new Wall(new Position(row,col),this);
				}else{
					itemMap[row][col] =new Cell(new Position(row,col),this);
				}
			}
		}	
       
        movableTile.add(new Player(new Position(5,4), this, 5));
		for(Monster m: monsters.values()){
			movableTile.add(m);
		}
		
	}
	
	
	/**
	 * Tells if the room has changed or not.
	 * @return
	 */
	public boolean currentRoomChanged()
	{
		Room r = previousRoom; 
		if(this.currentRoom != r){
				return true;
		}
		else{
			return false;
		}
		
	}
	public void syncItemMapAndField(ArrayList<Avatar> movableTile){
		
		for(Exit e : currentRoom.getExit()){
        	itemMap[e.getPosition().getRow()][e.getPosition().getCol()] = e;
        }
		for(int row = 0; row < currentRoom.getHeight(); row++){
			for(int col = 0; col < currentRoom.getWidth(); col++){
				playingField[row][col] = itemMap[row][col];
			}
		}
		for(Avatar m: movableTile){ //---------FIX THIS!!!--------------------------------------------------------------------------------------
			// only place if alive

			if (currentRoom.getMonster().size()!=0 && m instanceof Monster && (m == currentRoom.getMonster().get(0)) ){
				playingField[m.getPosition().getRow()][m.getPosition().getCol()] = m;				
			}
			if (m.getLives() != 0 && m instanceof Player) {
				playingField[m.getPosition().getRow()][m.getPosition().getCol()] = m;
				
			}
		}
		
		for(Exit e : currentRoom.getExit()){
        	itemMap[e.getPosition().getRow()][e.getPosition().getCol()] = e;
        }

		setChanged();
		notifyObservers("update");
	}
	
	public void removeExits(){
		for(int row = 0; row < currentRoom.getHeight(); row++){
			for(int col = 0; col < currentRoom.getWidth(); col++){
				if(itemMap[row][col] instanceof Exit){
					itemMap[row][col] = new Cell(new Position(row,col), this);
				}
			}
		}
	}

	public void removeMonster(){
		for(int row = 0; row < currentRoom.getHeight(); row++){
			for(int col = 0; col < currentRoom.getWidth(); col++){
				if(itemMap[row][col] instanceof Monster){
					itemMap[row][col] = new Cell(new Position(row,col), this);
				}
			}
		}
	}
	
	public Room getCurrentRoom(){
		return currentRoom;
	}
	
	/**
	 * returns the Hero of the current game
	 * @return Hero
	 */
	public Player getUser() {
		return (Player) movableTile.get(0);
	}

	public void resetPlayingField() {
	}


	protected boolean checkWin() {
		return false;
	}


	public void restartGame() {
	}
}
