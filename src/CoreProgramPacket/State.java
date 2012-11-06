package CoreProgramPacket;

import java.util.ArrayList;
import java.util.List;


public class State {
    //int life;
    Cell[][] currentMap;
    ArrayList< Item > inventory;
    private Room room;
    
    public State(Cell[][] currentMap, ArrayList<Item> inventory, Room room) {
        this.currentMap = currentMap;
        this.inventory = inventory;
        this.room = room;
    }
    
    public State() {
        
    }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void setCurrentMap(Cell[][] currentMap) {
        this.currentMap = currentMap;
    }
    
    public Cell[][] getCurrentMap() {
        return currentMap;
    }

    public Room getCurretRoom() {
        return this.room;
    }
}
