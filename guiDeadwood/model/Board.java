/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Gameboard class for Deadwood Assignment 2

SHOULD SCENE CARDS SHUFFLE?????
*/
package model;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


public class Board{

  private static int daysLeft;
  private static int scenesLeft;
  private static HashMap <String, Room> roomHashMap= new HashMap <String, Room>();
  private static LinkedList<Scene> sceneDrawPile = new LinkedList<Scene>();
  private static int[][] upgradeReqs = {{4,5}, {10,10}, {18,15}, {28,20}, {40, 25}};
  private static HashMap <String, Role> roleHashMap = new HashMap <String, Role>();

  private static Board boardObj = new Board();

  // getBoard is called by model.Deadwood
  // initializes a board object and sets the daysLeft to # days remaining in game
  public static Board getBoard(int dayNum){
      boardObj.daysLeft = dayNum;
      makeScenes();
      return boardObj;
  }

  // constructor for DeadwoodWrapper (the views)
  public static Board getBoard (){
    return boardObj;
  }

  public static Room getRoom(String rName){
    return roomHashMap.get(rName);
  }

  public static HashMap<String, Role> getRoleMap(){
    return roleHashMap;
  }


  /*
  Reads a file describing adjacent rooms, and tells each Room object which..
  ... other Room objs are adjacent to it. Room objs store this in a private HashMap
  */
  private static void makeRoomAdjList(){

    File roomAdjFile = null;
    Scanner scan = null;

    try {
      roomAdjFile = new File ("model/infoFiles/adjRoomsInfo.txt");
      scan = new Scanner (roomAdjFile);
      while (scan.hasNextLine() != false){
        String roomName = scan.nextLine();

        if (scan.hasNextLine()){
          String[] adjRoomList = scan.nextLine().split("_");
          for (int j = 0; j < adjRoomList.length; j++){
            roomHashMap.get(roomName).addAdjRoom(roomHashMap.get(adjRoomList[j])); // add all adjacent rooms to this room's adjacency list
          }
        }
        else {
          System.err.println ("Improperly formatted Room Adjacently List file (adjRoomsInfo.txt).");
          System.exit(1);
        }
      }
    }
    catch (FileNotFoundException e){
      System.out.println ("adjRoomsInfo.txt file not found.");
      System.exit(1);
    }
    catch (NumberFormatException e){
      System.out.println ("adjRoomsInfo.txt file formatted incorrectly.");
      System.exit(1);
    }
  }


  /* setupRooms: initializes all rooms
  INPUT: a file describing the contents of each room (and off-card scenes)
  POST-CONDITION: 10 rooms w/scenes and off-card roles and 2 rooms w/o scenes...
    ...(trailer and casting office) are created and added to roomList.
    Calls makeRoomAdjList
  */
  public void setupRooms(){

    File room_file = null;
    Scanner scan = null;
    try {
      room_file = new File ("model/infoFiles/roomInfo.txt");
      scan = new Scanner (room_file);//.useDelimiter("_");
      // goes through file descibing content of 10 acting rooms (not trailer or casting office)
      while (scan.hasNextLine() != false){
        // create the room / parse the first line
        String[] line = scan.nextLine().split("_");
        Room myRoom = new Room (line[0], Integer.parseInt(line[1]));
        myRoom.placeScene(sceneDrawPile.remove());//we could recycle scenes in a later version
        scenesLeft++;
        // parse all roles in the room
        int roleNum = Integer.parseInt(line[2]);
        for (int i = 0; i < roleNum; i++){
          String[] roleLine = scan.nextLine().split("_");
          Role myRole;
          myRole = myRoom.addRole (Integer.parseInt(roleLine[0]), roleLine[1], roleLine[2]);
          roleHashMap.put(myRole.getRoleName(), myRole);
          //System.out.println("Role Name added to hashMap: " + roleHashMap.get(myRole.getRoleName()).getRoleName() );
        }
        //System.out.println("Man in Overalls: " + roleHashMap.get("Man in Overalls").getRoleName());
        roomHashMap.put(myRoom.getRName(), myRoom);
      }
      // setup casting office and trailer
      roomHashMap.put("Casting Office", new Room("Casting Office"));
      roomHashMap.put("Trailers", new Room("Trailers"));
    }
    catch (FileNotFoundException e){
      System.out.println ("roomInfo file not found.");
      System.exit(1);
    }
    catch (NumberFormatException e){
      System.out.println ("roomInfo file formatted incorrectly.");
      System.exit(1);
    }

    makeRoomAdjList();
  }


  //method to make scene cards to populate the sceneDrawPile LinkedList
  public static void makeScenes(){

	  File scene_file = null;
	  Scanner scan = null;
	  try{
      scene_file = new File ("model/infoFiles/sceneInfo.txt");
		  scan = new Scanner(scene_file);

		  Scene myScene = null;

		  int sNumber = 0;
		  int budget = 0;
		  int numRoles = 0;

		  while(scan.hasNextLine()){

			  //read from sceneInfo.txt
			  String[] line = scan.nextLine().split("_");
			  //make array of roles to go on scene
			  numRoles = Integer.parseInt(line[4]);
			  Role[] sRoleList = new Role[numRoles];

			  for(int i = 0; i < numRoles; i++){
				  String[] roleLine = scan.nextLine().split("_");

				  Role newRole = new Role ( roleLine[1], roleLine[2], Integer.parseInt(roleLine[0]));

				  sRoleList[i] = newRole;
			  }

			  //make scene
			  myScene = new Scene(line[0], line[3], Integer.parseInt(line[2]), Integer.parseInt(line[1]), sRoleList);
			  //add scene to sceneDrawPile
			  sceneDrawPile.add(myScene);
		  }


	  }
	  catch (FileNotFoundException e){
	      System.out.println ("sceneInfo file not found.");
	      System.exit(1);
	    }
	  catch (NumberFormatException e){
	      System.out.println ("roomInfo file formatted incorrectly.");
	      System.exit(1);
	    }

  }


  public static int getUpgradeReqs (String type, int level){
    if (type.equals("cr")){
      return upgradeReqs[level-2][1];
    }
    else {
      return upgradeReqs[level-2][0];
    }
  }



  public Room getTrailer(){
    return roomHashMap.get("Trailers");
  }


  /* endDay:
  POST-CONDITION: Players are in Trailers, all Rooms have new Scenes and full shot ctrs
  */
  public static void endDay(){
    for (Room room : roomHashMap.values()){
      if (! room.getRName().equals("Casting Office") && ! room.getRName().equals("Trailers")){
        room.resetShotCounter();
        room.placeScene(sceneDrawPile.remove());
      }
    }
    scenesLeft = 10; //Zak added this line, should fix game ending too early glitch
    daysLeft--;
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
