/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Player class for Deadwood Assignment 2
*/
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System.*;
import java.util.Scanner;
import java.util.Collection;
import java.util.LinkedList;


public class Player {

  private Room myRoom;

  private Role myRole;

  private Scene myScene;

  private int rank, moneyCnt, creditCnt, practiceCnt, playerID;
  private int mode = 0;
  private boolean actionTaken = false;
  private boolean roleOnCard = false;
  private boolean turnDone = false;

  public interface Listener {
    void changed(Player r);
  }

  private Collection<Listener> listeners;

  public void subscribe (Listener l){
    listeners.add(l);
  }

  protected void changed(){
    for (Listener l : listeners){
      l.changed(this);
    }
  }


  // Player object constructor, takes the starting rank as a parameter
  public Player(int rank, int credits, Room trailer, int id){
    listeners = new LinkedList<Listener>();
    this.rank = rank;
    this.creditCnt = credits;
    this.moneyCnt = 0;
    this.practiceCnt = 0;
    this.playerID = id;
    this.myRoom = trailer;
    changed();
  }





  // Handles the turn for the player
  public void takeTurn(ArrayList <String> command){

    // try to process command, checking if input arg option # and types are valid
    String cmd0 = command.remove(0);

    if (cmd0.equals("who")){
      System.out.print ("Player " + this.playerID + " ($" + this.moneyCnt + ", " + this.creditCnt + "cr), Rank: " + this.rank);
      if (this.myRole != null){
        System.out.println(" working " + this.myRole.getRoleWho()); //Zak added a space before "working"
      }
      else {System.out.println();}
    }

    else if (cmd0.equals("where")){ // prints player, player's current room, scene name and number if on a role. if not, lists open roles
      whereAmI();
    }

    else if (cmd0.equals("upgrade")){ // already checked in validateUserCommand that the user is in the casting office
      if (command.size() == 2){
        try {
          String paymentType = command.remove(0);
          int level = Integer.parseInt(command.remove(0));
          if (level >=2 && level <= 6){
            int reqCurrency = Board.getUpgradeReqs (paymentType, level);
            doUpgrade (paymentType, level, reqCurrency);
          }
          else {
            System.out.println ("Invalid rank. Please choose from 2 to 6");
          }
        } catch (NumberFormatException e){
          System.out.println ("Desired rank cannot be parsed. Please enter a number between 2 and 6");
        }
      }
      else{
        System.out.println ("Wrong number of arguments for 'upgrade'.");
      }
    }

    else if (cmd0.equals("end")){
      this.turnDone = true;
    }

    else { // for commands which count as "one action per turn":

      if (this.actionTaken == true){
        System.out.println ("You have already used your action for the turn; you may ask 'where', 'who', or 'end' to end your turn.");
      }

      else { // if the player does have an action to take

        if (cmd0.equals("rehearse")){
          // add snarky comment about not needing to rehearse w/ so many practiceCnt s later
          this.practiceCnt++;
          System.out.println ("Total practice chips increased to " + this.practiceCnt);
          this.actionTaken = true;
        }

        else if (cmd0.equals("work")){
          String usrPart = String.join(" ", command.toArray(new CharSequence[command.size()]));

          boolean result = takeRole (usrPart);
          if(result){
            System.out.println ("You took the role: " + usrPart);
          }
          this.actionTaken = result;
        }

        else if (cmd0.equals("move")){
          String newRoom = String.join(" ", command.toArray(new CharSequence[command.size()]));
          if (this.myRoom.getAdjRoom(newRoom) != null){
            System.out.println ("You moved to: " + newRoom);
            this.myRoom = this.myRoom.getAdjRoom(newRoom);
            if (this.myRoom.getScene() != null){
              if (this.myRoom.getScene().isCardFlipped() == false){
                this.myRoom.getScene().flipSceneCard();
              }
            }
            this.actionTaken = true;
          }
          else {
            System.out.println ("Room is not adjacent or does not exist. Please try again.");
          }
          //System.out.println ("Player " + this.playerID + "is in room " + this.myRoom.getRName());
          //System.out.println ("Player " + this.playerID + " has taken action? " + actionTaken);
        }

        else if (cmd0.equals("act")){ // already checked in validateUserCommand that the user has a role
          act();
          this.actionTaken = true;
        }
      }
    }
    changed();
    view.DeadwoodWrapper.updatePlayerStats();
  }


  /*
  moveToTrailer is called by Board's endDay method
  resets all player attributes except $, cr, rank, and ID
  */
  public void moveToTrailer(Room trailers){
    this.myRoom = trailers;
    if (this.myRole != null){
      this.myRole.actorLeaves();
    }
    this.myRole = null;
    this.myScene = null;
    this.practiceCnt = 0;
    this.actionTaken = false;
    this.turnDone = false;
    this.roleOnCard = false;
    this.mode = 0;
    changed();
  }


