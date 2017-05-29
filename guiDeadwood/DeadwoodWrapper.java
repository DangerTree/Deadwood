import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class DeadwoodWrapper{

  public static class Closer extends WindowAdapter {
    public void windowClosing (WindowEvent e) {
      System.exit(0);
    }
  }

  public static void main(String[] args) throws Exception{
    startModel();

    JFrame frame = new JFrame();
    JLayeredPane pane = new JLayeredPane();

    model.Board bModel = model.Board.getBoard();
    view.BoardView bView = new view.BoardView (bModel); // connect board view to the model
    // controller.BoardContr bContr = new controller.BoardCountr (bModel); // connect board controller to model

    pane.add (bView, new Integer (0));
    //pane.add (bContr, new Integer (1));
    pane.setVisible (true);

    frame.setTitle ("Deadwood");
    frame.setPreferredSize (new Dimension (1200,900));
    frame.setResizable (false);
    frame.addWindowListener (new Closer());

    frame.add (pane);
    frame.pack();
    frame.setVisible(true);

    model.Deadwood.initGameplay();
  }


  private static void startModel (){
    Object[] playerNumOptions = {"2", "3", "4", "5", "6", "7", "8"};
    int np = 0;
    String numOfPlayers = (String)JOptionPane.showInputDialog(null,
                                                    "How many players?\n",
                                                    "Welcome to Deadwood!",
                                                    JOptionPane.PLAIN_MESSAGE,
                                                    null,
                                                    playerNumOptions,
                                                    playerNumOptions[0]);
    try {
      np = Integer.parseInt(numOfPlayers);
      if (np < 2 || np > 8){
        System.out.println ("Invalid number of players. Must be between 2 and 8 players, inclusive.");
        System.exit(1);
      }
    }
    catch (NumberFormatException e){
      System.out.println ("Wrong format for argument 0 [number of players]. Please input an integer.");
      System.exit(1);
    }

    model.Deadwood.initGameboard(np); // setup the model
  }





}
