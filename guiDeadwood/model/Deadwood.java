/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Deadwood class for Deadwood Assignment 2
*
*   Driver class for the Deadwood game
*/
package model;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.InputMismatchException;

public class Deadwood{

  //private static int numPlayer;
  private static Queue<Player> playerQueue = new LinkedList<Player>();
  private static Player activePlayer = null;

  public static void initGameboard (int numPlayer){

    Board gameBoard = null;
    if (numPlayer == 2 || numPlayer == 3){
      gameBoard = Board.getBoard(3);
    }
    else{
      gameBoard = Board.getBoard(4);
    }

    gameBoard.setupRooms();

    // Create player obects
    int startingCred = 0;
    int startingRank = 1;
    if (numPlayer == 5){
      startingCred = 2;
    }
    else if (numPlayer == 6){
      startingCred = 4;
    }
    else if (numPlayer == 7 || numPlayer == 8){
      startingRank = 2;
    }

    // make numPlayer player objects, and add them to Queue
    for (int i = 0; i < numPlayer; i ++){
      playerQueue.add(new Player(startingRank, startingCred, gameBoard.getTrailer(), i));
    }
    activePlayer = playerQueue.poll();
    promptPlayer();
    return;
  }


  public static void initGameplay (){

    while(Board.getDaysLeft() != 0){
      System.out.println ("Days left: " + Board.getDaysLeft() + "\tScenes left: " + Board.getScenesLeft());
      while(Board.getScenesLeft() > 1){
        // call method which waits for user's click input
        activePlayer = playerQueue.remove();
        System.out.println("It is player " + activePlayer.getPlayerID() +"'s turn.");
        /*while (!activePlayer.isTurnDone()){
          promptPlayer(); // prompts player in Status Window
          ArrayList <String> command = getUsrInput();
        }*/
        activePlayer.endTurn();
        //activePlayer.takeTurn();
        playerQueue.add(activePlayer);
      }
      Board.endDay();
    }
    endGame();//Board.endGame();
  }


  public static void takeAction (ArrayList <String> command){
    String buffer = command.get(0);
    activePlayer.takeTurn (command);
    if (buffer.equals("end")){
      activePlayer.setActionTaken(false);
      playerQueue.add(activePlayer);
      activePlayer = playerQueue.poll();
      if (Board.getScenesLeft() == 1){
        System.out.println ("Ending day.");
        if (Board.getDaysLeft() == 0){ // end game if no more days
          endGame();
        }
        Board.endDay();
        System.out.println ("Days left: " + Board.getDaysLeft() + "\tScenes left: " + Board.getScenesLeft());
      }
    }
    promptPlayer();
  }


  // promptPlayer asks the user what actions they would like to take, given their location and status
  // RETURNS: an int indicating their status/location
  private static void promptPlayer (){
    // if the player has already taken an action on their move, they can only enter who where or end (but not if they are in the casting office)
    //System.out.println ("Player " + activePlayer.getPlayerID() + "has taken action? " + activePlayer.hasTakenAction() + "\nPlayer is in: " + activePlayer.getRoom().getRName());
    //System.out.println ("Player " + activePlayer.getPlayerID() + "'s role: " + activePlayer.getRole());


    // Player HAS taken an action this turn + is NOT in casting office
    if (activePlayer.hasTakenAction() == true && !activePlayer.getRoom().getRName().equals("Casting Office")){
      activePlayer.setMode(1);
      System.out.println("\nPlayer " + activePlayer.getPlayerID() + ", what would you like to do?\n\tOPTIONS: who, where, or end.");
    }
    // Player has NOT taken an action + HAS a role
    else if(activePlayer.getRole() != null){
      activePlayer.setMode(2);
      System.out.println("\nPlayer " + activePlayer.getPlayerID() + ", what would you like to do?\n\tOPTIONS: who, where, act, rehearse, or end.");
    }
    // Player HAS taken an action + IS in casting office
    else if(activePlayer.hasTakenAction() == true && activePlayer.getRoom().getRName().equals("Casting Office")){
      activePlayer.setMode(3);
      System.out.println("\nPlayer " + activePlayer.getPlayerID() + ", what would you like to do?\n\tOPTIONS: who, where, move, upgrade, or end.");
    }
    // Player has NOT taken an action + IS in Trailers
    else if(activePlayer.getRoom().getRName().equals("Trailers")){
      activePlayer.setMode(4);
      System.out.println("\nPlayer " + activePlayer.getPlayerID() + ", what would you like to do?\n\tOPTIONS: who, where, move, or end.");
    }
    // Player has NOT taken an action + does NOT have a role
    else if(activePlayer.getRole() == null){ // player in room, but without role
      activePlayer.setMode(5);
      System.out.println("\nPlayer " + activePlayer.getPlayerID() + ", what would you like to do?\n\tOPTIONS: who, where, work, or end.");
    }
    //System.out.println ("\nPlayer mode: " + activePlayer.getMode());
    return;
  }


