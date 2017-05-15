/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Room class for Deadwood Assignment 2
*/

import java.util.ArrayList;
import java.util.Scanner;

public class Room{

  private String rName;
  private Scene rScene;
  private int maxShotCtr, shotCtr;
  private ArrayList<Room> adjRoomList = new ArrayList<Room>();
  private ArrayList<Role> rRoleList = new ArrayList<Role>(); // ArrayList of off-card roles in the room


  //Room object constructor, takes name of room as parameter to make a new room
  public Room(String roomName, int shotCtr){
    this.maxShotCtr = shotCtr;
    this.shotCtr = shotCtr;
    this.rName = roomName;
  }

  // Room constructor for rooms without shotCtr (Trailer and Casting Office)
  public Room(String roomName){
    this.rName = roomName;
  }


  // Adds an off-card role to the rRoleList
  public void addRole(int rank, String name, String quote){

    // create new role obj
    // put role in role arrayList
    rRoleList.add(new Role(name, quote, rank));

  }

  public void addAdjRoom (Room newAdjRoom){
    this.adjRoomList.add(newAdjRoom);
  }



  // Decrements number of shot counters in room after successful acting action
  // Returns number of shots left
  public int rmShotCounter(){
    this.shotCtr--;
    return this.shotCtr;
  }


  // Resets the number of shot counters after a new scene is placed(called from placeScene())
  public void resetShotCounter(){
    this.shotCtr = this.maxShotCtr;
  }


  //places a new scene card in the room
  public void placeScene(Scene faceDownScene){
    this.rScene = faceDownScene;
  }


  // return true if there is an on-card player
  public boolean hasOncardPlayer(){
    // looks for on-card actors in scene
    for (int j = 0; j < this.rScene.getSRoleListSize(); j++){
      if (this.rScene.getSRoleList()[j].getActor() != null){
        return true;
      }
    }
    return false;
  }


  // Discards the current scene card and awards appropriate bonuses to players in the room
  // bonusDice will be 0 if no bonuses are to be awarded, or an array of ints if not
  public void wrapScene(int [] bonusDice){

	  for (int i = 0; i < bonusDice.length; i++){ //awards on card actors their bonuses
		  if (rScene.getSRoleList()[i % rScene.getSRoleList().length].getActor() != null){ //if there is a player in this role, award it a bonus equal to the correct assignment of bonus dice
			  rScene.getSRoleList()[i % rScene.getSRoleList().length].getActor().payActor(bonusDice[i]);
		  }
	  }
    /*
    for (int i = bonusDice.length - 1; i >= 0; i++){ //awards on card actors their bonuses
      if (rScene.getSRoleList()[i % rScene.getSRoleList().length].getActor() != null){ //if there is a player in this role, award it a bonus equal to the correct assignment of bonus dice
        rScene.getSRoleList()[i % rScene.getSRoleList().length].getActor().payActor(bonusDice[i]);
      }
    }*/

	  for (int i = 0; i< rScene.getSRoleListSize(); i ++){ //removes on card actors from their roles
		  if (rScene.getSRoleList()[i] != null){
			  rScene.getSRoleList()[i].getActor().leaveRole();
			  rScene.getSRoleList()[i] = null;
		  }
	  }

	  for(int i  =0; i < rRoleList.size(); i++){ //awards off card actors their bonuses and removes them from their roles
		  if(rRoleList.get(i).getActor() != null){
			  rRoleList.get(i).getActor().awardOffCardBonus();
			  rRoleList.get(i).getActor().leaveRole();
			  rRoleList.get(i).actorLeaves();
		  }
	  }
    // discard scene card
    this.rScene = null;
    // decrement scene num
    Board.decSceneNum();
  }


  public void wrapScene(){

	  for(int i  =0; i < rRoleList.size(); i++){ //removes off card actors from their roles
		  if(rRoleList.get(i).getActor() != null){
			  rRoleList.get(i).getActor().leaveRole();
			  rRoleList.get(i).actorLeaves();
		  }
	  }
	  this.rScene = null; //discards scene card
	  Board.decSceneNum(); //decrements number of scenes left on the board
  }


