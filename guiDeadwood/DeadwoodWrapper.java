import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeadwoodWrapper{

  public static class Closer extends WindowAdapter {
    public void windowClosing (WindowEvent e) {
      System.exit(0);
    }
  }

  JLabel mLabel;

  static JButton bAct;
  static JButton bRehearse;
  static JButton bEnd;
  static JButton bUpgradeM;
  static JButton bUpgradeCR;
  static JComboBox upgradeChoice;
  static TextField inputRank;

  static JLabel ActivePlayerLabel;

  public static void main(String[] args) throws Exception{
    DeadwoodWrapper board = new DeadwoodWrapper();
    //model.Deadwood.initGameplay();
  }

  public DeadwoodWrapper() throws Exception{
    int numPlayers = startModel();

    JFrame frame = new JFrame();
    JLayeredPane pane = new JLayeredPane();

    model.Board bModel = model.Board.getBoard();
    view.BoardView bView = new view.BoardView (bModel, numPlayers); // connect board view to the model
    controller.BoardController bContr = new controller.BoardController (bModel); // connect board controller to model



    pane.add (bView, new Integer (0));
    pane.add (bContr, new Integer (1));
    pane.setVisible (true);

    frame.setTitle ("Deadwood");
    frame.setPreferredSize (new Dimension (1500,900));
    frame.setResizable (false);
    frame.addWindowListener (new Closer());

    JLabel mLabel = new JLabel ("MENU");
    mLabel.setBounds(1240, 0, 100, 20);
    pane.add(mLabel, new Integer(2));

    bAct = new JButton("ACT");
    bAct.setBackground(Color.white);
    bAct.setBounds(1210, 30, 100, 20);
    bAct.addMouseListener(new boardMouseListener());

    bRehearse = new JButton("REHEARSE");
    bRehearse.setBackground(Color.white);
    bRehearse.setBounds(1210, 60, 100, 20);
    bRehearse.addMouseListener(new boardMouseListener());

    bUpgradeM = new JButton("UPGRADE($)");
    bUpgradeM.setBackground(Color.white);
    bUpgradeM.setBounds(1320, 150, 130, 20);
    bUpgradeM.addMouseListener(new boardMouseListener());

    bUpgradeCR = new JButton("UPGRADE(CR)");
    bUpgradeCR.setBackground(Color.white);
    bUpgradeCR.setBounds(1320, 170, 130, 20);
    bUpgradeCR.addMouseListener(new boardMouseListener());

    inputRank = new TextField("RANK");
    inputRank.setBounds(1210, 160, 100, 20);


    bEnd = new JButton("END");
    bEnd.setBackground(Color.white);
    bEnd.setBounds(1210, 90, 100, 20);
    bEnd.addMouseListener(new boardMouseListener());

    /* Trying to make a choice box for upgrade ranks
    String[] ranks = { "2", "3", "4", "5", "6"};
    upgradeChoice = new JComboBox(ranks);
    upgradeChoice.setSelectedIndex(0);
    upgradeChoice.setBounds(1210, 120, 100, 20);
    upgradeChoice.addActionListener(this);
    */

    pane.add(bAct, new Integer(2));
    pane.add(bRehearse, new Integer(2));
    pane.add(bEnd, new Integer(2));
    pane.add(bUpgradeM, new Integer(2));
    pane.add(bUpgradeCR, new Integer(2));
    pane.add(inputRank, new Integer(2));
    //pane.add(upgradeChoice, new Integer(2));

    frame.add (pane);
    frame.pack();
    frame.setVisible(true);

  }


  private static int startModel (){
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
    return np;
  }

  class boardMouseListener implements MouseListener{
    public void mouseClicked(MouseEvent e){

      if(e.getSource() == bAct){
        System.out.println("pushed act");
      }
      else if(e.getSource() == bRehearse){
        System.out.println("pushed rehearse");
      }
      else if(e.getSource() == bEnd){
        System.out.println("PUSHED END");
        if(model.Deadwood.validateUserCommand("end")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("end");
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bUpgradeM){
        System.out.println("Pushed upgrade$");
      }
      else if(e.getSource() == bUpgradeCR){
        System.out.println("Pushed upgradeCR");
      }
    }
    public void mousePressed(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
  }



}
