import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.StringBuilder;


public class DeadwoodWrapper{

  public static class Closer extends WindowAdapter{
    public void windowClosing (WindowEvent e) {
      System.exit(0);
    }
  }

  JLabel activeP_label;
  JLabel pRank_label;
  JLabel pPracChip_label;
  JLabel pMcnt_label;
  JLabel pCrcnt_label;

  JLabel mLabel;
  static JButton bAct;
  static JButton bRehearse;
  static JButton bEnd;
  static JButton bUpgradeM;
  static JButton bUpgradeCR;
  static JComboBox upgradeChoice;
  static JTextField inputRank;
  static JTextArea playerInfo;

  static JLabel activePlayerLabel;


  public static void main(String[] args) throws Exception{
    DeadwoodWrapper board = new DeadwoodWrapper();
  }


  public DeadwoodWrapper() throws Exception{
    int numPlayers = startModel();

    JFrame frame = new JFrame();
    JLayeredPane pane = new JLayeredPane();

    model.Board bModel = model.Board.getBoard();
    //view.BoardView bView = new view.BoardView (bModel, numPlayers); // connect board view to the model
    view.BoardView bView = view.BoardView.getBoardView (bModel, numPlayers); // connect board view to the model
    controller.BoardController bContr = new controller.BoardController (bModel); // connect board controller to model

    pane.add (bView, new Integer (0));
    pane.add (bContr, new Integer (1));
    pane.setVisible (true);

    frame.setTitle ("Deadwood");
    frame.setPreferredSize (new Dimension (1500,900));
    frame.setResizable (false);
    frame.addWindowListener (new Closer());

    addCtrlPanel(pane);

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


  // addCtrlPanel creates and adds elements to the control panel on ...
  // ... the right side of the board.
  public void addCtrlPanel(JLayeredPane pane){

    JLabel mLabel = new JLabel ("MENU");
    mLabel.setBounds(1250, 0, 140, 20);
    pane.add(mLabel, new Integer(2));

    bAct = new JButton("ACT");
    bAct.setBackground(Color.white);
    bAct.setBounds(1210, 30, 140, 20);
    bAct.addMouseListener(new boardMouseListener());

    bRehearse = new JButton("REHEARSE");
    bRehearse.setBackground(Color.white);
    bRehearse.setBounds(1210, 60, 140, 20);
    bRehearse.addMouseListener(new boardMouseListener());

    bEnd = new JButton("END");
    bEnd.setBackground(Color.white);
    bEnd.setBounds(1210, 90, 140, 20);
    bEnd.addMouseListener(new boardMouseListener());

    bUpgradeM = new JButton("UPGRADE($)");
    bUpgradeM.setBackground(Color.white);
    bUpgradeM.setBounds(1320, 150, 130, 20);
    bUpgradeM.addMouseListener(new boardMouseListener());

    bUpgradeCR = new JButton("UPGRADE(CR)");
    bUpgradeCR.setBackground(Color.white);
    bUpgradeCR.setBounds(1320, 170, 130, 20);
    bUpgradeCR.addMouseListener(new boardMouseListener());

    inputRank = new JTextField("RANK");
    inputRank.setBounds(1210, 160, 100, 20);

    /****************** Display active player... *****************/

    JLabel activePlayerLabel = new JLabel ("Active Player Stats");
    activePlayerLabel.setBounds(1250, 250, 140, 30);
    pane.add(activePlayerLabel, new Integer(2));

    /*
    JLabel activeP_label;
    JLabel pRank_label;
    JLabel pPracChip_label;
    JLabel pMcnt_label;
    JLabel pCrcnt_label;
    */

    //playerInfo = new playerStatusBoxView();

    playerInfo = new JTextArea ();
    playerInfo.append("Rank: ");
    playerInfo.append("\nMoney: ");
    playerInfo.append("\nCredits: ");
    playerInfo.append("\nPractice Chips: ");
    
    playerInfo.setWrapStyleWord(true);
    playerInfo.setLineWrap(true);
    playerInfo.setBounds(1210, 280, 280, 150);
    pane.add(playerInfo, new Integer (2));
  }

  /************************************************************/
  class boardMouseListener implements MouseListener{
    public void mouseClicked(MouseEvent e){

      if(e.getSource() == bAct){
        System.out.println("pushed act");
        if (model.Deadwood.validateUserCommand ("act")){
          ArrayList <String> command = new ArrayList <String>();
          command.add("act");
          model.Deadwood.takeAction (command);
        }
      }
      else if(e.getSource() == bRehearse){
        if(model.Deadwood.validateUserCommand("rehearse")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("rehearse");
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bEnd){
        if(model.Deadwood.validateUserCommand("end")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("end");
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bUpgradeM){
        if(model.Deadwood.validateUserCommand("upgrade")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("upgrade");
          command.add("$");
          command.add(inputRank.getText());
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bUpgradeCR){
        if(model.Deadwood.validateUserCommand("upgrade")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("upgrade");
          command.add("cr");
          command.add(inputRank.getText());
          model.Deadwood.takeAction(command);
        }
      }
      updatePlayerStats();
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




  private void updatePlayerStats (){
    playerInfo.append("PlayerID: " + Integer.toString (model.Deadwood.getActivePlayer().getPlayerID()) + "\n");
    playerInfo.append("Rank: ");
    playerInfo.append (Integer.toString (model.Deadwood.getActivePlayer().getRank()));
    playerInfo.append("\nMoney: ");
    playerInfo.append("\nCredits: ");
    playerInfo.append("\nPractice Chips: ");
  }



  // startModel creates a popup asking how many players there are ...
  // then calls a method in model.Deadwood to populate/init the model
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


}
