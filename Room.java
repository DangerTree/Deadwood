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
  private Room[] adjRoomList;
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
    int roleRank = rank;
    String roleName = name;
    String roleQuote = quote;
    // put role in role arrayList
    rRoleList.add(new Role(roleName, roleQuote, roleRank));

  }


  // Decrements number of shot counters in room after successful acting action
  // Returns number of shots left
  public int rmShotCounter(){
    this.shotCtr--;
    return this.shotCtr;
    //if(this.shotCtr == 0){
    //  wrapScene();
    //}
  }


  // Resets the number of shot counters after a new scene is placed(called from placeScene())
  public void resetShotCounter(){
    this.shotCtr = this.maxShotCtr;
  }


  //places a new scene card in the room
  public void placeScene(){
    //this.rScene = new Scene ();

  }


  // return true if there is an on-card player
  public boolean isOncardPlayer(){
    // looks for on-card actors in scene
    for (int j = 0; j < this.rScene.getSRoleListSize(); j++){
      if (this.rScene.getSRoleList()[j].getActor() != null){
        return true;
      }
    }
    return false;
  }

  private void awardBonus (int[] bonusDice){
    // AWARD BONUSES HERE
    // goes through off-card roles to find actors
    //for (int i = 0; i < rRoleList.size(); i++){
    //}
  }

  // Discards the current scene card and awards appropriate bonuses to players in the room
  // bonusDice will be 0 if no bonuses are to be awarded, or an array of ints if not
  public void wrapScene(int [] bonusDice){

    if (bonusDice[0] != 0){
      awardBonus(bonusDice);
    }
    // discard scene card (AND REMOVE PLAYERS FROM IT....)
    this.rScene = null;
    // decrement scene num
    Board.decSceneNum();
  }

  //returns the list of rooms that are accessible from this room
  public Room[] getAdjRoom(){
    return adjRoomList;
  }

  //returns the room's name
  public String getRName(){
    return rName;
  }

}
