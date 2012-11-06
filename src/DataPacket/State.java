package DataPacket;

import java.util.ArrayList;
import java.util.List;

import CoreProgramPacket.ItemCell;


public class State {
    //int life;
    ArrayList< ItemCell > inventory;
    Room room;
    
    public State(Room room, ArrayList<ItemCell> inventory) {
        this.inventory = inventory;
        this.room = room;
    }
    
    public State() {
                
    }
    
    public State(State state) {
        this.inventory = state.getInventory();
        this.room = state.getRoom();
    }
    
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ArrayList<ItemCell> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<ItemCell> inventory) {
        this.inventory = inventory;
    }

}
