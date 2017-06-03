package controller;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLayeredPane;


public class SceneController extends JLayeredPane implements model.Scene.Listener{

  private model.Scene sModel;
  private String roomName;

  public SceneController (int x, int y, int h, int w, model.Scene scene){
    sModel = scene;
    setBounds (x, y, w, h);
    setOpaque (false);
    scene.subscribe(this);

    addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        System.out.println ("clicked scene controller");
      }
    });

  }

  public void newScene(model.Scene scene){
    sModel = scene;
    scene.subscribe(this);

  }

  public void changed(){
    //update the image for this scene
  }


  public void signalWrapping (){
    this.removeAll(); // throw away role controllers
  }


  // if scene is flipped, make role controllers
  public void flipSceneCard (model.Scene s){

    //System.out.println ("in flip Scene Card");
    model.Role[] roleList = s.getSRoleList();
    RoleController rl_c;

    switch (roleList.length){
      case 1: // make a role view positioned on the card for only 1 role
        rl_c = new RoleController (87, 51, 47, 47, roleList[0]);
        this.add (rl_c, new Integer (4));
        break;
      case 2:
        rl_c = new RoleController (122, 51, 47, 47, roleList[0]);
        this.add (rl_c, new Integer (4));
        rl_c = new RoleController (56, 51, 47, 47, roleList[1]);
        this.add (rl_c, new Integer (4));
        break;
      case 3:
        rl_c = new RoleController (152, 51, 47, 47, roleList[0]);
        this.add (rl_c, new Integer (4));
        rl_c = new RoleController (86, 51, 47, 47, roleList[1]);
        this.add (rl_c, new Integer (4));
        rl_c = new RoleController (21, 51, 47, 47, roleList[2]);
        this.add (rl_c, new Integer (4));
        break;
    }

  }

}
