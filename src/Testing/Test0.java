package Testing;

import java.util.*;
import java.util.Stack;

import CoreProgramPacket.*;


public class Test0 {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {

/*
        //currentState = new State(playingField, (ArrayList<Item>) inventory);
        //currentState = new State(new Cell[currentRoom.getHeight()][currentRoom.getWidth()], new ArrayList < Item > ());
        State currentState = new State();
        ArrayList b = new ArrayList < ItemCell > ();
      //  b.add(new ItemCell());
        currentState = new State(new Cell[55][66], b);
        Stack < State > stateStack = null;
        stateStack.push(currentState);*/

	    ArrayList < Test_item > items = new ArrayList< Test_item >();
        for (int i = 0; i < 5; i++) {
            items.add(new Test_item(i));
		}
        Stack < Test_item > itemStack = new Stack < Test_item >();
	    
        Test_item b = new Test_item(12);
        b = items.get(4);
        b = new Test_item(13);
        System.out.println(items.get(4));
        
        b = items.get(3);
        b.setA(88);
        System.out.println(items.get(3));
        
        
        
	    

	    
	}

}
