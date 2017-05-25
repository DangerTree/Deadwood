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
    boolean actionTaken = false;
    //ArrayList <String> command = new ArrayList <String> ();
    ArrayList <String> command = null;
    boolean turnDone = false;

    while (turnDone != true){
      // Print out correct prompt and assign mode
      System.out.println("\n");
      int mode = promptUser(actionTaken);

      command = validateUserCommand(mode, command);
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
          String paymentType = command.remove(0);
          int level = Integer.parseInt(command.remove(0));
          int reqCurrency = Board.getUpgradeReqs (paymentType, level);
          doUpgrade (paymentType, level, reqCurrency);
        }
        else{
          System.out.println ("Wrong number of arguments for 'upgrade'.");
        }
      }

      else if (cmd0.equals("end")){
        turnDone = true;
      }

      else { // for commands which count as "one action per turn":

        if (actionTaken == true){
          System.out.println ("You have already used your action for the turn; you may ask 'where', 'who', or 'end' to end your turn.");
        }

        else { // if the player does have an action to take

          if (cmd0.equals("rehearse")){
            // add snarky comment about not needing to rehearse w/ so many practiceCnt s later
            this.practiceCnt++;
            System.out.println ("Total practice chips increased to " + this.practiceCnt);
            actionTaken = true;
          }

          else if (cmd0.equals("work")){
            String usrPart = String.join(" ", command.toArray(new CharSequence[command.size()]));
            boolean result = takeRole (usrPart);
            if(result){
              System.out.println ("You took the role: " + usrPart);
            }
            actionTaken = result;
          }

          else if (cmd0.equals("move")){
            String newRoom = String.join(" ", command.toArray(new CharSequence[command.size()]));
            System.out.println ("Room to move to: " + newRoom);
            if (this.myRoom.getAdjRoom(newRoom) != null){
              this.myRoom = this.myRoom.getAdjRoom(newRoom);
              if (this.myRoom.getScene() != null){
                if (this.myRoom.getScene().isCardFlipped() == false){
                  this.myRoom.getScene().flipSceneCard();
                }
              }
              actionTaken = true;
            }
            else {
              System.out.println ("Room is not adjacent or does not exist. Please try again.");
            }
          }

          else if (cmd0.equals("act")){ // already checked in validateUserCommand that the user has a role
            act();
            actionTaken = true;
          }
        }
      }
    }
  }


  // promptUser asks the user what actions they would like to take, given their location and status
  // RETURNS: an int indicating their status/location
  private int promptUser(boolean actionTaken){
    int mode = 0;
    //System.out.println ("MADE IT TO promptUser");

    // if the player has already taken an action on their move, they can only enter who where or end (but not if they are in the casting office)
    if (actionTaken == true && !this.myRoom.getRName().equals("Casting Office")){
      mode = 1;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, or end.");
    }

    else if(this.myRole != null){
      mode = 2;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, act, rehearse, or end.");
    }

    else if(this.myRoom.getRName().equals("Casting Office")){
      mode = 3;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, move, upgrade, or end.");
    }

    else if(this.myRoom.getRName().equals("Trailers")){
      mode = 4;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, move, or end.");
    }

    else if(this.myRole == null){ // player in room, but without role
      mode = 5;
      System.out.println("Player " + this.playerID + ", what would you like to do?\n\tOPTIONS: who, where, work, or end.");
    }
    return mode;
  }


  /* validateUserCommand takes user input and ensures that it is valid given their location/status,
  * asking them to input a different command if it is invalid.
  */
  private ArrayList<String> validateUserCommand(int mode, ArrayList<String> command){
    // Take command from user
    System.out.print("> ");
    Scanner scan = new Scanner (System.in);
    command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));

    // check if valid command, given player's position / status
    switch (mode){
      case 1: // if player has already done an action this turn (and is not in the casting office)
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are working a role. Please enter a command from the list above.");
          System.out.print("> ");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
      break;
      case 2: // if player has a role already
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("act") && !command.get(0).equals("rehearse") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are working a role. Please enter a command from the list above.");
          System.out.print("> ");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
        break;
      case 3: // if player is in casting office
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("upgrade") && !command.get(0).equals("move") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are in the Casting Office. Please enter a command from the list above.");
          System.out.print("> ");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
        break;
      case 4: // if player is in Trailers
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("move") && !command.get(0).equals("end")){
          System.out.println("You can not do that while you are in the Trailers. Please enter a command from the list above.");
          System.out.print("> ");
          command = new ArrayList <String> (Arrays.asList(scan.nextLine().split(" ")));
        }
        break;
      default: // player is not in Trailers or Casting Office, nor working a role (e.g. is in a room's whitespace w/o part)
        while(!command.get(0).equals("who") && !command.get(0).equals("where") && !command.get(0).equals("move") && !command.get(0).equals("end") && !command.get(0).equals("work")){
          System.out.println("You can not do that while you do not have a role. Please enter a command from the list above.");
          System.out.print("> ");
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


  public void leaveRole(){
	  this.myRole = null;
  }

  public void awardOffCardBonus(){
	  this.moneyCnt += this.myRole.getRank();
  }

  public void payActor(int pay){
	  this.moneyCnt += pay;
  }


  private void doUpgrade (String paymentType, int level, int reqCurrency){

    if (paymentType.equals("$")){
      if (this.moneyCnt >= reqCurrency){
        this.moneyCnt -= reqCurrency;
        this.rank = level;
      }
      else{
        System.out.println ("You're too poor!!! " + paymentType + reqCurrency + " required for upgrade.");
      }
    }
    if (paymentType.equals("cr")){
      if (this.creditCnt >= reqCurrency){
        this.creditCnt -= reqCurrency;
        this.rank = level;
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
      //Role tempRole = this.myRoom.findRole(usrPart);
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
      else {
        this.myRole = tempRole;
        this.myRole.addActor(this);
        this.myScene = myRoom.getScene();
        return true;
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


  public int getPlayerID(){
    return this.playerID;
  }


  public int calculateScore (){
    int score = this.moneyCnt + this.creditCnt;
    score += this.rank * 5;
    System.out.println ("Player " + this.playerID + " received a score of " + score + ".");
    return score;
  }


}
