package view;

import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.util.Queue;


public class PlayerStatusBoxView extends JLayeredPane implements model.Player.Listener{

  private JLabel titleLabel;
  private JLabel activePlayerLabel;
  private JLabel rankLabel;
  private JLabel moneyLabel;
  private JLabel creditLabel;
  private JLabel practiceLabel;

  private JTextArea playerNumInfo;
  private JTextArea rankInfo;
  private JTextArea moneyInfo;
  private JTextArea creditInfo;
  private JTextArea practiceInfo;


  public PlayerStatusBoxView() throws Exception{
    // create labels which will not change
    createLabels();
    // initialize status box (only once)
    initStats();

    this.setBounds (1200, 0, 300, 900);

    System.out.println ("in PlayerStatusBoxView constructor");

    Queue<model.Player> thePQ =  model.Deadwood.getPlayerQ();

    // subscribe to all players
    for (model.Player p: thePQ){
      p.subscribe(this);
    }
    model.Deadwood.getActivePlayer().subscribe(this);

  }


  public void changed (model.Player p){

    playerNumInfo.setText(model.Deadwood.getActivePlayer().getPlayerID() + "");
    rankInfo.setText(model.Deadwood.getActivePlayer().getRank() + "");
    moneyInfo.setText(model.Deadwood.getActivePlayer().getMoneyCnt() + "");
    creditInfo.setText(model.Deadwood.getActivePlayer().getCreditCnt() + "");
    practiceInfo.setText(model.Deadwood.getActivePlayer().getPracticeCnt() + "");

  }


  private void createLabels(){

    titleLabel = new JLabel ("Active Player Stats");
    titleLabel.setBounds(50, 250, 140, 30);


    activePlayerLabel = new JLabel("Player #: ");
    activePlayerLabel.setBounds(45, 280, 150, 15);


    rankLabel = new JLabel ("Rank:");
    rankLabel.setBounds(70, 300, 80, 15);


    moneyLabel = new JLabel("Money: ");
    moneyLabel.setBounds(60, 320, 80, 15);


    creditLabel = new JLabel("Credits: ");
    creditLabel.setBounds(55, 340, 80, 15);


    practiceLabel = new JLabel("Practice: ");
    practiceLabel.setBounds(50, 360, 150, 15);

    add(titleLabel, new Integer(2));
    add(activePlayerLabel, new Integer(2));
    add(practiceLabel, new Integer(2));
    add(rankLabel, new Integer(2));
    add(moneyLabel, new Integer(2));
    add(creditLabel, new Integer(2));
  }


  private void initStats(){
    playerNumInfo = new JTextArea ();
    playerNumInfo.append(model.Deadwood.getActivePlayer().getPlayerID() + "");


    playerNumInfo.setWrapStyleWord(true);
    playerNumInfo.setLineWrap(true);
    playerNumInfo.setBounds(120, 280, 30, 15);
    playerNumInfo.setEditable(false);
    add(playerNumInfo, new Integer (2));

    rankInfo = new JTextArea ();
    rankInfo.append(model.Deadwood.getActivePlayer().getRank() + "");


    rankInfo.setWrapStyleWord(true);
    rankInfo.setLineWrap(true);
    rankInfo.setBounds(120, 300, 30, 15);
    rankInfo.setEditable(false);
    add(rankInfo, new Integer (2));

    moneyInfo = new JTextArea();
    moneyInfo.append(model.Deadwood.getActivePlayer().getMoneyCnt() + "");

    moneyInfo.setWrapStyleWord(true);
    moneyInfo.setLineWrap(true);
    moneyInfo.setBounds(120, 320, 30, 15);
    moneyInfo.setEditable(false);
    add(moneyInfo, new Integer(2));

    creditInfo = new JTextArea();
    creditInfo.append(model.Deadwood.getActivePlayer().getCreditCnt() + "");

    creditInfo.setWrapStyleWord(true);
    creditInfo.setLineWrap(true);
    creditInfo.setBounds(120, 340, 30, 15);
    creditInfo.setEditable(false);
    add(creditInfo, new Integer(2));

    practiceInfo = new JTextArea();
    practiceInfo.append(model.Deadwood.getActivePlayer().getPracticeCnt() + "");

    practiceInfo.setWrapStyleWord(true);
    practiceInfo.setLineWrap(true);
    practiceInfo.setBounds(120, 360, 30, 15);
    practiceInfo.setEditable(false);
    add(practiceInfo, new Integer(2));

  }

}
