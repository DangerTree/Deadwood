/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Gameboard class for Deadwood Assignment 2
*/

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;


public class Board{

  private static int daysLeft;
  private static int scenesLeft;
  private static ArrayList<Room> roomList = new ArrayList<Room>();
  private static LinkedList<Scene> sceneDrawPile = new LinkedList<Scene>();

  private static Board boardObj = new Board();

  public static Board getBoard(int dayNum){
      boardObj.daysLeft = dayNum;
      return boardObj;
  }
  
  public static void initializeSceneDrawPile(){
	  /*
	  File scene_file = new File ("")
	  
	  for(int i = 0; i < 40; i ++){
		  
	  }*/
  }

  /* setupRooms: initializes all rooms
  INPUT: a file describing the contents of each room (and off-card scenes)
  POST-CONDITION: 10 rooms w/scenes and off-card roles and 2 rooms w/o scenes...
    ...(trailer and casting office) are created and added to roomList
  */
  public void setupRooms(){

    File room_file = new File ("roomInfo2.txt");
    Scanner scan = null;
    try {
      scan = new Scanner (room_file);//.useDelimiter("_");

      // goes through file descibing content of 10 acting rooms (not trailer or casting office)
      while (scan.hasNextLine() != false){
        // create the room / parse the first line
        String[] line = scan.nextLine().split("_");
        //System.out.println ("room: " + line[0] + " shot ctrs: " + line[1]);
        Room myRoom = new Room (line[0], Integer.parseInt(line[1]));
        myRoom.placeScene(sceneDrawPile.remove());//we could recycle scenes in a later version
        // parse all roles in the room
        int roleNum = Integer.parseInt(line[2]);
        for (int i = 0; i < roleNum; i++){
          String[] roleLine = scan.nextLine().split("_");
          System.out.println ("role rank: " + roleLine[0] + " role name: " + roleLine[1] + " quote: " + roleLine[2]);
          myRoom.addRole (Integer.parseInt(roleLine[0]), roleLine[1], roleLine[2]);
        }
        /*
        System.out.println("Start:");
        // Create a new room object
        String roomName = scan.next();
        String sc = scan.next();
        String nr = scan.next();
        Integer shotCtr = Integer.parseInt(sc);
        Integer numRoles = Integer.parseInt(nr);
        System.out.println (roomName + " " + shotCtr + " " + numRoles);
        Room myRoom = new Room (roomName, shotCtr);
        // place a scene in the room
        myRoom.placeScene();
        // add the room's off-card roles to its role list
        String rr = "";
        int rRank = 1;
        String RoleName = "";
        String RoleQuote = "";
        for (int j = 0; j < numRoles; j++){
      	  rr = scan.next();
          rRank = Integer.parseInt(rr);
          RoleName = scan.next();
          RoleQuote = scan.next();
          myRoom.addRole (rRank, RoleName, RoleQuote);
        }
        // add room to list of rooms
        roomList.add(myRoom);
        */
      }
      // setup casting office and trailer

      roomList.add(new Room ("Casting Office"));
      roomList.add(new Room ("Trailers"));
    }
    catch (FileNotFoundException e){
      System.out.println ("roomInfo file not found.");
      System.exit(1);
    }
    catch (NumberFormatException e){
      System.out.println ("roomInfo file formatted incorrectly.");
      System.exit(1);
    }
  }


  /* endDay:
  POST-CONDITION: Players are in Trailers, all Rooms have new Scenes and full shot ctrs
  NOTE: discard last scene card?
  */
  public void endDay(){
    for (int i = 0; i < roomList.size(); i++){
      String rName = roomList.get(i).getRName();
      if (! rName.equals("Casting Office") && ! rName.equals("Trailers")){
        roomList.get(i).resetShotCounter();
        roomList.get(i).placeScene();
      }
    }
  }


  // getDaysLeft: Returns the number of days left in the game
  public static int getDaysLeft(){
    return daysLeft;
  }

  // getScenesLeft: Returns the number of scenes left on the board
  public static int getScenesLeft(){
    return scenesLeft;
  }

  // decSceneNum: Recrements scenesLeft (the number of scenes on board) by one
  public static void decSceneNum(){
    scenesLeft--;
  }

}
