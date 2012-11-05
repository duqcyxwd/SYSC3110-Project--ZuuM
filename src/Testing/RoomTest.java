package Testing;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import GamePackage.Item;
import GamePackage.Room;


//public class RoomTest extends Room {
public class RoomTest{
    //variable declarations
    private Room testRoom;
    
/*    public RoomTest() {
        
    }
*/

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
       testRoom = new Room("Room class test");
    }

    @After
    public void tearDown() throws Exception {
    }

    //test cases
    @Test
    public void testSet_number_of_monster() {
        testRoom.set_number_of_monster(20);
        assertEquals(20, testRoom.getMonster());
        
    }

    @Test
    public void testAddMonster() {
        testRoom.addMonster();
        assertEquals(1, testRoom.getMonster());
    }

    @Test
    public void testGetMonster() {
        testRoom.set_number_of_monster(30);
        assertEquals(30, testRoom.getMonster());
    }

    @Test
    public void testGetDescription() {
        String temp = "Testing Description";
        testRoom = new Room(temp);
        assertEquals(temp, testRoom.getDescription());
    }

    @Test
    public void testAddItem() {
    /*    String itemName = "Test Item";
        Item item = new Item(itemName, 5);
        testRoom.addItem(item);
        assertEquals(testRoom.getItem(itemName), item);*/
    }

/*    @Test
    public void testGetItem() {
        fail("Not yet implemented"); // TODO
    }*/

    @Test
    public void testRemoveItem() {
     /*   String itemName = "Test Item";
        Item item = new Item(itemName, 5);
        testRoom.addItem(item);
        testRoom.removeItem(itemName);
        
        assertNull(testRoom.getItem(itemName));*/
        
    }

    @Test
    public void testSetExit() {
        Room a = new Room("another room"); //add another room as exit
        String direction = "west";
        //testRoom.setExit(a, direction);
        assertEquals(a, testRoom.getExit(direction));
    }

/*    @Test
    public void testGetExit() {
        fail("Not yet implemented"); // TODO
    }
*/
    @Test
    public void testGetExitString() {
        Room a = new Room("another room"); //add another room as exit
        String direction = "west";
        
        Room b = new Room("another room"); //add another room as exit
        String direction2 = "north";
        
        Room c = new Room("another room"); //add another room as exit
        String direction3 = "south";
        
        
       // testRoom.setExit(a, direction);
        //testRoom.setExit(b, direction2);
        //testRoom.setExit(c, direction3);

 /*       System.out.println(testRoom.getExitString());
        System.out.println(direction3 + " "+ direction2 + " "+ direction + " ");
        
        System.out.println(testRoom.getExitString().equals(direction3 + " "+ direction2 + " "+ direction + " "));
*/
        assertTrue(testRoom.getExitString().equals(direction3 + " "+ direction2 + " "+ direction + " "));
    }

    @Test
    public void testGetLongDescription() {
        fail("\n  Not yet implemented"); // TODO
    }

}
