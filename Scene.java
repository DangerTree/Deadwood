/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Class to maintain Scene card objects for Deadwood
*   Version 1.0, skeleton methods
*/

public Class Scene{

  private String movieName, sceneDescript, sName;

  private int budget;

  private Role[] sRoleList;


  //Scene object constructor, takes all attributes as parameters
  public Scene(String movieName, String sceneDescript, String sName, int budget, Role[] sRoleList){

  }

  //takes a roll from a player and checks it against the movie's budget to determine a success or failure to act for the scene
  public boolean checkBudget(int playerRoll){
    /*
    boolean success = true;

    if(playerRoll < budget){
      success = false;
    }
    return success;
    */
    return true;
  }

}
