package GamePackage;

import java.util.ArrayList;
import java.util.List;

public class State {
    //int life;
    Cell[][] currentMap;
    ArrayList< Item > inventory;
    
    public State(Cell[][] currentMap, ArrayList<Item> inventory) {
        this.currentMap = currentMap;
        this.inventory = inventory;
    }
    
    public void getLife() {
        
        
    }
    
    public ArrayList getItems() {
        return inventory;
    }
    
    public Cell[][] getCurrentMap() {
        return currentMap;
    }

}
