package controller;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class RoomController extends JPanel{

  private model.Room rmModel;

  public RoomController (int x, int y, int h, int w, model.Room rm){

    setBounds (x, y, w, h);
    setOpaque (false);
    addMouseListener (new MouseAdapter(){
      public void mouseClicked (MouseEvent e) {
        clicked();
      }
    });
    rmModel = rm;
  }

  private void clicked (){
    // tryToMove
    if (model.Deadwood.validateUserCommand ("move")){
      ArrayList <String> command = new ArrayList <String>();
      command.add("move");
      command.add(rmModel.getRName());
      model.Deadwood.takeAction (command);
    }
  }

}
