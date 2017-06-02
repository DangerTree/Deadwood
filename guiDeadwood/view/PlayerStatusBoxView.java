package view;

import javax.swing.JTextArea;


class PlayerStatusBoxView extends JTextArea implements model.Player.Listener{

  public void changed (model.Player p){

  }
  /*

  public void mouseClicked(MouseEvent e){

    if(e.getSource() == bAct){
      System.out.println("pushed act");
      if (model.Deadwood.validateUserCommand ("act")){
        ArrayList <String> command = new ArrayList <String>();
        command.add("act");
        model.Deadwood.takeAction (command);
      }
    }
    else if(e.getSource() == bRehearse){
      //System.out.println("pushed rehearse");
      if(model.Deadwood.validateUserCommand("rehearse")){
        ArrayList<String> command = new ArrayList<String>();
        command.add("rehearse");
        model.Deadwood.takeAction(command);
      }
    }
    else if(e.getSource() == bEnd){
      //System.out.println("PUSHED END");
      if(model.Deadwood.validateUserCommand("end")){
        ArrayList<String> command = new ArrayList<String>();
        command.add("end");
        model.Deadwood.takeAction(command);
      }
    }
    else if(e.getSource() == bUpgradeM){
      //System.out.println("Pushed upgrade$");
      //String inputtedText = inputRank.getText();
      //System.out.println ("inputtedText: " + inputtedText);
      if(model.Deadwood.validateUserCommand("upgrade")){
        ArrayList<String> command = new ArrayList<String>();
        command.add("upgrade");
        command.add("$");
        command.add(inputRank.getText());
        model.Deadwood.takeAction(command);
      }
    }
    else if(e.getSource() == bUpgradeCR){
      //System.out.println("Pushed upgradeCR");
      if(model.Deadwood.validateUserCommand("upgrade")){
        ArrayList<String> command = new ArrayList<String>();
        command.add("upgrade");
        command.add("cr");
        command.add(inputRank.getText());
        model.Deadwood.takeAction(command);
      }
    }
    updatePlayerStats();
  }
  public void mousePressed(MouseEvent e){
  }
  public void mouseReleased(MouseEvent e){
  }
  public void mouseEntered(MouseEvent e){
  }
  public void mouseExited(MouseEvent e){
  }
  */
}
