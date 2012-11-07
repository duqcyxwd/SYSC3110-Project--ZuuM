package CoreProgramPacket;

import java.util. * ;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import DataPacket.MonsterCell;
import DataPacket.PlayerCell;
import DataPacket.Position;
import DataPacket.Room;
import DataPacket.State;
import UI_1DPacket.Command;
import UI_2DPacket.Tile;


/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Bruno Colantonio, Nishant Bhasin, Mohamed Ahmed, Yongquinchuan Du
 * @version Oct 23rd, 2012
 */

public class Game extends Observable {

    private Room currentRoom;
    //private List < Item > inventory;
    public Room outside, theatre, pub, lab, office, cafe, basement, previousRoom, initialRoom;
    public Exit oEast, oWest, oSouth, oUp, oDown, tEast, pWest, lNorth, lWest, ofEast, cDown, bUp;
    
    private ArrayList< ItemCell > inventory;

    // Monster Variables
    private HashMap < Room, MonsterCell > monsters; // Will be used later
    public MonsterCell monTowards, monTowards2;
    
    protected Cell[][] playingField;

    private ArrayList < Avatar > movableTile;
    
    protected int undoIndex = 0;
    protected int undoCount = 0;

    private Stack < State > stateStack;
    private Stack < State > redoStateStack;
    //private State currentState;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        //inventory = new ArrayList < Item > ();
        playingField = new Cell[currentRoom.getHeight()][currentRoom.getWidth()];
        movableTile = new ArrayList < Avatar > ();
        populateItemMap();
        syncItemMapAndField(movableTile);
        
        inventory =  new ArrayList < ItemCell>();
        
        //currentState = new State();
        
