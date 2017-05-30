// boardView organizes roompanels?
package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class RoomView extends JLayeredPane{
  private model.Room rmModel;
  //private JLabel roomRectangle;
  private JLabel[] shotCounterLabels;

  // rx, ry, rh, rw are the room placement and height/width params
  public RoomView (int rx, int ry, int rh, int rw, model.Room rModel) throws Exception {

    setBounds (rx, ry, rh, rw); // set bounds of JLayeredPane


    // create a

    //roomRectangle = new JLabel ();
    //roomRectangle.setVisible(false);
    //add(roomRectangle, new Integer (3));
    //roomRectangle.setBounds (0, 0, rh, rw);

    this.rmModel = rModel;

    // add this RoomView as a listener of a Room obj in the model
    //rModel.subscribe (this);

    // create a variable number of Role Views
    //RoleView rlv;
    System.out.println ("\nRoomView:     " + this);
  }


  public void changedShotCounter (int rmShotNum){
    // walk from beginning of shotCounterLabels array until = num remaining...
    // all other shot counters are invisible
    int totalShots = shotCounterLabels.length;
    for (int i = 0; i < totalShots; i++){
      if (i < rmShotNum){
        shotCounterLabels[i].setVisible(true);
      }
      else{
        shotCounterLabels[i].setVisible(false);
      }
    }
  }


  public void setupShots (int [][] shotCtrLocations){
    int shotNum = shotCtrLocations.length;
    ResourcesDW r = ResourcesDW.getInstance();
    ImageIcon shotIcon = r.getShotIcon();
    shotCounterLabels = new JLabel[shotNum];
    for (int i = 0; i < shotNum; i++){
      shotCounterLabels[i] = new JLabel();
      shotCounterLabels[i].setIcon(shotIcon);
      shotCounterLabels[i].setBounds(shotCtrLocations[i][0], shotCtrLocations[i][1], shotIcon.getIconWidth(), shotIcon.getIconHeight());
      shotCounterLabels[i].setVisible(true);
      add(shotCounterLabels[i]);
    }
  }

  // sx, sy, sh, sw are the room's scene placement and height/width params
  /*public void addSceneView (int sx, int sy, int sh, int sw, model.Scene sModel){
    // makes a new scene view
    SceneView sv = new SceneView (sx, sy, sh, sw, sModel);
    add(sv, new Integer (4));
  }*/




}
