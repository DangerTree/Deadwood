/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Dice class for Deadwood Assignment 2
*/

import java.util.Random;

public class Dice{

  private static Random randy = new Random();

  // Returns a "random" dice roll
  public int rollOneD(){
    return randy.nextInt(6)+1;
  }

  // rolls dice dNum times, returning results in an int[]
  public int[] bonusRoll(int budget){
    int[] rolls = new int[budget];
    for (int i = 0; i < budget; i++){
      rolls[i]= randy.nextInt(6)+1;
    }
    return rolls;
  }

}
