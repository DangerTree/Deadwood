/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Role class for Deadwood Assignment 2
*/

public class Role{

  private Player actor;
  private int rankRequired;
  private String rName;
  private String rQuote;

  public Role (String roleName, String roleQuote, int rank){
    this.rankRequired = rank;
    this.rName = roleName;
    this.rQuote = roleQuote;
  }

  public void addActor (Player newActor){
    this.actor = newActor;
  }

  public Player getActor (){
    return this.actor;
  }

  public void actorLeaves(){
	  this.actor = null;
  }

  public int getRank(){
	  return this.rankRequired;
  }

  public String getRoleWho(){
    String toRet = this.rName + ", " + this.rQuote;
    return toRet;
  }


}
