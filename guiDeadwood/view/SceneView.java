package view;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class SceneView
  extends javax.swing.JLayeredPane
  implements model.Scene.Listener
{

  private JLabel sceneLabel;

  public SceneView (int x, int y, int h, int w, model.Scene s){

    setBounds (x, y, w, h); // create bounds of JLayeredPane
    sceneLabel = new JLabel(); // create new JLabel (will hold scene card image) in JLayeredPane
    ResourcesDW r = ResourcesDW.getInstance();
    sceneLabel.setIcon (r.getBackOfCard()); // face down card
    sceneLabel.setBounds(0, 0, w, h); // establish bounds of scene card
    sceneLabel.setVisible(true);
    add (sceneLabel, new Integer (1)); // add sceneLabel to JLayeredPane

    //System.out.println ("s: " + s);
    //System.out.println ("this: " + this);
    s.subscribe(this); // tell the scene model that it is listening to it

    makeRoleViews(s);
  }


  private void makeRoleViews(model.Scene s){
    RoleView rl_v;
    model.Role[] roleList = s.getSRoleList();
    switch (s.getSRoleListSize()){
      case 1: // make a role view positioned on the card for only 1 role
        rl_v = new RoleView (87, 51, 47, 47, roleList[0]);
        this.add (rl_v, new Integer (2));
        break;
      case 2:
        rl_v = new RoleView (122, 51, 47, 47, roleList[0]);
        this.add (rl_v, new Integer (2));
        rl_v = new RoleView (56, 51, 47, 47, roleList[1]);
        this.add (rl_v, new Integer (2));
        break;
      case 3:
        rl_v = new RoleView (152, 51, 47, 47, roleList[0]);
        this.add (rl_v, new Integer (2));
        rl_v = new RoleView (86, 51, 47, 47, roleList[1]);
        this.add (rl_v, new Integer (1));
        rl_v = new RoleView (21, 51, 47, 47, roleList[2]);
        this.add (rl_v, new Integer (2));
        break;
    }

  }


  // IF THE SCENE IS WRAPPING, MAKE THE SCENE VIEW INVISIBLE
  public void signalWrapping (){
    sceneLabel.setVisible(false);
  }

  public void flipSceneCard (model.Scene s){
    ResourcesDW r = ResourcesDW.getInstance();
    sceneLabel.setIcon(r.getSceneIcon(s.getMovieName(), s.getSNumber()));
  }



}
