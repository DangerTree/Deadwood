/*
*   May 4, 2017, Sarah Gunderson, Zachary Lazar
*   Role class for Deadwood Assignment 2
*/
package model;

import java.util.Collection;
import java.util.LinkedList;

public class Role{

  private Player actor;
  private int rankRequired;
  private String rName;
  private String rQuote;

  /**************************************************************/

  public interface Listener {
    public void changed(Role rl);
  }

  private Collection<Listener> listeners;

  public void subscribe(Listener l){
    listeners.add(l);
  }

  public void changed (Role rl){
    for(Listener l: listeners)
      l.changed(this);
  }



  /**************************************************************/

  public Role (String roleName, String roleQuote, int rank){
    listeners = new LinkedList<Listener>();
    this.rankRequired = rank;
    this.rName = roleName;
    this.rQuote = roleQuote;
  }

  public void addActor (Player newActor){
    this.actor = newActor;
    changed(this);
  }

  public Player getActor (){
    return this.actor;
  }

  public void actorLeaves(){
	  this.actor = null;
    changed(this);
  }

  public int getRank(){
	  return this.rankRequired;
  }

  public String getRoleWho(){
    String toRet = this.rName + ", " + this.rQuote;
    return toRet;
  }

  public String getRoleName(){
    return this.rName;
  }


}
