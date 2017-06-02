//package guiDeadwood;

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

  public static class Closer extends WindowAdapter {
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

  static JTextArea rankInfo;
  static JTextArea moneyInfo;
  static JTextArea creditInfo;
  static JTextArea practiceInfo;


  public static void main(String[] args) throws Exception{
    DeadwoodWrapper board = new DeadwoodWrapper();
  }


  public DeadwoodWrapper() throws Exception{
    int numPlayers = startModel();

    JFrame frame = new JFrame();
    JLayeredPane pane = new JLayeredPane();

    model.Board bModel = model.Board.getBoard();
    view.BoardView bView = new view.BoardView (bModel, numPlayers); // connect board view to the model
    controller.BoardController bContr = new controller.BoardController (bModel); // connect board controller to model
    //view.PlayerStatusBoxView pStatBox = new view.PlayerStatusBoxView (model.Deadwood.getPlayerQ());
    view.PlayerStatusBoxView pStatBox = new view.PlayerStatusBoxView ();

    pane.add (bView, new Integer (0));
    pane.add (bContr, new Integer (1));
    pane.add (pStatBox, new Integer (2));
    addCtrlPanel(pane);

    pane.setVisible (true);

    frame.setTitle ("Deadwood");
    frame.setPreferredSize (new Dimension (1500,900));
    frame.setResizable (false);
    frame.addWindowListener (new Closer());

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

    /*

    JLabel activePlayerLabel = new JLabel ("Active Player Stats");
    activePlayerLabel.setBounds(1250, 250, 140, 30);
    pane.add(activePlayerLabel, new Integer(2));

    JLabel rankLabel = new JLabel ("Rank:");
    rankLabel.setBounds(1270, 280, 80, 15);
    pane.add(rankLabel, new Integer(2));

    JLabel moneyLabel = new JLabel("Money: ");
    moneyLabel.setBounds(1260, 300, 80, 15);
    pane.add(moneyLabel, new Integer(2));

    JLabel creditLabel = new JLabel("Credits: ");
    creditLabel.setBounds(1255, 320, 80, 15);
    pane.add(creditLabel, new Integer(2));

    JLabel practiceLabel = new JLabel("Practice: ");
    practiceLabel.setBounds(1250, 340, 150, 15);
    pane.add(practiceLabel, new Integer(2));





    rankInfo = new JTextArea ();


    rankInfo.setWrapStyleWord(true);
    rankInfo.setLineWrap(true);
    rankInfo.setBounds(1320, 280, 30, 15);

    moneyInfo = new JTextArea();

    moneyInfo.setWrapStyleWord(true);
    moneyInfo.setLineWrap(true);
    moneyInfo.setBounds(1320, 300, 30, 15);

    creditInfo = new JTextArea();

    creditInfo.setWrapStyleWord(true);
    creditInfo.setLineWrap(true);
    creditInfo.setBounds(1320, 320, 30, 15);

    practiceInfo = new JTextArea();

    practiceInfo.setWrapStyleWord(true);
    practiceInfo.setLineWrap(true);
    practiceInfo.setBounds(1320, 340, 30, 15);

    updatePlayerStats();

    pane.add(moneyInfo, new Integer(2));
    pane.add(creditInfo, new Integer(2));
    pane.add(rankInfo, new Integer (2));
    pane.add(practiceInfo, new Integer(2));
    */
  }

  public static void updatePlayerStats(){

    rankInfo.setText(model.Deadwood.getActivePlayer().getRank() + "");
    moneyInfo.setText(model.Deadwood.getActivePlayer().getMoneyCnt() + "");
    creditInfo.setText(model.Deadwood.getActivePlayer().getCreditCnt() + "");
    practiceInfo.setText(model.Deadwood.getActivePlayer().getPracticeCnt() + "");

  }


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
        //System.out.println("pushed rehearse");
        if(model.Deadwood.validateUserCommand("rehearse")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("rehearse");
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bEnd){
        //System.out.println("PUSHED END");
        if(model.Deadwood.validateUserCommand("end")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("end");
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bUpgradeM){
        //System.out.println("Pushed upgrade$");
        //String inputtedText = inputRank.getText();
        //System.out.println ("inputtedText: " + inputtedText);
        if(model.Deadwood.validateUserCommand("upgrade")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("upgrade");
          command.add("$");
          command.add(inputRank.getText());
          model.Deadwood.takeAction(command);
        }
      }
      else if(e.getSource() == bUpgradeCR){
        //System.out.println("Pushed upgradeCR");
        if(model.Deadwood.validateUserCommand("upgrade")){
          ArrayList<String> command = new ArrayList<String>();
          command.add("upgrade");
          command.add("cr");
          command.add(inputRank.getText());
          model.Deadwood.takeAction(command);
        }
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


  /*
  public static void endGameView (int[] scores, int highestScore){

    ArrayList <Integer> winners = new ArrayList <Integer> (scores.length);
    // Just in case of a tie, winners ArrayList with the playerID of all winners
    for (int j = 0; j < scores.length; j++){
      if (scores[j] == highestScore){
        winners.add(j);
      }
    }

    StringBuilder winnerAnnounce = new StringBuilder ();
    if (winners.size() > 1){
      winnerAnnounce.append ("THERE IS A TIE!!\nWinners:\n");
      for (int i = 0; i < winners.size(); i++){
        winnerAnnounce.append ("Player " + winners.get(i) + " scored " + scores[i] + " points!\n");
      }
    }
    // annouce other player's scores
    StringBuilder otherScores = new StringBuilder ("\nOther players:\n");
    for (int j = 0; j < scores.length - winners.size(); j++){
      if (!winners.contains(new Integer (j))){ // only print non-winner's scores
        otherScores.append ("Player " + j + " scored " + scores[j] + " points.\n");
      }
    }

    // add the two announcements together
    StringBuilder toAnnounce = winnerAnnounce.append(otherScores);

    JOptionPane.showMessageDialog(null, toAnnounce.toString(), "Game Over!", JOptionPane.PLAIN_MESSAGE);

    // when player clicks ok, end game
    System.exit(0);
  }*/


}
