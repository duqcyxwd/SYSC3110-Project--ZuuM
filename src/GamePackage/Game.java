package GamePackage;

import java.util.*;

import javax.swing.ImageIcon;

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
	
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> moves;
    private Stack<Command> commands;
    private Stack<Command> undoCommands;
    private Command lastCommand;  ///save the last command you did and it is using for redo
    private List<Item> inventory;
	public Room outside, theatre, pub, lab, office, cafe, basement;
    //Monster Variables
	private Map<Room, ArrayList<Monster>> monsters; // Will be used later
	private HashMap<Monster,Room> monster_map; 
	
	
	protected Tile[][] playingField;
	protected Tile[][] itemMap;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	protected ArrayList<Avatar> movableTile;
	
	protected LinkedList<Tile[][]> prevItemMaps;
	protected LinkedList<ArrayList<Avatar>> prevMovableTiles;
	protected int undoIndex = 0;
	protected int undoCount = 0;
	
	protected static ImageIcon playerImage = new ImageIcon("img/red-tile.png");
	protected static ImageIcon wallImage = new ImageIcon("img/black-tile.png");
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<Item>();        
        commands = new Stack<Command>();
        undoCommands = new Stack<Command>();
		playingField = new Tile[HEIGHT][WIDTH];	
		itemMap = new Tile[HEIGHT][WIDTH];
		movableTile = new ArrayList<Avatar>();
		prevItemMaps = new LinkedList<Tile[][]>();
		prevMovableTiles = new LinkedList<ArrayList<Avatar>>();
		populateItemMap();
		//updateUndoLists();
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
        
        // Initialise room exits
   /*     outside.setExit(pub,"east");
        outside.setExit(theatre,"west");
        outside.setExit(lab,"south");
        outside.setExit(cafe,"up");
        outside.setExit(basement, "down");
        theatre.setExit(outside, "east");
        pub.setExit(outside,"west");
        lab.setExit(outside,"north");
        lab.setExit(office,"west");
        office.setExit(lab,"east");
        cafe.setExit(outside,"down");
        basement.setExit(outside, "up");*/
        
        //Add items to rooms
     //   cafe.addItem(new Item("coffee", 1));
     //   cafe.addItem(new Item("sandwich", 2));
       
      //Adding monsters to rooms
     //  addMonstersToRoom(); //randomly adds monsters to all room 
        
        //initialize moves (for 'back' command as a stack with the first element as the first room)
        moves = new Stack<Room>();
        currentRoom = outside;  // start game outside
        moves.push(currentRoom);
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play(Position pos){      
    	Avatar hero = movableTile.get(0);
		
		Position oldPosition = hero.getPosition();
		
		Position nextPos;
		nextPos = hero.getNextPosition(pos);
		
		int nextPosRow;
		int nextPosCol;
		int foodCol;
		int foodRow;
		
		int posRow;
		int posCol;
		for(int i = 1; i < movableTile.size(); i++){

			nextPosRow = nextPos.getRow();
			nextPosCol = nextPos.getCol();
			
			foodCol = pos.getCol();
			foodRow = pos.getRow();
			
			posRow = foodRow-nextPosRow;
			posCol = foodCol-nextPosCol;
			
		}
		hero.setPosition(nextPos);
		syncItemMapAndField(movableTile);
		if (checkWin()) {
			setChanged();
			notifyObservers("Congratulations: You win!");
		}
       // printWelcome();
        

       /* boolean finished = false;
        while (! finished) {// Enter the main command loop.  Here we repeatedly read commands and
        					// execute them until the game is over.
            Command command = parser.getCommand();
            finished = processCommand(command);
      } */ 
        //System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        
        if (commandWord.equals("help")) //if use enters "help" it will call printHelp() 
            printHelp();
        else if (commandWord.equals("go")){//if use enters "go" it will call goRoom(command), 
        								   // also pushing command on the commands stack
            goRoom(command);
            commands.push(command);
            
            while(!undoCommands.isEmpty()){ // emptys undoCommand in case undo was called without a redo,
            	undoCommands.pop();			// it doesnt allow you to do redo if undo wasnt the last instruction
            }
        }else if (commandWord.equals("look")){ //if use enters "look" it will print the information 
        									   //about the current room 
            System.out.println(currentRoom.getLongDescription());
        }
        else if (commandWord.equals("undo")){ //if use enters "undo" 
            if(moves.size() > 1){			  //check to see is the moves stack has any item
                lastCommand = commands.pop();
                if(lastCommand.getCommandWord().equals("pick")){ //if the LAST COMMAND was "pick" call unPick(Room)
                	unPick(moves.peek());		// the parameter (moves.peek()) = a copy of the Room at the top 
                								// of the stack which is the current room, so unPick(Room) knows
                								// what room to add the item back
                }else{
                	moves.pop();				// if the LAST COMMAND was NOT "pick" pop from the moves stack
                }
                currentRoom = moves.peek(); 
                undoCommands.push(lastCommand);
                System.out.println(currentRoom.getLongDescription());
            }
            else
                System.out.println("No where to go back to.");
        }
        else if (commandWord.equals("redo")){   // redo the last commands. 
        	if(undoCommands.empty()){	//checks to see if undoCommands is empty
                System.out.println("You need to undo before you can redo");
        	}else{
                lastCommand = undoCommands.pop(); 
                processCommand(lastCommand); // calls processCommand(lastCommand) with the previous command
        	}

        }
        else if (commandWord.equals("pick")){ //if user enters "pick" call pick(command)
            pick(command);
            commands.push(command);
            
            while(!undoCommands.isEmpty()){ // emptys undoCommand in case undo was called without a redo,
            	undoCommands.pop();			// it doesnt allow you to do redo if undo wasnt the last instruction
            }
        }else if (commandWord.equals("inventory")){
        	if(inventory.isEmpty()){ //checks to see if inventory is empty
        		System.out.println("You have no Items in your Inventory: ");
        	}else{
        		System.out.print("In your inventory you have: ");
        		for(Item item : inventory)
        			System.out.print(item.getDescription() + " ");
        		System.out.println();
        	}
            
        }
        else if (commandWord.equals("quit")) //if user enters "quit" call quit(command) which should return true
        									 //if there was nothing else entered after "quit".
            wantToQuit = quit(command);
        
        return wantToQuit;
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp(){
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) { //if nextRoom == null then the user either entered an invalid word or the direction 
        	 					// the user entered is not an exit in the current room
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom; //changes the current room with the one the user entered
            System.out.println(currentRoom.getLongDescription());
            moves.push(currentRoom);
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
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
                inventory.add(currentRoom.getItem(item)); //add item to inventory
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
     
     /**
      *  Randomly decides to add monsters to all room
      */
  /*   private void addMonstersToRoom(){
 		Random r = new Random();
 		this.theatre.set_number_of_monster(r.nextInt(2));
 		this.pub.set_number_of_monster(r.nextInt(2));
 		this.cafe.set_number_of_monster(r.nextInt(1));
 		this.lab.set_number_of_monster(r.nextInt(3));
 		this.basement.set_number_of_monster(r.nextInt(2));
 		this.office.set_number_of_monster(r.nextInt(2));
 	} */
     
     public int getWidth(){
 		return WIDTH;
 	}
 	
 	public int getHeight(){
 		return HEIGHT;
 	}
 	
 	public Item getItem(Position position) throws IndexOutOfBoundsException{
		if(position.getRow() < 0 || position.getRow() >= HEIGHT)
			throw new IndexOutOfBoundsException("Row out of bounds."+position.getRow());
		else if(position.getCol() < 0 || position.getCol() >= WIDTH)
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
	public Tile getTile(Position position) throws IndexOutOfBoundsException{
		if(position.getRow() < 0 || position.getRow() >= HEIGHT)
			throw new IndexOutOfBoundsException("Row out of bounds.");
		else if(position.getCol() < 0 || position.getCol() >= WIDTH)
			throw new IndexOutOfBoundsException("Col out of bounds");
		else
			return playingField[position.getRow()][position.getCol()];
	}
	
	
	public void placeItem(Item item) {
		itemMap[item.getPosition().getRow()][item.getPosition().getCol()] = item;
	}
	
	public String toString(){
		String s = "";
		
		for(int i = 0; i < WIDTH; i++){
			for(int j = 0; j < HEIGHT; j++){
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
		for(int row = 0; row<HEIGHT; row++){
		for(int col =0; col<WIDTH; col++){
			if(row == 0 || col == 0 || row == HEIGHT-1 || col == WIDTH-1){
				itemMap[row][col] =new Wall(new Position(row,col),this, wallImage);
			}else{
				itemMap[row][col] =new Tile(new Position(row,col),this);
			}
		}
	}


	movableTile.add(new Player(new Position(3,3), this, playerImage, 1));

	//movableTile.add(new PlayerBlack(startTilesP2, this, "playerBlack"));
	
	}
	
	
	
	public void syncItemMapAndField(ArrayList<Avatar> movableTile){
		LinkedList<Tile> tiles = new LinkedList<Tile>();
		for(int row = 0; row < HEIGHT; row++){
			for(int col = 0; col < WIDTH; col++){
				playingField[row][col] = itemMap[row][col];
			}
		}
		
		for(Avatar mt: movableTile){ //---------FIX THIS!!!--------------------------------------------------------------------------------------
			// only place if alive
			if (mt.getLives() != 0) {
				playingField[mt.getPosition().getRow()][mt.getPosition().getCol()] = mt;
				
				//playingField[mT.getPosition().getRow()][mT.getPosition().getCol()] = mT; 
			}
		}
		setChanged();
		notifyObservers("update");
	}

	public void resetPlayingField() {
	}


	protected boolean checkWin() {
		return false;
	}


	public void restartGame() {
	}
}
