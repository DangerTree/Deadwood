/*
The gameboard class for Deadwood game
*/
import java.util.Scanner;
import java.util.ArrayList;

public class Board{


    private static int daysLeft;
    private static int scenesLeft;
    private static ArrayList<Room> roomList = new ArrayList<Room>();

    private static Board boardObj = new Board();

    public static Board getBoard(int dayNum){
        boardObj.daysLeft = dayNum;
        return boardObj;
    }

    /* setupRooms: initializes all rooms
    INPUT: a file describing the contents of each room (and off-card scenes)
    POST-CONDITION: 10 rooms w/scenes and 2 rooms w/o scenes are created and added to roomList
    */
    public void setupRooms(){

      Scanner scan = new Scanner ("Jail_2_1\n3_hi_sup").useDelimiter("_");

      // goes through file descibing content of 10 acting rooms (not trailer or casting office)
      while (scan.hasNext() != false){
        // Create a new room object
        String roomName = scan.next();
        int shotCtr = Integer.parseInt(scan.next());
        int numRoles = Integer.parseInt(scan.next());
        Room myRoom = new Room (roomName, shotCtr);
        // place a scene in the room
        myRoom.placeScene();
        // add the room's off-card roles to its role list
        for (int j = 0; j < numRoles; j++){
          myRoom.addRole (scan.nextLine());
        }
        // add room to list of rooms
        roomList.add(myRoom);
      }
      // setup casting office and trailer
      roomList.add(new Room ("Casting Office"));
      roomList.add(new Room ("Trailer"));
    }


    // moves players to Trailers, deals new scene cards to rooms, and replaces shot ctrs
    // NOTE: discard last scene card?
    public void endDay(){
      // replace shot counters and deal new scene cards to rooms
      for (int i = 0; i < roomList.size()-2; i++){
        roomList.get(i).resetShotCounter();
        roomList.get(i).placeScene();
      }
    }


    // getDaysLeft: returns the number of days left in the game
    public static int getDaysLeft(){
      return daysLeft;
    }

    // getScenesLeft: returns the number of scenes left on the board
    public static int getScenesLeft(){
      return scenesLeft;
    }

    // decSceneNum: decrements scenesLeft (the number of scenes on board) by one
    public static void decSceneNum(){
      scenesLeft--;
    }

}