  /* getAdjRoom
  * Parameter: String rName, the name of a room
  * Returns: the Room object with name rName, if that Room is adjacent, or null if not
  */
  public Room getAdjRoom (String rName){
    for (int k = 0; k < adjRoomList.size(); k++){
      if (adjRoomList.get(k).getRName().equals(rName)){
        return adjRoomList.get(k);
      }
    }
    return null;
  }


  // displayAdjRooms prints out a list of adjacent rooms
  public void displayAdjRooms(){
    for (int k = 0; k < adjRoomList.size()-1; k++){
      System.out.print (adjRoomList.get(k).getRName() + ", ");
    }
    System.out.println (adjRoomList.get(adjRoomList.size()-1).getRName());
  }


public Role findOnCardRole (String roleName){
  // Look for on-card roles
  if (this.rScene != null){
    for (int i = 0; i < rScene.getSRoleListSize(); i++){
      if (this.rScene.getSRoleList()[i].getRoleName().equals(rName)){
        if (this.rScene.getSRoleList()[i].getActor() != null){ // if the role is available
          System.out.print ("Sorry, this role is already taken.");
          return null;
        }
        else {
          return this.rScene.getSRoleList()[i];
        }
      }
    }
  }
  System.out.print ("No role found with that name.");
  return null;
}


public Role findOffCardRole (String roleName){
  // find off-card roles
  for (int k = 0; k < rRoleList.size(); k++){
    if (rRoleList.get(k).getRoleName().equals(rName)){
      if (rRoleList.get(k).getActor() != null){
        System.out.print ("Sorry, this role is already taken.");
        return null;
      }
      else{
        return rRoleList.get(k);
      }
    }
  }
  System.out.print ("No role found with that name.");
  return null;
}

  // findRole: returns the Role object with the same indicated roleName, or null otherwise
  /*public Role findRole (String roleName){
    // find off-card roles
    for (int k = 0; k < rRoleList.size(); k++){
      if (rRoleList.get(k).getRoleName().equals(rName)){
        if (rRoleList.get(k).getActor() != null){
          System.out.print ("Sorry, this role is already taken.");
          return null;
        }
        else{
          return rRoleList.get(k);
        }
      }
    }
    // Look for on-card roles
    if (this.rScene != null){
      for (int i = 0; i < rScene.getSRoleListSize(); i++){
        if (this.rScene.getSRoleList()[i].getRoleName().equals(rName)){
          if (this.rScene.getSRoleList()[i].getActor() != null){ // if the role is available
            System.out.print ("Sorry, this role is already taken.");
            return null;
          }
          else {
            return this.rScene.getSRoleList()[i];
          }
        }
      }
    }
    System.out.print ("No role found with that name.");
    return null;
  }*/


  // getRName returns the room's name
  public String getRName(){
    return rName;
  }


  public Scene getScene(){
    return this.rScene;
  }

  // hasScene returns true it this room has an affiliated scene, or false otherwise
  public boolean hasScene(){
    if (this.rScene == null){
      return false;
    }
    return true;
  }


  // getSName returns the name of the Room's scene
  public String getSName(){
    return this.rScene.getSName();
  }


  // getSNumber returns the # of the Room's scene
  public int getSNumber(){
    return this.rScene.getSNumber();
  }

  // print all oncard and off card roles
  // returns false if the room has no scene; true if otherwise
  public boolean displayAllRoles(){

    // print on card roles
    if (this.rScene != null){
      System.out.println("\nAvailable on-card roles:");
      for (int i = 0; i < rScene.getSRoleListSize(); i++){
        if (this.rScene.getSRoleList()[i].getActor() == null){ // if the role is available
          System.out.println("Rank " + this.rScene.getSRoleList()[i].getRank() + " " + this.rScene.getSRoleList()[i].getRoleWho());
        }
      }
    }
    else {
      return false;
    }
    // print off-card roles
    System.out.println("\nAvailable off-card roles:");
    for (int i = 0; i < rRoleList.size(); i++){
      if (rRoleList.get(i).getActor() == null){
        System.out.println("Rank " + rRoleList.get(i).getRank() + ": " + rRoleList.get(i).getRoleWho());
      }
    }
    return true;
  }


}
