/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Scene class for Deadwood Assignment 2
*/

public class Scene{

  private String movieName, sceneDescript;
  private int budget, sNumber;
  private Role[] sRoleList;
  private boolean faceUp;


  //Scene object constructor, takes all attributes as parameters
  public Scene(String movieName, String sceneDescript, int sNumber, int budget, Role[] sRoleList){

	  this.movieName = movieName;
	  this.sceneDescript = sceneDescript;
	  this.sNumber = sNumber;
	  this.budget = budget;
	  this.sRoleList = sRoleList;
	  this.faceUp = false;

  }

  //takes a roll from a player and checks it against the movie's budget to determine a success or failure to act for the scene
  public boolean checkBudget(int playerRoll){
    return (playerRoll < this.budget) ? false : true;
  }

  public int getBudget(){
    return this.budget;
  }

  public String getMovieName(){
	  return this.movieName;
  }

  public Role[] getSRoleList(){
    return this.sRoleList;
  }

  public int getSRoleListSize(){
    return sRoleList.length;
  }

  public boolean isCardFlipped(){
    return this.faceUp;
  }

  public void flipSceneCard(){
    this.faceUp = true;
  }

  public int getSNumber(){
    return this.sNumber;
  }

  public String getSName(){
    return this.movieName;
  }


}
