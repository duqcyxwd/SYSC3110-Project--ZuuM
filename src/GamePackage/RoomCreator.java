

package GamePackage;

import java.io.*;
import java.util.*;

/**
 * The class RoomCreator creates a room based on text files in the 'room'
 * directory in the proect directory. Looks at the files for examples of the format
 * the file name is the name of the room
 * the first line of the file is the description of the room
 * the next lines after that are the doors or exits
 * for example D,1,5,lab is an exit to the lab at location (1,5)
 * @author Mohamed Ahmed
 * @version Nov 2nd, 2012
 */
public class RoomCreator {

	List<Room> rooms;

	public RoomCreator(){

		rooms = new ArrayList<Room>();
	}



	/**
	 * This method returns reads the list of rooms from a file
	 * and creates the rooms and exits. it returns an Arraylist of rooms
	 * complete with all the exits in it.
	 * @param void
	 * @return ArrayList<Room> rooms
	 */
	
	public ArrayList<Room >getRooms(){
		File folder = new File("./rooms");
		Room currentRoom;
		File[] listOfFiles = folder.listFiles();
		String strLine;

		
		/*
		 * creates the rooms from the files in the 
		 */
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				try{
					FileInputStream fstream = new FileInputStream(listOfFiles[i]);

					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));



					strLine = br.readLine();
					System.out.println(strLine);
					currentRoom = new Room(strLine);
					currentRoom.setName(listOfFiles[i].getName());
					rooms.add(currentRoom);


					in.close();
				}
				catch(Exception e){
					System.err.println("Error: " +  e.getMessage());
				}


			} 
			else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}

		System.out.println("\n\nNow in the rooms files, trying to create doors");
		for(Room current: rooms){

			System.out.println("\nFile " + current.getName());
			try{
				FileInputStream fstream = new FileInputStream("rooms" + "//" +current.getName());

				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));



				//strLine = br.readLine();
				int counter = 0;
				String[] lineArray;
				while ((strLine = br.readLine()) != null){
					System.out.println("\n" + strLine);
					if(counter != 0){
						lineArray = strLine.split(",");
						if(lineArray[0].toCharArray()[0] == 'D'){
							System.out.println("this is a door");
							for(Room next: rooms){
								if(lineArray[3].equals(next.getName())){
									//current.setExit(next, lineArray[1] + "," + lineArray[2]);
									System.out.println("\n" + current.getName() + " now has exit " + next.getName() + " at " + lineArray[1] + "," + lineArray[2] );
								}
							}
						}
					}

					counter++;

				}



				in.close();
			}
			catch(Exception e){
				System.err.println("Error: " +  e.getMessage());
			


		}


	}

	return (ArrayList<Room>) rooms;

}


}
