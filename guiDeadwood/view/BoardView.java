/*
boardView does the following:
  1) creates RoomViews (layer 1), SceneViews (layer 2), and RoleViews (layer 4) for off-card roles
  2) listens to model.Player objs and updates their position (visually) on board
  3)
*/

package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class BoardView extends JLayeredPane implements model.Player.Listener{
  private JLabel boardLabel;
  private JLabel[] playerLabels;
  private HashMap<String, model.Role> roleMap;
  private HashMap <String, int[][]> playerRoomLoc; // holds String "Room Name", [[player1 x_loc, player1 y_loc], [player1 x_loc, player1 y_loc]...]
  private HashMap <String, int[][]> shotCounterLoc; // <Room name, [ [x_loc1, y_loc1], [x_loc2, y_loc2] ...] (of shot counter image locations)

  public BoardView (model.Board bModel, int numPlayers) throws Exception {

    playerRoomLoc = new HashMap <String, int[][]>();
    shotCounterLoc = new HashMap <String, int[][]>();
    initPlayerRoomLoc();
    initShotCounterLoc();
    initPlayerLabels(numPlayers);

    ResourcesDW r = ResourcesDW.getInstance();
    ImageIcon backgroundImg = r.getBG();

    boardLabel = new JLabel (backgroundImg);
    boardLabel.setBounds (0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
    add(boardLabel, new Integer (0)); // add the board image as the first layer
    this.setBounds (boardLabel.getBounds());

    // create room, scene, and off-card role view objects
    makeRoomViews(bModel);
    makeSceneViews(bModel);
    makeOffCardRoleViews(bModel);

    // subscribe to all players
    Queue<model.Player> thePQ =  model.Deadwood.getPlayerQ();

    for (model.Player p: thePQ){
      p.subscribe(this);
    }
    model.Deadwood.getActivePlayer().subscribe(this);
  }


  /* initPlayerRoomLoc reads in the coordiates in each room where a player, ...
  ... given their playerID, will move to when entering that room.
  POST-CONDITION: hashmap playerRoomLoc is populated
  */
  private void initPlayerRoomLoc(){

    File PlayerLocationsFile = null;
    Scanner scan = null;

    try{
      PlayerLocationsFile = new File ("resources/PlayerLocations.txt");
      scan = new Scanner (PlayerLocationsFile);
      while(scan.hasNextLine() != false){
        String roomName = scan.nextLine();
        int [][] newLocations = new int[8][2];
        for(int i = 0; i < 8; i++){
          String [] readLine = scan.nextLine().split(" ");
          newLocations[i][0] = Integer.parseInt(readLine[0]);
          newLocations[i][1] = Integer.parseInt(readLine[1]);
        }

        playerRoomLoc.put(roomName, newLocations);
      }
    }
    catch(FileNotFoundException e){
      System.out.println("PlayerLocations.txt file not found.");
      System.exit(1);
    }
    catch(NumberFormatException e){
      System.out.println("PlayerLocations.txt file formatted incorrectly.");
      System.exit(1);
    }

  }


  /* initShotCounterLoc reads in the coordiates in each room where its ...
  ... shot counters are positioned.
  POST-CONDITION: hashmap shotCounterLoc is populated
  */
  private void initShotCounterLoc(){

    File shotCounterLocationsFile = null;
    Scanner scan = null;

    try{
      shotCounterLocationsFile = new File ("resources/ShotCounterLocations.txt");
      scan = new Scanner (shotCounterLocationsFile);
      while(scan.hasNextLine() != false){
        String [] firstLine = scan.nextLine().split("_");
        String roomName = firstLine[0];
        int shotNum = Integer.parseInt(firstLine[1]);
        int[][] newLocations = new int[shotNum][2];
        for(int i = 0; i < shotNum; i ++){
          String [] readLine = scan.nextLine().split(" ");
          newLocations[i][0] = Integer.parseInt(readLine[0]);
          newLocations[i][1] = Integer.parseInt(readLine[1]);
        }
        shotCounterLoc.put(roomName, newLocations);
      }
    }
    catch(FileNotFoundException e){
      System.out.println("ShotCounterLocations.txt file not found.");
      System.exit(1);
    }
    catch(NumberFormatException e){
      System.out.println("ShotCounterLocations.txt file formatted incorrectly.");
      System.exit(1);
    }
  }


  /*
  initPlayerLabels initializes the playerLabels list by creating a JLabel ...
  ... for each player and adding it to the JLayeredPane.
  POST-CONDITION: playerLabels array is populated with JLabels with the players'...
  ... coorresponding mover die image. JLabels are located in "Trailers".
  */
  private void initPlayerLabels (int numPlayers){

    ResourcesDW r = ResourcesDW.getInstance();
    int startingRank = 1;
    if (numPlayers == 7 || numPlayers == 8){
      startingRank = 2;
    }
    playerLabels = new JLabel[numPlayers];
    for (int i = 0; i < numPlayers; i++){
      playerLabels [i] = new JLabel();
      playerLabels[i].setIcon (r.getPlayerIcon(i, startingRank)); // rank 1; color determined by ID
      int[] pLoc = playerRoomLoc.get("Trailers")[i];
      playerLabels[i].setBounds(pLoc[0], pLoc[1], 30, 30); // establish bounds of scene card
      playerLabels[i].setVisible(true);
      add (playerLabels[i], new Integer (4)); // add sceneLabel to JLayeredPane
    }
  }


  // if the player is in whitespace, update it's postion visually
  public void changed (model.Player p){
    // depict where player is at
    if (p.getRole() == null){
      int pID = p.getPlayerID();
      // MOVE THE PLAYER ICON
      ResourcesDW r = ResourcesDW.getInstance();
      // update the player's mover dice icon
      playerLabels[pID].setIcon (r.getPlayerIcon (pID, p.getRank()));
      // update its location
      int[] newLoc = playerRoomLoc.get(p.getRoom().getRName())[pID];
      playerLabels[pID].setLocation(newLoc[0], newLoc[1]);
      playerLabels[p.getPlayerID()].setVisible(true); // make the jLabel visible
    }
    else { // if the player is not in the whitespace of a room
      playerLabels[p.getPlayerID()].setVisible(false);
    }
  }


  /*
  makeRoomViews: for each room in RoomLocationAndSizes.txt this method:
    1) creates a new RoomView, and associates it with a model.Room
    2) sets up the shot counters in the room view
    3) adds the RoomView to the JLayeredPane
  */
  private void makeRoomViews(model.Board bModel) throws Exception{
    RoomView rv;
    File RoomViewLocationsFile = null;
    Scanner scan = null;

    try{
      RoomViewLocationsFile = new File("resources/RoomLocationAndSizes.txt");
      scan = new Scanner(RoomViewLocationsFile);
      while(scan.hasNextLine() != false){
        String name = scan.nextLine();
        String [] location = scan.nextLine().split(" ");
        //rv = new RoomView(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), bModel.getRoom(name));
        if(!name.equals("Trailers") && !name.equals("Casting Office")){
          rv = new RoomView(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), bModel.getRoom(name));
          rv.setupShots(shotCounterLoc.get(name));
          this.add(rv, new Integer (1));
        }
        //this.add(rv, new Integer (1));
      }
    }
    catch(FileNotFoundException e){
      System.out.println("RoomLocationAndSizes.txt file not found.");
      System.exit(1);
    }
    catch(NumberFormatException e){
      System.out.println("RoomLocationAndSizes.txt file formatted incorrectly.");
      System.exit(1);
    }
  }


  /*
  makeSceneViews: for every scene read from SceneLocationAndSizes.txt:
    1) create a new SceneView, and associates it with a model.Scene
      1b) the SceneView constructor will make the appropriate # of on-card RoleViews
    2) adds the SceneView to the JLayeredPane
  */
  private void makeSceneViews(model.Board bModel) throws Exception{
    SceneView sv;
    File SceneViewLocationsFile = null;
    Scanner scan = null;

    try{
      SceneViewLocationsFile = new File("resources/SceneLocationAndSizes.txt");
      scan = new Scanner(SceneViewLocationsFile);
      while(scan.hasNextLine() != false){
        String name = scan.nextLine();
        String [] location = scan.nextLine().split(" ");
        sv = new SceneView(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), bModel.getRoom(name).getScene());
        this.add(sv, new Integer (2));
      }
    }
    catch(FileNotFoundException e){
      System.out.println("RoomLocationAndSizes.txt file not found.");
      System.exit(1);
    }
    catch(NumberFormatException e){
      System.out.println("RoomLocationAndSizes.txt file formatted incorrectly.");
      System.exit(1);
    }
  }


  /*
  makeRoomViews: for every off card role read from RoleLocationAndSizes.txt:
    1) create a new RoleView, and associate it with a model.Role
    2) adds the RoleView to the JLayeredPane
  */
  private void makeOffCardRoleViews(model.Board bModel) throws Exception{


    RoleView rl_v;
    File RoleLocationsFile = null;
    Scanner scan = null;
    //HashMap<String, model.Role> roleMap = model.Board.getRoleMap();

    try{
      RoleLocationsFile = new File("resources/RoleLocationAndSizes.txt");
      scan = new Scanner(RoleLocationsFile);
      while(scan.hasNextLine() != false){
        String name = scan.nextLine();
        String [] location = scan.nextLine().split(" ");
        rl_v = new RoleView(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), model.Board.getRoleMap().get(name));
        this.add(rl_v, new Integer(4));
      }
    }
    catch(FileNotFoundException e){
      System.out.println("RoleLocationAndSizes.txt file not found.");
      System.exit(1);
    }
    catch(NumberFormatException e){
      System.out.println("RoleLocationAndSizes.txt file formatted incorrectly.");
      System.exit(1);
    }
  }


  public static void endGameView (String announcement){

    JOptionPane.showMessageDialog(null, announcement, "Game Over!", JOptionPane.PLAIN_MESSAGE);
    // when player clicks ok, end game
    System.exit(0);
    
    /*
    ArrayList <Integer> winners = new ArrayList <Integer> (scores.length);
    // Just in case of a tie, winners ArrayList with the playerID of all winners
    for (int j = 0; j < scores.length; j++){
      if (scores[j] == highestScore){
        winners.add(j);
      }
    }

    // create a string with the announcement for winning players
    StringBuilder winnerAnnounce = new StringBuilder ();
    if (winners.size() > 1){
      winnerAnnounce.append ("THERE IS A TIE!!\n");
    }
    winnerAnnounce.append ("Winners:\n");
    for (int i = 0; i < winners.size(); i++){
      winnerAnnounce.append ("Player " + winners.get(i) + " scored " + scores[i] + " points!\n");
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
    */
  }


}
