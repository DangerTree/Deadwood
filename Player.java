/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Player class for Deadwood Assignment 2
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System.*;
import java.util.Scanner;


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
    boolean canMove = true;
    ArrayList <String> command = null;
    command.add("not end");

    while (!command.get(0).equals("end")){
      // Print out correct prompt and assign mode
      int mode = 0;

      if(this.myRole != null){
        mode = 1;
        System.out.println("Player " + this.playerID + ", what would you like to do?\n who, where, act, rehearse, or end.");
      }

      else if(this.myRoom.getRName().equals("Casting Office")){
        mode = 2;
        System.out.println("Player " + this.playerID + ", what would you like to do?\n who, where, move, upgrade, or end.");
      }

      else if(this.myRoom.getRName().equals("Trailers")){
        mode = 3;
        System.out.println("Player " + this.playerID + ", what would you like to do?\n who, where, move, or end.");
      }

      // Take command from user
      Scanner scan = new Scanner (System.in);
      command = new ArrayList <String> (Arrays.asList(scan.next().split(" ")));

      // check if valid command, given player's position / status
      switch (mode){
        case 1: // if player has a role already
          while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("act") && !command.get(0).equals("rehearse") && !command.get(0).equals("end")){
            System.out.println("You can not do that while you are working a role. Please enter a command from the list above.");
            command = new ArrayList <String> (Arrays.asList(scan.next().split(" ")));
          }
          break;
        case 2: // if player is in casting office
          while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("upgrade") && !command.get(0).equals("move") && !command.get(0).equals("end")){
            System.out.println("You can not do that while you are in the Casting Office. Please enter a command from the list above.");
            command = new ArrayList <String> (Arrays.asList(scan.next().split(" ")));
          }
          break;
        case 3: // if player is in Trailers
          while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("move") && !command.get(0).equals("end")){
            System.out.println("You can not do that while you are in the Trailers. Please enter a command from the list above.");
            command = new ArrayList <String> (Arrays.asList(scan.next().split(" ")));
          }
          break;
        default: // player is not in Trailers or Casting Office, nor working a role (e.g. is in a room's whitespace w/o part)
          while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("move") && !command.get(0).equals("end") && !command.get(0).equals("work")){
            System.out.println("You can not do that while you are in the Trailers. Please enter a command from the list above.");
            command = new ArrayList <String> (Arrays.asList(scan.next().split(" ")));
          }
      }

      // try to process command, checking if input arg option # and types are valid
      String cmd0 = command.get(0);
      if (cmd0.equals("who")){
        System.out.print ("Player " + this.playerID + " ($" + this.moneyCnt + ", " + this.creditCnt + "cr) ");
        if (this.myRole != null){
          System.out.println("working " + this.myRole.getRoleWho());
        }
        else {System.out.println();}
      }
      else if (cmd0.equals("where")){
        System.out.print ("in " + this.myRoom.getRName());
        if (this.myRoom.hasScene()){
          System.out.println (" shooting " + this.myRoom.getSName() + " scene " + this.myRoom.getSNumber()); // "shooting [scene name] scene [scene #]"
        }
        else {
          System.out.println(" wrapped.");
        }
      }
      /*else if (cmd0.equals ("rehearse")){
        if (this.practiceCnt >= this.my)
        this.practiceCnt++;
      }*/


    }
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
    //int attempt = Dice.rollOneD();

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

  public void takeRole(Role newRole){ //checking for valid role may happen in this method or in takeTurn method
    //this.myRole = newRole;
    //this.myScene = myRoom.getScene();
  }

  public int getPlayerID(){
    return this.playerID;
  }


}
