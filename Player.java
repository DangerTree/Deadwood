/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Class to create and manage Player objects for the Deadwood game
*   Version 1.0, Skeleton methods only
*/

public class Player {

  private Room myRoom;

  private Role myRole;

  private Scene myScene;

  private int rank, moneyCnt, creditCnt, practiceCnt;


  //Player object constructor, takes the starting rank as a parameter
  public Player(int rank){

  }

  //handles the turn for the player
  public void takeTurn(){

  }

  //rank value getter
  public int getRank(){
    return rank;
  }

  //moneyCnt getter
  public int getMoneyCnt(){
    return moneyCnt;
  }

  //creditCnt getter
  public int getCreditCnt(){
    return creditCnt;
  }

  //attempts to upgrade the player's rank, returns false if unable to do so
  private boolean upgrade(){
    return true;
  }

  //rehearses for the player's current role, returns false if unable to do so
  private boolean rehearse(){
    return true;
  }

  //moves the player to a new room
  private void move(String rName){

  }

  //works an off card role
  private void act_OffCard(){

  }

  //works an on card role
  private void act_OnCard(){

  }


}