  // Works an off card role
  private void act(){
    int theRoll = Dice.rollOneD();
    System.out.println ("You rolled a " + theRoll + " and you have " + this.practiceCnt + " practice chips.");
    System.out.println ("Total: " + (theRoll + this.practiceCnt));
    // compare dice roll to movie budget
    if (this.myScene.checkBudget (theRoll + this.practiceCnt)){
      // pay regular salary
      if (roleOnCard){
        System.out.println ("You succeeded! Take two credits.");
        this.creditCnt += 2;
      }
      else {
        System.out.println ("You succeeded! Take 1 dollar and 1 credit.");
        this.moneyCnt ++;
        this.creditCnt ++;
      }

      int shotsLeft = this.myRoom.rmShotCounter();
      if (shotsLeft == 0){
        if (this.myRoom.hasOncardPlayer()){
          // wrap scene w/ bonus roll, dice # = to budget
          int[] bRoll = Dice.bonusRoll (this.myScene.getBudget());
          this.myRoom.wrapScene(bRoll);
        }
        else{
          this.myRoom.wrapScene();
        }
        // remove player from role, remove role from player
        this.roleOnCard = false;
        this.practiceCnt = 0;
        this.myRole = null;
        this.myScene = null;
      }
    }
    else if (roleOnCard == false){ // if acting failed and player was off-card
      this.moneyCnt ++;
      System.out.println ("You failed. But still got $1!");
    }
    else {
      System.out.println("You failed, better luck next time.");
    }
  }


  private void doUpgrade (String paymentType, int level, int reqCurrency){

    if (paymentType.equals("$")){
      if (this.moneyCnt >= reqCurrency){
        this.moneyCnt -= reqCurrency;
        this.rank = level;
        System.out.println ("Upgraded to rank " + this.rank + "!");
      }
      else{
        System.out.println ("You're too poor!!! " + paymentType + reqCurrency + " required for upgrade.");
      }
    }
    if (paymentType.equals("cr")){
      if (this.creditCnt >= reqCurrency){
        this.creditCnt -= reqCurrency;
        this.rank = level;
        System.out.println ("Upgraded to rank " + this.rank + "!");
      }
      else{
        System.out.println ("You're not popular enough!!! " + paymentType + reqCurrency + " required for upgrade.");
      }
    }
  }


  // takeRole sets the player's role to usrPart if possible and updates roleOnCard if neccessary. returns false otherwise
  private boolean takeRole(String usrPart){
    if (this.myRoom.hasScene() == false){
      System.out.println ("There is no active scene in this room.");
      return false;
    }
    else {
      Role tempRole = this.myRoom.findOnCardRole(usrPart);
      if (tempRole == null){
        tempRole = this.myRoom.findOffCardRole(usrPart);
      }
      else { // if players new role does exist on the scene card
        roleOnCard = true;
      }
      if (tempRole == null){
        System.out.println ("Please try again.");
        return false;
      }
      else if (this.rank >= tempRole.getRank()){ // if the player has sufficient rank
        this.myRole = tempRole;
        this.myRole.addActor(this);
        this.myScene = myRoom.getScene();
        return true;
      }
      else {
        System.out.println ("This part is out of your league!");
        return false;
      }
    }
  }


  private void whereAmI (){

    System.out.print ("in " + this.myRoom.getRName());
    if (this.myRoom.hasScene()){
      System.out.println (" shooting " + this.myRoom.getSName() + " scene #" + this.myRoom.getSNumber() + ", Budget: $" + this.myRoom.getScene().getBudget()); // "shooting [scene name] scene [scene #]"
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


  public int calculateScore (){
    int score = this.moneyCnt + this.creditCnt;
    score += this.rank * 5;
    System.out.println ("Player " + this.playerID + " received a score of " + score + ".");
    return score;
  }


  public boolean hasTakenAction(){
    return this.actionTaken;
  }

  public boolean isTurnDone(){
    return this.turnDone;
  }

  public void endTurn(){
    this.turnDone = false;
  }

  public int getMode(){
    return this.mode;
  }

  public void setMode(int newMode){
    this.mode = newMode;
  }

  public Role getRole (){
    return myRole;
  }

  public Room getRoom (){
    return myRoom;
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

  public int getPracticeCnt(){
    return practiceCnt;
  }

  public void leaveRole(){
    this.myRole = null;
    changed();
  }

  public void awardOffCardBonus(){
    this.moneyCnt += this.myRole.getRank();
    changed();
  }

  public void payActor(int pay){
    this.moneyCnt += pay;
    changed();
  }

  public int getPlayerID(){
    return this.playerID;
  }

  public void setActionTaken(boolean set){
    this.actionTaken = set;
  }



}
