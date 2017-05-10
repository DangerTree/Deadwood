/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Dice class for Deadwood Assignment 2
*/

import java.util.Random;

public class Dice{

  private Random randy = new Random();

  // Returns a "random" dice roll
  public int rollOneD(){
    return randy.nextInt(5)+1;
  }

  // rolls dice dNum times, returning results in an int[]
  public int[] bonusRoll(int dNum){
    int[] rolls = new int[dNum];
    /*for (int i = 0; i < dNum; i++){
      rolls[i]= randy.nextInt(5)+1;
    }
    */
    return rolls;
  }

}