  /* validateUserCommand takes user input and ensures that it is valid given their location/status,
  * asking them to input a different command if it is invalid.
  */
  public static boolean validateUserCommand (String command){
    boolean toRet = true;

    // check if valid command, given player's position / status
    switch (activePlayer.getMode()){
      case 1: // if player has already done an action this turn (and is not in the casting office)
        if(!command.equals("who") && !command.equals("where") && !command.equals("end")){
          System.out.println("You have already done an action this turn. Please enter a command from the list above.");
          System.out.print("> ");
          toRet = false;
        }
      break;
      case 2: // if player has a role already
        if(!command.equals("who") && !command.equals("where") && !command.equals("act") && !command.equals("rehearse") && !command.equals("end")){
          System.out.println("You can not do that while you are working a role. Please enter a command from the list above.");
          System.out.print("> ");
          toRet = false;
        }
        break;
      case 3: // if player is in casting office
        if(!command.equals("who") && !command.equals("where") && !command.equals("upgrade") && !command.equals("move") && !command.equals("end")){
          System.out.println("You can not do that while you are in the Casting Office. Please enter a command from the list above.");
          System.out.print("> ");
          toRet = false;
        }
        break;
      case 4: // if player is in Trailers
        if(!command.equals("who") && !command.equals("where") && !command.equals("move") && !command.equals("end")){
          System.out.println("You can not do that while you are in the Trailers. Please enter a command from the list above.");
          System.out.print("> ");
          toRet = false;
        }
        break;
      default: // player is not in Trailers or Casting Office, nor working a role (e.g. is in a room's whitespace w/o part)
        if(!command.equals("who") && !command.equals("where") && !command.equals("move") && !command.equals("end") && !command.equals("work")){
          System.out.println("You cannot do that right now. Please enter a command from the list above.");
          System.out.print("> ");
          toRet = false;
        }
    }
    return toRet;
  }


  public static Queue<Player> getPlayerQ (){
    return playerQueue;
  }


  public static Player getActivePlayer(){
    return activePlayer;
  }


  private static void endGame (){
    int playerNum = playerQueue.size();
    int [] scores = new int [playerNum]; // holds scores of all players
    int highestScore = 0; // saves highest score
    ArrayList <Integer> winners = new ArrayList <Integer> (playerNum);

    // get highest scorer
    for (int i = 0; i < playerNum; i++){
      scores[i] = playerQueue.poll().calculateScore();
      if (scores[i] >= highestScore){
        highestScore = scores[i];
      }
    }

    // Just in case of a tie, winners ArrayList with the playerID of all winners
    for (int j = 0; j < scores.length; j++){
      if (scores[j] == highestScore){
        winners.add(j);
      }
    }

    // create a string with the announcement for winning players
    StringBuilder winnerAnnounce = new StringBuilder ();
    if (winners.size() > 1){
      winnerAnnounce.append ("THERE IS A TIE!!\n");
    }
    winnerAnnounce.append ("Winners:\n");
    for (int i = 0; i < winners.size(); i++){
      winnerAnnounce.append ("Player " + winners.get(i) + " scored " + scores[i] + " points!\n");
    }

    // annouce other player's scores
    StringBuilder otherScores = new StringBuilder ("\nOther players:\n");
    for (int j = 0; j < scores.length - winners.size(); j++){
      if (!winners.contains(new Integer (j))){ // only print non-winner's scores
        otherScores.append ("Player " + j + " scored " + scores[j] + " points.\n");
      }
    }

    // add the two announcements together
    StringBuilder toAnnounce = winnerAnnounce.append(otherScores);

    view.BoardView.endGameView(otherScores.toString());
  }

}