        stateStack = new Stack < State > ();
        redoStateStack = new Stack < State >();
        
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {

        // create the rooms
        outside = new Room("outside");
        theatre = new Room("lecture theatre");
        pub = new Room("campus pub");
        lab = new Room("computing lab");
        office = new Room("admin office");
        cafe = new Room("cafe");
        basement = new Room("basement");

        monTowards = new MonsterCell(new Position(1, 1), this, "monster1");
        monTowards2 = new MonsterCell(new Position(1, 8), this, "monster2");

        oEast = new Exit(new Position(5, 9), this, "east", pub);
        oWest = new Exit(new Position(5, 1), this, "west", theatre);
        oSouth = new Exit(new Position(9, 5), this, "south", lab);
        oUp = new Exit(new Position(3, 3), this, "up", cafe);
        oDown = new Exit(new Position(7, 7), this, "down", basement);
        tEast = new Exit(new Position(5, 9), this, "east", outside);
        pWest = new Exit(new Position(5, 1), this, "west", outside);
        lNorth = new Exit(new Position(1, 5), this, "north", outside);
        lWest = new Exit(new Position(5, 1), this, "west", office);
        ofEast = new Exit(new Position(5, 9), this, "east", lab);
        cDown = new Exit(new Position(7, 7), this, "down", outside);
        bUp = new Exit(new Position(3, 3), this, "up", outside);


        ItemCell shield = new ItemCell(new Position(2,8), this, "shield");
        ItemCell life = new ItemCell(new Position (7,7), this, "life");
        ItemCell key = new ItemCell(new Position (1,1), this, "key");
        ItemCell food = new ItemCell(new Position (1,9), this, "food");
        ItemCell trophy = new ItemCell(new Position (5,5), this, "trophy");
        cafe.addItem(shield);
        lab.addItem(life);
        basement.addItem(key);
        outside.addItem(food);
        office.addItem(trophy);

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
        // basement.addItem(basementItem);
        // adding monster
        cafe.addMonster(monTowards);
        theatre.addMonster(monTowards2);
        monsters = new HashMap < Room, MonsterCell > ();
        monsters.put(cafe, monTowards);
        monsters.put(theatre, monTowards2);
        initialRoom = outside;
        previousRoom = currentRoom = outside;
        //Adding items to cafe
        

    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play(Position pos) {
        int exitCol, exitRow, heroCol, heroRow;
        Position roomChangePos = null;
        Avatar hero = movableTile.get(0);
        Position nextPos;
        nextPos = hero.getNextPosition(pos);
        State currentState = new State(this.currentRoom, this.inventory, this.movableTile);
        stateStack.push(currentState);
        
        for(ItemCell i: currentRoom.getItem()) {
        	if(i.getPosition().equals(nextPos)) {
        		pick(i);
        		break;
        	}
        }
        
        for(Exit e: currentRoom.getExit()) {
            if(e.getPosition().equals(nextPos)) {
            	if(e.getNextRoom().equals(office)){
            		if(!((PlayerCell) hero).haveKey()){
		    			setChanged();
		    			notifyObservers("You need the key in the basement before you can enter!");
		    			return;
            		}
            	}

                removeExits();
            	removeItems();
                exitRow = e.getPosition().getRow();
                exitCol = e.getPosition().getCol();
                heroRow = currentRoom.getHeight() - 1 - exitRow;
                heroCol = currentRoom.getWidth() - 1 - exitCol;

                roomChangePos = new Position(heroRow, heroCol);

                this.previousRoom = currentRoom;
                currentRoom = e.getNextRoom();
                System.out.println(currentRoom);
            }
        }
        if(roomChangePos != null) {
            nextPos = roomChangePos;
        }
        if(((PlayerCell) hero).haveFood()){ // make guy go faster potentially
        }
        hero.setPosition(nextPos);
        // something wrong with following logic but leave it for temperatory
        if(currentRoom.getMonster().size() != 0) {
        	for(MonsterCell m : currentRoom.getMonster()){
	            Position monCurrent = m.getPosition();
	            Position monNextPosition = m.getNextPosition(hero.getPosition());
	            for(Exit e: currentRoom.getExit()) {
	                if(e.getPosition().equals(monNextPosition)) {
	                    monNextPosition = monCurrent;
	                    m.setPosition(monNextPosition);
	                    removeMonster();
	                } else {
	                    m.setPosition(monNextPosition);
	                }
	            }
	
	            if(m.collidesWith(hero)) {
	            	if(((PlayerCell) hero).haveShield()){
		            	m.setPosition(new Position(1, 1));
		            	hero.addLife();
		            	syncItemMapAndField(movableTile);
		    			setChanged();
		    			notifyObservers("The shield blocked you!");
		    			return;
	            	}else{
		                hero.setPosition(new Position(5, 4));
		                currentRoom = initialRoom;
		                //System.out.println(currentRoom);
						setChanged();
						notifyObservers("You died. You have" + hero.getLives() + " lives left ");
						return;
	            	}
	            }
	            if(hero.getLives() == 0) {
					setChanged();
					notifyObservers("Game Over. You have 0 lives LEFT !!");
					return;
	            }
        	}
        } else {
            removeMonster();
        }

        syncItemMapAndField(movableTile);
        if(checkWin()) {
            setChanged();
            notifyObservers("Congratulations: You win!");
        }

    }

    /**
     * "Pick was entered. Process to see what we can pick up"
     */
    private void pick(ItemCell item) {
    	if(item.getName().equals("life")){
    		getUser().addLife();
    	}else{
    		getUser().addItem(item);
    		getCurrentRoom().removeItem(item);
    		playingField[item.getPosition().getRow()][item.getPosition().getRow()] = new Cell(new Position(item.getPosition().getRow(),item.getPosition().getRow()), this);
    	}
    }

    public ItemCell getItem(Position position) throws IndexOutOfBoundsException {
        if(position.getRow() < 0 || position.getRow() >= currentRoom.getHeight()) throw new IndexOutOfBoundsException("Row out of bounds." + position.getRow());
        else if(position.getCol() < 0 || position.getCol() >= currentRoom.getWidth()) throw new IndexOutOfBoundsException("Col out of bounds" + position.getCol());
        else {
            if(playingField[position.getRow()][position.getCol()] instanceof ItemCell) return(ItemCell) playingField[position.getRow()][position.getCol()];
        }
        return null;
    }

    /**
     * Returns the tile at said position or throws an exception if the position
     * is invalid.
     *
     * @param position
     *            The position of interest
     * @return Tile at position, null if no item
     */
    public Cell getCell(Position position) throws IndexOutOfBoundsException {
        if(position.getRow() < 0 || position.getRow() >= currentRoom.getHeight()) throw new IndexOutOfBoundsException("Row out of bounds.");
        else if(position.getCol() < 0 || position.getCol() >= currentRoom.getWidth()) throw new IndexOutOfBoundsException("Col out of bounds");
        else return playingField[position.getRow()][position.getCol()];
    }

    public void placeItem(ItemCell item) {
        playingField[item.getPosition().getRow()][item.getPosition().getCol()] = item;
    }

    public String toString() {
        String s = "";

        for(int i = 0; i < currentRoom.getWidth(); i++) {
            for(int j = 0; j < currentRoom.getHeight(); j++) {
                s += " " + playingField[i][j].toString();
            }
            s += "\n";
        }
        return s;
    }

    protected void populateItemMap() {

        for(int row = 0; row < currentRoom.getHeight(); row++) {
            for(int col = 0; col < currentRoom.getWidth(); col++) {
                if(row == 0 || col == 0 || row == currentRoom.getHeight() - 1 || col == currentRoom.getWidth() - 1) {
                    playingField[row][col] = new WallCell(new Position(row, col), this);
                } else {
                    playingField[row][col] = new Cell(new Position(row, col), this);
                }
            }
        }

        movableTile.add(new PlayerCell(new Position(5, 5), this, 3));
        for(MonsterCell m: monsters.values()) {
            movableTile.add(m);
        }
        
        if(!currentRoom.getItem().isEmpty()) {
			for(ItemCell i : currentRoom.getItem()){
				playingField[i.getPosition().getRow()][i.getPosition().getCol()] = i;
			}
		}

    }

    /**
     * Tells if the room has changed or not.
     *
     * @return
     */
    public boolean currentRoomChanged() {
        Room r = previousRoom;
        if(this.currentRoom != r) {
            return true;
        } else {
            return false;
        }

    }

    public void syncItemMapAndField(ArrayList < Avatar > movableTile) {
    	
    	if(currentRoom.getItem()!=null){
			for(ItemCell i : currentRoom.getItem()){
				playingField[i.getPosition().getRow()][i.getPosition().getCol()] = i;
			}
		}
        
        this.removeAvatar();
        for(Exit e: currentRoom.getExit()) {
            playingField[e.getPosition().getRow()][e.getPosition().getCol()] = e;
        }


        for(Avatar m: movableTile) { // ---------FIX
            // THIS!!!--------------------------------------------------------------------------------------
            // only place if alive
            if(currentRoom.getMonster().size() != 0 && m instanceof MonsterCell && (m == currentRoom.getMonster().get(0))) {
                playingField[m.getPosition().getRow()][m.getPosition().getCol()] = m;
            }
            if(m.getLives() != 0 && m instanceof PlayerCell) {
                playingField[m.getPosition().getRow()][m.getPosition().getCol()] = m;

            }
        }

        setChanged();
        notifyObservers("update");
    }

    public void removeExits() {
        for(int row = 0; row < currentRoom.getHeight(); row++) {
            for(int col = 0; col < currentRoom.getWidth(); col++) {
                if(playingField[row][col] instanceof Exit) {
                    playingField[row][col] = new Cell(new Position(row, col), this);
                }
            }
        }
    }
    
    public void removeItems(){
		for(int row = 0; row < currentRoom.getHeight(); row++){
			for(int col = 0; col < currentRoom.getWidth(); col++){
				if(playingField[row][col] instanceof ItemCell){
					playingField[row][col] = new Cell(new Position(row,col), this);
				}
			}
		}
	}

    public void removeMonster() {
        for(int row = 0; row < currentRoom.getHeight(); row++) {
            for(int col = 0; col < currentRoom.getWidth(); col++) {
                if(playingField[row][col] instanceof MonsterCell) {
                    playingField[row][col] = new Cell(new Position(row, col), this);
                }
            }
        }
    }

    public void removeAvatar() {
        for(int row = 0; row < currentRoom.getHeight(); row++) {
            for(int col = 0; col < currentRoom.getWidth(); col++) {
                if(playingField[row][col] instanceof MonsterCell || playingField[row][col] instanceof PlayerCell) {
                    playingField[row][col] = new Cell(new Position(row, col), this);
                }
            }
        }
    }
    

    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * returns the Hero of the current game
     *
     * @return Hero
     */
    public PlayerCell getUser() {
        return(PlayerCell) movableTile.get(0);
    }

    public void resetPlayingField() {}

    protected boolean checkWin() {
    	if(getUser().haveTrophy()){
    		return true;
    	}
    	return false;
    }

    public void restartGame() {
    	currentRoom = initialRoom;
        playingField = new Cell[currentRoom.getHeight()][currentRoom.getWidth()];
		movableTile = new ArrayList<Avatar>();
		populateItemMap();
		syncItemMapAndField(movableTile);
    }
    
    
    public void updateByState(State currentState){
        Room room0 = currentState.getRoom();
        ArrayList< ItemCell > inventory0 = currentState.getInventory();
        ArrayList < Avatar > movableTile0 = currentState.getMovableTile();
        this.currentRoom = new Room(room0);
        this.inventory = inventory0;
        this.movableTile =movableTile0;
        syncItemMapAndField(movableTile);
        System.out.println("test");
    }

    /*
     * undo method called by click button inventory life monster
     */
    public void undo() {
        State currentState;
        
        currentState = new State(this.currentRoom, this.inventory, this.movableTile);
        
        if(!stateStack.isEmpty()) {
           // System.out.println("undo");

           // System.out.println(stateStack);

            System.out.println("----------------------------------------------------------------------");
            currentState = new State(this.currentRoom, this.inventory, this.movableTile);
            redoStateStack.push(currentState);
            currentState = stateStack.pop();
            
           // System.out.println(stateStack);
            updateByState(currentState);
        }
    }

    /*
     * redo mehtod called by click redo button if there is a new
     */
    public void redo() {
        if (!redoStateStack.isEmpty()) {
            State currentState = new State(this.currentRoom, this.inventory, this.movableTile);
            stateStack.push(currentState);
            
            //System.out.println("redo");
            currentState = redoStateStack.pop();
            updateByState(currentState);
        }
        
    }
}