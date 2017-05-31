// boardView organizes roompanels?
package view;

//import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

//public class RoomView extends JLayer{
public class RoomView extends JLayeredPane implements model.Room.Listener{
  private model.Room rmModel;
  //private JLabel roomRectangle;
  private JLabel[] shotCounterLabels;

  // rx, ry, rh, rw are the room placement and height/width params
  public RoomView (int rx, int ry, int rh, int rw, model.Room rModel) throws Exception {

    setBounds (rx, ry, rw, rh); // set bounds of JLayeredPane
    this.rmModel = rModel;
    // add this RoomView as a listener of a Room obj in the model
    rmModel.subscribe (this);
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


  /* setupShots:
  PARAMETER:  2d int array of all the x, y coordiates of the shot counters in the room
  */
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
      add(shotCounterLabels[i], new Integer (1));
    }
  }

  // sx, sy, sh, sw are the room's scene placement and height/width params
  /*public void addSceneView (int sx, int sy, int sh, int sw, model.Scene sModel){
    // makes a new scene view
    SceneView sv = new SceneView (sx, sy, sh, sw, sModel);
    add(sv, new Integer (4));
  }*/




}
