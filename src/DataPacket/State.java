package DataPacket;

import java.util.ArrayList;
import java.util.List;

import CoreProgramPacket.Avatar;
import CoreProgramPacket.ItemCell;


public class State {
    //int life;
    private ArrayList< ItemCell > inventory;
    private Room room;
    private ArrayList < Avatar > movableTile;
    private Position position;
    
    
    
    public State(Room room, ArrayList<ItemCell> inventory, ArrayList < Avatar > movableTile) {
        this.inventory = (ArrayList<ItemCell>) inventory.clone();
        this.room = room;
        this.movableTile = (ArrayList<Avatar>) movableTile.clone();
        this.position = new Position(movableTile.get(0).getPosition());
    }
    
    public State(State state) {
        this.inventory = state.getInventory();
        this.room = state.getRoom();
    }
    
    public ArrayList<Avatar> getMovableTile() {
        return movableTile;
    }

    public void setMovableTile(ArrayList<Avatar> movableTile) {
        this.movableTile = movableTile;
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

    public String toString() {
        String a = new String();
        a += "\n====================\n";
        a += room.getDescription();
        a += "\nInventory is : ";
        if (!inventory.isEmpty()) {
            for(ItemCell i : inventory) {
                a+= (i.getName() + " ");
            }
        }
        a += "\nmovableTile is: ";
        if(!movableTile.isEmpty()) {
            for(Avatar i : movableTile) {
                a += (i.getPosition() + " ");
            }
        }
        a += "\nPosition is : ";
        a += this.position;
        a += "\n====================\n";
        return a;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
