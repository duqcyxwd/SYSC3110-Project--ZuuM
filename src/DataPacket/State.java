package DataPacket;

import java.util.ArrayList;
import java.util.List;

import CoreProgramPacket.Avatar;
import CoreProgramPacket.ItemCell;


public class State {
    //int life;
    ArrayList< ItemCell > inventory;
    Room room;
    ArrayList < Avatar > movableTile;
    
    
    public State(Room room, ArrayList<ItemCell> inventory, ArrayList < Avatar > movableTile) {
        this.inventory = inventory;
        this.room = room;
        this.movableTile = movableTile;
    }
    
    public ArrayList<Avatar> getMovableTile() {
        return movableTile;
    }

    public void setMovableTile(ArrayList<Avatar> movableTile) {
        this.movableTile = movableTile;
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
