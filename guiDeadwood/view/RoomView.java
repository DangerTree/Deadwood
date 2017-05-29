// boardView organizes roompanels?
package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class RoomView extends JLayeredPane{
  private JLabel roomRectangle;

  // rx, ry, rh, rw are the room placement and height/width params
  public RoomView (int rx, int ry, int rh, int rw, model.Room rModel) throws Exception {

    roomRectangle = new JLabel ();
    roomRectangle.setBounds (rx, ry, rh, rw);
    roomRectangle.setVisible(false);
    add(roomRectangle, new Integer (1)); // add the board image as the first layer
    setBounds (roomRectangle.getBounds());

    // add this RoomView as a listener of a Room obj in the model
    //rModel.subscribe (this);

    // create a variable number of Role Views
    //RoleView rlv;



  }

  // sx, sy, sh, sw are the room's scene placement and height/width params
  public void addSceneView (int sx, int sy, int sh, int sw){
    // makes a new scene view
  }




}
