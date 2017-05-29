// boardView organizes roompanels?
package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class BoardView extends JLayeredPane{
  private JLabel boardLabel;

  public BoardView (model.Board bModel) throws Exception {
    System.out.println ("in BoardView.java");


    //boardLabel = new JLabel ();
    //Class cls = getClass();
    ResourcesDW r = ResourcesDW.getInstance();
    ImageIcon backgroundImg = r.getBG();
    boardLabel = new JLabel (backgroundImg);
    //boardLabel.setIcon(backgroundImg);
    boardLabel.setBounds (0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
    add(boardLabel, new Integer (0)); // add the board image as the first layer
    setBounds (boardLabel.getBounds());




  }

}
