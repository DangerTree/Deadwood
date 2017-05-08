/*
The gameboard class for Deadwood game
*/
import java.util.Scanner;
import java.util.ArrayList;

public class Board{


    private int daysLeft;
    private int scenesLeft;
    private static ArrayList<Room> roomList = new ArrayList<Room>();

    private static Board boardObj = new Board();

    public static Board getBoard(int dayNum){
        boardObj.daysLeft = dayNum;
        return boardObj;
    }

    // creates 12 room objects, standard for Deadwood
    public void setupRooms(){

      Scanner scan = new Scanner ("Jail_2_1\n3_hi_sup").useDelimiter("_");

      // goes through file descibing content of 10 acting rooms (not trailer or casting office)
      while (scan.hasNext() != false){
        // Create new room obj, passing roomName, shotCtr
        String roomName = scan.next();
        int shotCtr = Integer.parseInt(scan.next());
        int numRoles = Integer.parseInt(scan.next());
        Room myRoom = new Room (roomName, shotCtr);
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

    public void endScene(){

    }

    public int getDaysLeft(){
      return this.daysLeft;
    }

    public int getScenesLeft(){
      return this.scenesLeft;
    }

    public void decSceneNum(){
      this.scenesLeft--;
    }

}
