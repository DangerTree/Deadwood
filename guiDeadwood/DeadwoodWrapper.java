import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import java.awt.event.WidowAdapter;
import java.awt.event.WindowEvent;

public class DeadwoodWrapper{

  public static class Closer extends WindowAdapter {
    public void windowClosing (WindowEvent e) {
      System.exit(0);
    }
  }

  public static void main(String[] args) throws Exception{

    JFrame frame = new JFrame();
    JLayeredPane pane = new JLayeredPane();

    //model.Deadwood 

  }







}
