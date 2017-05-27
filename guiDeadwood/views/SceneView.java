package views;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class SceneView
  extends javax.swing.JLayeredPane
  implements model.Scene.Listener
{

  private JLabel sceneLabel;

  public SceneView (int x, int y, int h, int w, model.Scene s){

    setBounds (x, y, h, w);
    sceneLabel = new JLabel();
    sceneLabel.setVisible(true);
    add (sceneLabel, new Integer (0)); // THIS IS PROBABLY THE WRONG LAYER
    sceneLabel.setBounds(0, 0, h, w);
    ResourcesDW r = new ResourcesDW.getInstance();
    s.setIcon (r.getBackOfCard()); // face down card

    s.subscribe(this);
  }

  // IF THE SCENE IS WRAPPING, MAKE THE SCENE VIEW INVISIBLE
  public void signalWrapping (){
    sceneLabel.setVisible(false);
  }

  public void flipSceneCard (model.Scene s){
    ResourcesDW r = new ResourcesDW.getInstance();
    sceneLabel.setIcon(r.getSceneIcon(s.getMovieName(), s.getSNumber()));
  }



}
