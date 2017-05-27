// boardView organizes roompanels?
package views;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class BoardView extends JLayeredPane{
  private JLabel boardLabel;

  public BoardView (model.Board bModel) throws Exception {
    ImageIcon icon = new ImageIcon (ImageIO.read(cls.getResourceAsStream("../resources/fullBoard.jpg"))); // board image
    boardLabel.setIcon (icon);
    add(boardLabel, new Integer (0)); // add the board image as the first layer
    boardLabel.setBounds (0, 0, screenSize.width, screenSize.height);
    setBounds (boardLabel.getBounds());




  }

}
