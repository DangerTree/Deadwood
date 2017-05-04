/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Room Class for Deadwood Assignment 2
*   Version 1.0, Skeleton methods
*/

public Class Room{

  private String rName;

  private Scene rScene;

  private int maxShotCtr, shotCtr;

  private Room[] adjRoomList;

  private Role[] rRoleList;


  //Room object constructor, takes name of room as parameter to make a new room
  public Room(String rName){

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
