/*
The roll class for Deadwood game
*/

public class Role{

  private Player actor;
  private int rankRequired;
  private String rName;
  private String rQuote;

  public Role (String roleName, String roleQuote, int rank){
    /*this.rankRequired = rank;
    this.rName = roleName;
    this.rQuote = roleQuote;
    */
  }

  public void addActor (Player newActor){
    //this.actor = newActor;
  }

  public Player getActor (){
    return this.actor;
  }

}
