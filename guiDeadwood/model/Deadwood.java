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


  /*public static void run (int numOfPlayers){

    Board gameB = initGameboard(numOfPlayers);
    initGameplay();
    return;
  }*/


  public static Board initGameboard (int numPlayer){

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
    return gameBoard;
  }


  public static void initGameplay (){

    while(Board.getDaysLeft() != 0){
      System.out.println ("Days left: " + Board.getDaysLeft() + "\tScenes left: " + Board.getScenesLeft());
      while(Board.getScenesLeft() > 1){
        System.out.println("It is player " + playerQueue.peek().getPlayerID() +"'s turn.");
        Player activePlayer = playerQueue.remove();
        activePlayer.takeTurn();
        playerQueue.add(activePlayer);
      }
      Board.endDay();
    }
    endGame();//Board.endGame();
  }

  private static void endGame (){
    int playerNum = playerQueue.size();
    int [] scores = new int [playerNum];
    int highestScorerID = 0;
    ArrayList <Integer> winners = new ArrayList <Integer> (playerNum);

    for (int i = 0; i < playerNum; i++){
      scores[i] = playerQueue.poll().calculateScore();
      if (scores[i] > scores[highestScorerID]){
        highestScorerID = i;
      }
    }
    // Fill winners ArrayList with the playerID of the winners
    for (int j = 0; j < playerNum; j++){
      if (scores[j] == highestScorerID){
        winners.add(j);
      }
    }
    // Print our winners
    if (winners.size() > 1){
      System.out.print ("There is a tie! Players ");
      for (int k = 0; k < winners.size() -1 ; k++){
        System.out.print (winners.get(k) + ", ");
      }
      System.out.print ("and " + winners.get(winners.size()-1) + " won!");
    }
    else {
      System.out.println ("Player " + highestScorerID + " won!");
    }
  }

}
