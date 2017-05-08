/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Room Class for Deadwood Assignment 2
*   Version 1.1, Skeleton methods
*/
import java.util.ArrayList;
import java.util.Scanner;

public class Room{

  private String rName;

  private Scene rScene;

  private int maxShotCtr, shotCtr;

  private Room[] adjRoomList;

  private ArrayList<Role> rRoleList = new ArrayList<Role>();


  //Room object constructor, takes name of room as parameter to make a new room
  public Room(String roomName, int shotCtr){
    this.shotCtr = shotCtr;
    this.rName = roomName;
  }

  // Room constructor for rooms without shotCtr (Trailer and Casting Office)
  public Room(String roomName){
    this.rName = roomName;
  }

  //Adds an off-card role to the rRoleList
  public void addRole(String roleInfo){

    // create new role obj
    Scanner s = new Scanner(roleInfo).useDelimiter("_");
    int roleRank = Integer.parseInt(s.next());
    String roleName = s.next();
    String roleQuote = s.next();
    // put role in role arrayList
    rRoleList.add(new Role(roleName, roleQuote, roleRank));

  }

  //decrements number of shot counters in room after successful acting action
  public void rmShotCounter(){

  }

  //resets the number of shot counters after a new scene is placed(called from placeScene())
  public void resetShotCounter(){

  }

  //places a new scene card in the room
  public void placeScene(){

  }

  //discards the current scene card and awards appropriate bonuses to players in the room
  public void wrapScene(){

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
