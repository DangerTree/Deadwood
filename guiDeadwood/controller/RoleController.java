package controller;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class RoleController extends JPanel implements model.Role.Listener{

  private model.Role rlModel;

  public RoleController (int x, int y, int h, int w, model.Role role){
    //System.out.println ("creating new RoleController");
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
    // try to take role
    if (model.Deadwood.validateUserCommand ("work")){
      ArrayList <String> command = new ArrayList <String>();
      command.add("work");
      command.add(rlModel.getRoleName());
      model.Deadwood.takeAction (command);
    }
  }

  public void changed(model.Role r){
    // nothing????
  }
}
