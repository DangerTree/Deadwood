package view;

import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.util.Queue;


public class PlayerStatusBoxView extends JLayeredPane implements model.Player.Listener{

  private JLabel activePlayerLabel;
  private JLabel rankLabel;
  private JLabel moneyLabel;
  private JLabel creditLabel;
  private JLabel practiceLabel;

  private JTextArea playerNum;
  private JTextArea rankInfo;
  private JTextArea moneyInfo;
  private JTextArea creditInfo;
  private JTextArea practiceInfo;


  public PlayerStatusBoxView() throws Exception{
    // create labels which will not change
    createLabels();
    // initialize status box (only once)
    initStats();

    Queue<model.Player> thePQ =  model.Deadwood.getPlayerQ();

    // subscribe to all players
    for (model.Player p: thePQ){
      p.subscribe(this);
    }
    model.Deadwood.getActivePlayer().subscribe(this);

  }


  public void changed (model.Player p){

    rankInfo.setText(model.Deadwood.getActivePlayer().getMoneyCnt() + "");
    moneyInfo.setText(model.Deadwood.getActivePlayer().getMoneyCnt() + "");
    creditInfo.setText(model.Deadwood.getActivePlayer().getCreditCnt() + "");
    practiceInfo.setText(model.Deadwood.getActivePlayer().getPracticeCnt() + "");

  }


  private void createLabels(){

    activePlayerLabel = new JLabel ("Active Player Stats");
    activePlayerLabel.setBounds(1250, 250, 140, 30);
    add(activePlayerLabel, new Integer(2));

    rankLabel = new JLabel ("Rank:");
    rankLabel.setBounds(1270, 280, 80, 15);
    add(rankLabel, new Integer(2));

    moneyLabel = new JLabel("Money: ");
    moneyLabel.setBounds(1260, 300, 80, 15);
    add(moneyLabel, new Integer(2));

    creditLabel = new JLabel("Credits: ");
    creditLabel.setBounds(1255, 320, 80, 15);
    add(creditLabel, new Integer(2));

    practiceLabel = new JLabel("Practice: ");
    practiceLabel.setBounds(1250, 340, 150, 15);
    add(practiceLabel, new Integer(2));
  }


  private void initStats(){
    rankInfo = new JTextArea ();
    rankInfo.append(model.Deadwood.getActivePlayer().getRank() + "");


    rankInfo.setWrapStyleWord(true);
    rankInfo.setLineWrap(true);
    rankInfo.setBounds(1320, 280, 30, 15);
    add(rankInfo, new Integer (2));

    moneyInfo = new JTextArea();
    moneyInfo.append(model.Deadwood.getActivePlayer().getMoneyCnt() + "");

    moneyInfo.setWrapStyleWord(true);
    moneyInfo.setLineWrap(true);
    moneyInfo.setBounds(1320, 300, 30, 15);
    add(moneyInfo, new Integer(2));

    creditInfo = new JTextArea();
    creditInfo.append(model.Deadwood.getActivePlayer().getCreditCnt() + "");

    creditInfo.setWrapStyleWord(true);
    creditInfo.setLineWrap(true);
    creditInfo.setBounds(1320, 320, 30, 15);
    add(creditInfo, new Integer(2));

    practiceInfo = new JTextArea();
    practiceInfo.append(model.Deadwood.getActivePlayer().getPracticeCnt() + "");

    practiceInfo.setWrapStyleWord(true);
    practiceInfo.setLineWrap(true);
    practiceInfo.setBounds(1320, 340, 30, 15);
    add(practiceInfo, new Integer(2));

  }

}
