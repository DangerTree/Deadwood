package view;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class RoleView
  extends javax.swing.JLayeredPane
  implements model.Role.Listener
{

  private JLabel roleLabel;

  public RoleView (int x, int y, int h, int w, model.Role rl){

    setBounds (x, y, w, h); // create bounds of JLayeredPane
    roleLabel = new JLabel(); // create new JLabel (will hold scene card image) in JLayeredPane
    roleLabel.setBounds(0, 0, w, h); // establish bounds of scene card
    roleLabel.setVisible(true);
    add (roleLabel, new Integer (5)); // add roleLabel to JLayeredPane

    rl.subscribe(this); // tell the scene model that it is listening to it

  }


  // If a player took or left the role, indicate that
  public void changed (model.Role rl){
    //System.out.println ("changed in RoleView");
    if (rl.getActor() != null){
      ResourcesDW r = ResourcesDW.getInstance();
      // update the role's label to be a pic of the player's die
      roleLabel.setIcon (r.getPlayerIcon (rl.getActor().getPlayerID(), rl.getActor().getRank()));
      roleLabel.setVisible(true);
    }
    else {
      roleLabel.setVisible(false);
    }
  }

}
