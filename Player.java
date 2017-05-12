/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Player class for Deadwood Assignment 2
*/

public class Player {

  private Room myRoom;

  private Role myRole;

  private Scene myScene;

  private int rank, moneyCnt, creditCnt, practiceCnt, playerID;


  // Player object constructor, takes the starting rank as a parameter
  public Player(int rank, int credits, Room trailer, int id){
    this.rank = rank;
    this.creditCnt = credits;
    this.moneyCnt = 0;
    this.practiceCnt = 0;
    this.playerID = id;
    this.myRoom = trailer;
  }

  // Handles the turn for the player
  public void takeTurn(){

  }

  // rank getter
  public int getRank(){
    return rank;
  }

  // moneyCnt getter
  public int getMoneyCnt(){
    return moneyCnt;
  }

  // creditCnt getter
  public int getCreditCnt(){
    return creditCnt;
  }

  // Attempts to upgrade the player's rank, returns false if unable to do so
  private boolean upgrade(String type, int level){
    // check if in Casting office
    if (this.myRoom.getRName().equals("Casting Office")){
      if (this.rank >= level){
        System.out.println ("Are you sure? Desired upgrade rank is lower than your current rank.");
        return false;
      }
      int req = Board.getUpgradeReqs(type, level);
      if (type.equals("$")){
        if (this.moneyCnt >= req){
          this.rank = level;
          this.moneyCnt -= req;
          return true;
        }
      }
      else if (type.equals("cr")){
        if (this.creditCnt >= req){
          this.rank = level;
          this.creditCnt -= req;
          return true;
        }
      }
    }
    return false;
  }


  // Rehearses for the player's current role, returns false if unable to do so
  private boolean rehearse(){
    if (this.myRole != null){
      this.practiceCnt ++;
      return true;
    }
    return false;
  }

  // Moves the player to a new room
  private boolean move(String rName){
    Room temp = myRoom.getAdjRoom(rName);
    if (temp == null){
      System.out.println("Indicated room is not adjacent.");
      return false;
    }
    this.myRoom = temp;
    return true;
  }
  

  // Works an off card role
  private void act_OffCard(){

  }

  // Works an on card role
  private void act_OnCard(){
    // role dice
    // if role > myRole.get

  }

  public void leaveRole(){
	  this.myRole = null;
  }

  public void awardOffCardBonus(){
	  this.moneyCnt += this.myRole.getRank();
  }

  public void payActor(int pay){
	  this.moneyCnt += pay;
  }


}
