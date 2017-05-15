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

  private boolean roleOnCard = false;

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
    boolean noActionsTaken = true;
    //ArrayList <String> command = new ArrayList <String> ();
    ArrayList <String> command = null;
    boolean turnDone = false;

    while (turnDone != true){
      // Print out correct prompt and assign mode
      System.out.println("\n");
      int mode = promptUser();

      command = validateUserCommand(mode, command);
      System.out.println ("1size of ArrayList command: " + command.size());
      // try to process command, checking if input arg option # and types are valid
      String cmd0 = command.remove(0);

      if (cmd0.equals("who")){
        System.out.print ("Player " + this.playerID + " ($" + this.moneyCnt + ", " + this.creditCnt + "cr) ");
        if (this.myRole != null){
          System.out.println("working " + this.myRole.getRoleWho());
        }
        else {System.out.println();}
      }

      else if (cmd0.equals("where")){ // prints player, player's current room, scene name and number if on a role. if not, lists open roles
        whereAmI();
      }

      //(noActionsTaken == false){}

      else if (cmd0.equals("rehearse")){
        // add snarky comment about not needing to rehearse w/ so many practiceCnt s later
        this.practiceCnt++;
        noActionsTaken = false;
      }

      else if (cmd0.equals("work")){
        if (noActionsTaken){
          String usrPart = String.join(" ", command.toArray(new CharSequence[command.size()]));
          System.out.println (usrPart);
          takeRole (usrPart);
          noActionsTaken = false;
        }
        else {
          System.out.println ("You can only move once per turn.");
        }
      }

      else if (cmd0.equals("move")){
        if (noActionsTaken){
          String newRoom = String.join(" ", command.toArray(new CharSequence[command.size()]));
          //CharSequence[] cs = command.toArray(new CharSequence[command.size()]);
          //String newRoom = Arrays.toString(command.toArray(new CharSequence[command.size()]));
          System.out.println ("Room to move to: " + newRoom);
          if (this.myRoom.getAdjRoom(newRoom) != null){
            this.myRoom = this.myRoom.getAdjRoom(newRoom);
          }
          else {
            System.out.println ("Room is not adjacent or does not exist. Please try again.");
          }
          noActionsTaken = false;
        }
        else {
          System.out.println ("You can only move once per turn.");
        }
      }

      else if (cmd0.equals("upgrade")){

      }

      else if (cmd0.equals("act")){

      }

      else if (cmd0.equals("end")){
        turnDone = true;
      }

    }
  }


  // promptUser asks the user what actions they would like to take, given their location and status
  // RETURNS: an int indicating their status/location
  private int promptUser(){
    int mode = 0;
    //System.out.println ("MADE IT TO promptUser");

    if(this.myRole != null){
      mode = 1;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, act, rehearse, or end.");
    }

    else if(this.myRoom.getRName().equals("Casting Office")){
      mode = 2;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, move, upgrade, or end.");
    }

    else if(this.myRoom.getRName().equals("Trailers")){
      mode = 3;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, move, or end.");
    }

    else if(this.myRole == null){ // player in room, but without role
      mode = 4;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, work, or end.");
    }
    return mode;
  }


  /* validateUserCommand takes user input and ensures that it is valid given their location/status,
  * asking them to input a different command if it is invalid.
  */
  private ArrayList<String> validateUserCommand(int mode, ArrayList<String> command){
    // Take command from user
    Scanner scan = new Scanner (System.in);
    command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));

    // check if valid command, given player's position / status
    switch (mode){
      case 1: // if player has a role already
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("act") && !command.get(0).equals("rehearse") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are working a role. Please enter a command from the list above.");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
        break;
      case 2: // if player is in casting office
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("upgrade") && !command.get(0).equals("move") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are in the Casting Office. Please enter a command from the list above.");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
        break;
      case 3: // if player is in Trailers
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("move") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are in the Trailers. Please enter a command from the list above.");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
        break;
      default: // player is not in Trailers or Casting Office, nor working a role (e.g. is in a room's whitespace w/o part)
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("move") && !command.get(0).equals("end") && !command.get(0).equals("work")){
          System.out.println("You can not do that while you are in the Trailers. Please enter a command from the list above.");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
    }
    return command;
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


  private void takeRole(String usrPart){
    if (this.myScene == null){
      System.out.println ("There is no active scene in this room.");
    }
    else {
      Role tempRole = this.myRoom.findRole(usrPart);
      if (tempRole == null){
        System.out.println ("Please try again.");
      }
      else {
        this.myRole = tempRole;
      }
    }
  }


  private void whereAmI (){

    System.out.print ("in " + this.myRoom.getRName());
    if (this.myRoom.hasScene()){
      System.out.println (" shooting " + this.myRoom.getSName() + " scene #" + this.myRoom.getSNumber()); // "shooting [scene name] scene [scene #]"
      this.myRoom.displayAllRoles(); // lists available acting roles off and on card
    }
    else {
      if (this.myRoom.getRName().equals("Trailers") || this.myRoom.getRName().equals("Casting Office")){
        System.out.println(".");
      }
      else {
        System.out.println(" wrapped.");
      }
    }
    System.out.print ("Adjacent rooms: ");
    this.myRoom.displayAdjRooms();
  }


  public int getPlayerID(){
    return this.playerID;
  }


}
