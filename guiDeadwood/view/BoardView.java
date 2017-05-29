// boardView organizes roompanels?
package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class BoardView extends JLayeredPane{
  private JLabel boardLabel;

  public BoardView (model.Board bModel) throws Exception {

    ResourcesDW r = ResourcesDW.getInstance();
    ImageIcon backgroundImg = r.getBG();

    boardLabel = new JLabel (backgroundImg);
    boardLabel.setBounds (0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
    add(boardLabel, new Integer (0)); // add the board image as the first layer
    setBounds (boardLabel.getBounds());

    // create room view objects
    makeRoomViews(bModel);
  }

  private void makeRoomViews(model.Board bModel) throws Exception{

    RoomView rv;

    rv = new RoomView (0, 0, 450, 230, bModel.getRoom ("Train Station"));
    rv.addSceneView (15, 65, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (250, 0, 250, 350, bModel.getRoom ("Jail"));
    rv.addSceneView (275, 25, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (600, 0, 200, 600, bModel.getRoom ("Main Street"));
    rv.addSceneView (965, 25, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (230, 250, 200, 370, bModel.getRoom ("General Store"));
    rv.addSceneView (365, 280, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (600, 200, 250, 380, bModel.getRoom ("Saloon"));
    rv.addSceneView (625, 275, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (225, 450, 250, 375, bModel.getRoom ("Ranch"));
    rv.addSceneView (245, 470, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (0, 700, 200, 600, bModel.getRoom ("Secret Hideout"));
    rv.addSceneView (20, 725, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (600, 450, 200, 370, bModel.getRoom ("Bank"));
    rv.addSceneView (618, 470, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (600, 650, 250, 340, bModel.getRoom ("Church"));
    rv.addSceneView (618, 730, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (970, 450, 450, 230, bModel.getRoom ("Hotel"));
    rv.addSceneView (965, 735, 125, 215);
    add(rv, new Integer (1));

    rv = new RoomView (980, 250, 200, 220, bModel.getRoom ("Trailers"));
    add(rv, new Integer (1));

    rv = new RoomView (0, 450, 230, 225, bModel.getRoom ("Casting Office"));
    add(rv, new Integer (1));
  }

}
