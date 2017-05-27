import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.ImageIO;

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

    s.subscribe(this);
    s.setIcon () // face down card
  }

  // IF THE SCENE IS WRAPPING, MAKE THE SCENE VIEW INVISIBLE
  public void signalWrapping (){
    sceneLabel.setVisible(false);
  }

  public void flipSceneCard (){
    sceneLabel.setIcon(getIcon);
  }



}
