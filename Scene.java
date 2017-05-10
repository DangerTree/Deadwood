/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Scene class for Deadwood Assignment 2
*/

public class Scene{

  private String movieName, sceneDescript, sName;
  private int budget;
  private Role[] sRoleList;
  private boolean faceUp = false;



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

  public Role[] getSRoleList(){
    return sRoleList;
  }

  public int getSRoleListSize(){
    return sRoleList.length;
  }

  public boolean isCardFlipped(){
    return faceUp;
  }

  public void flipSceneCard(){
    this.faceUp = true;
  }

}
