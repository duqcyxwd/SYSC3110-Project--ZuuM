package GamePackage;

import java.awt.List;
import java.util.*;

public class InitializeGame {
    
    //public Room outside, theatre, pub, lab, office, cafe, basement;
    Game game;
    private ArrayList<Room> roomList;
    
    public InitializeGame(Game game) {
        this.game = game;
        this.roomList = new ArrayList < Room > ();
        //RoomCreator object = new Roomcere()
        //this.roomList = (new object).getRooms()
        
    }



    /**
     * Create all the rooms and link their exits together.
     */
    public void createRooms(Room currentRoom) {

        // create the rooms
        Room outside = new Room("outside the main entrance of the university");
        Room theatre = new Room("in a lecture theatre");
        Room pub = new Room("in the campus pub");
        Room lab = new Room("in a computing lab");
        Room office = new Room("in the computing admin office");
        Room cafe = new Room("in the cafe");
        Room basement = new Room("in the basement");

        // Initialize room exits
        outside.setExit(pub, "east");
        outside.setExit(theatre, "west");
        outside.setExit(lab, "south");
        outside.setExit(cafe, "up");
        outside.setExit(basement, "down");
        theatre.setExit(outside, "east");

        pub.setExit(outside, "west");
        lab.setExit(outside, "north");
        lab.setExit(office, "west");
        office.setExit(lab, "east");
        cafe.setExit(outside, "down");
        basement.setExit(outside, "up");

        //Add items to rooms
        cafe.addItem(new Item("coffee", 1));
        cafe.addItem(new Item("sandwich", 2));

        //Adding monsters to rooms
        addMonstersToRoom(); //randomly adds monsters to all room 
        //initialize moves (for 'back' command as a stack with the first element as the first room)
        
        
        roomList.add(outside);
        roomList.add(theatre);
        roomList.add(lab);
        roomList.add(pub);
        roomList.add(office);
        roomList.add(cafe);
        roomList.add(basement);
        
        
        currentRoom = outside; // start game outside
    }
    
    

    /**
     *  Randomly decides to add monsters to all room
     */
    private void addMonstersToRoom() {
        Random r = new Random();
        int size = roomList.size();
        for(int i = 0; i < size; i ++) {
            roomList.get(i).set_number_of_monster(r.nextInt(2));
        }
        /*
        this.theatre.set_number_of_monster(r.nextInt(2));
        this.pub.set_number_of_monster(r.nextInt(2));
        this.cafe.set_number_of_monster(r.nextInt(1));
        this.lab.set_number_of_monster(r.nextInt(3));
        this.basement.set_number_of_monster(r.nextInt(2));
        this.office.set_number_of_monster(r.nextInt(2));
        */
    }
    

}
