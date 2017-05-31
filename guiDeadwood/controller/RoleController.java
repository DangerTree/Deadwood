package controller;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class RoleController extends JPanel{

  private model.Role rlModel;

  public RoleController (int x, int y, int h, int w, model.Role role){
    rlModel = role;
    setBounds (x, y, w, h);
    setOpaque (false);
    addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        clicked();
      }
    });
    rlModel = role;
  }

  private void clicked(){
    if(model.Deadwood.validateUserCommand("work")){
      ArrayList<String> command = new ArrayList<String>();
      command.add("work");
      command.add(rlModel.getRoleName());
      model.Deadwood.takeAction(command);
    }
  }
}
