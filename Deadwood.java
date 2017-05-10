/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Deadwood class for Deadwood Assignment 2
*
*   Driver class for the Deadwood game
*/

import java.util.LinkedList;
import java.util.Queue;

public class Deadwood{

  private int numPlayer;
  private Queue<Player> playerQueue = new LinkedList<Player>();

  public static void main (String args[]){

    Board gameBoard = Board.getBoard(3);
    gameBoard.setupRooms();
    

    return;
  }
}
