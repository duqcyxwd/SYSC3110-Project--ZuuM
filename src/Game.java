import java.util.*;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> moves;
    private List<Item> inventory;
	public Room outside, theatre, pub, lab, office, cafe, basement;
    //Monster Variables
	private Map<Room, ArrayList<Monster>> monsters; // Will be used later
	private HashMap<Monster,Room> monster_map; 
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<Item>();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
             
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cafe = new Room("in the cafe");
        basement = new Room("in the basement");
        
        // Initialize room exits
        outside.setExit(pub,"east");
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
        basement.setExit(outside, "up");
        
        //Add items to rooms
        cafe.addItem(new Item("coffee", 1));
        cafe.addItem(new Item("sandwich", 2));
       
      //Adding monsters to rooms
       addMonstersToRoom(); //randomly adds monsters to all room 
        
        //initialize moves (for 'back' command as a stack with the first element as the first room)
        moves = new Stack<Room>();
        currentRoom = outside;  // start game outside
        moves.push(currentRoom);

    }
    
    //Randomly adds monsters to all room
    private void addMonstersToRoom()
	{
		Random r = new Random();		
		this.theatre.add_number_of_monster(r.nextInt(2));
		this.pub.add_number_of_monster(r.nextInt(2));
		this.cafe.add_number_of_monster(r.nextInt(1));
		this.lab.add_number_of_monster(r.nextInt(3));
		this.basement.add_number_of_monster(r.nextInt(2));
		this.office.add_number_of_monster(r.nextInt(2));
	}


    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
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
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("look")){
            System.out.println(currentRoom.getLongDescription());
        }
        else if (commandWord.equals("back"))
        {
            if(moves.size() > 1)
            {
                moves.pop();
                currentRoom = moves.peek();    
                System.out.println(currentRoom.getLongDescription());
            }
            else
                System.out.println("No where to go back to.");
        }
        else if (commandWord.equals("pick"))
            pick(command);
        else if (commandWord.equals("inventory")){
            System.out.print("In your inventory you have: ");
            for(Item item : inventory)
               System.out.print(item.getDescription() + " ");
            System.out.println();
        }
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
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
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
         if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            moves.push(currentRoom);
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
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
     private void pick(Command command)
     {
       if(!command.hasSecondWord()){
            System.out.println("Pick what?");
            return;
        }
        else{
           String item = command.getSecondWord();
           if(currentRoom.getItem(item) == null)
                System.out.println("There is no such item in this room.");
           
           else
                inventory.add(currentRoom.getItem(item));
                currentRoom.removeItem("coffee");
            
        }
            
           
     }

        
        
    
    

    
}
