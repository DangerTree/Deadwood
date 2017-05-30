// boardView organizes roompanels?
package view;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class BoardView extends JLayeredPane{
  private JLabel boardLabel;
  private JLabel[] playerLabels;
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
    setBounds (boardLabel.getBounds());


    // create room view objects
    makeRooms(bModel);
    makeScenes(bModel);
    //makeR_SViews(bModel);
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

    playerLabels = new JLabel[numPlayers];
    for (int i = 0; i < numPlayers; i++){
      playerLabels [i] = new JLabel();
      playerLabels[i].setIcon (r.getPlayerIcon(i, 1)); // rank 1; color determined by ID
      int[] pLoc = playerRoomLoc.get("Trailers")[i];
      playerLabels[i].setBounds(pLoc[0], pLoc[1], 30, 30); // establish bounds of scene card
      playerLabels[i].setVisible(true);
      add (playerLabels[i], new Integer (4)); // add sceneLabel to JLayeredPane


    }
  }


  // if the player is in whitespace, update it's postion, visually
  /*
  */
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


  private void makeRooms(model.Board bModel) throws Exception{
    RoomView rv;
    File RoomViewLocationsFile = null;
    Scanner scan = null;

    try{
      RoomViewLocationsFile = new File("resources/RoomLocationAndSizes.txt");
      scan = new Scanner(RoomViewLocationsFile);
      while(scan.hasNextLine() != false){
        String name = scan.nextLine();
        String [] location = scan.nextLine().split(" ");
        rv = new RoomView(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), bModel.getRoom(name));
        if(!name.equals("Trailers") && !name.equals("Casting Office")){
          rv.setupShots(shotCounterLoc.get(name));
        }
        this.add(rv, new Integer (1));
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

  private void makeScenes(model.Board bModel) throws Exception{
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
  makeR_SViews: for each room on the board, this method:
    1) creates a new RoomView, and associates it with a model.Room
    2) sets up the shot counters in the room view
    3) creates a new SceneView, and associates it with a model.Scene
    4) adds the RoomView and SceneView to the JLayeredPane
  */
  private void makeR_SViews(model.Board bModel) throws Exception{

    RoomView rv;
    SceneView sv;

    rv = new RoomView (0, 0, 450, 230, bModel.getRoom ("Train Station"));
    rv.setupShots(shotCounterLoc.get("Train Station"));
    sv = new SceneView (15, 65, 125, 215, bModel.getRoom ("Train Station").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (250, 0, 250, 350, bModel.getRoom ("Jail"));
    rv.setupShots(shotCounterLoc.get("Jail"));
    sv = new SceneView (275, 25, 125, 215, bModel.getRoom ("Jail").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (600, 0, 200, 600, bModel.getRoom ("Main Street"));
    rv.setupShots(shotCounterLoc.get("Main Street"));
    sv = new SceneView (965, 25, 125, 215, bModel.getRoom ("Main Street").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (230, 250, 200, 370, bModel.getRoom ("General Store"));
    rv.setupShots(shotCounterLoc.get("General Store"));
    sv = new SceneView (365, 280, 125, 215, bModel.getRoom ("General Store").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (600, 200, 250, 380, bModel.getRoom ("Saloon"));
    rv.setupShots(shotCounterLoc.get("Saloon"));
    sv = new SceneView (625, 275, 125, 215, bModel.getRoom ("Saloon").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (225, 450, 250, 375, bModel.getRoom ("Ranch"));
    rv.setupShots(shotCounterLoc.get("Ranch"));
    sv = new SceneView (245, 470, 125, 215, bModel.getRoom ("Ranch").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (0, 700, 200, 600, bModel.getRoom ("Secret Hideout"));
    rv.setupShots(shotCounterLoc.get("Secret Hideout"));
    sv = new SceneView (20, 725, 125, 215, bModel.getRoom ("Secret Hideout").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (600, 450, 200, 370, bModel.getRoom ("Bank"));
    rv.setupShots(shotCounterLoc.get("Bank"));
    sv = new SceneView (618, 470, 125, 215, bModel.getRoom ("Bank").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (600, 650, 250, 340, bModel.getRoom ("Church"));
    rv.setupShots(shotCounterLoc.get("Church"));
    sv = new SceneView (618, 730, 125, 215, bModel.getRoom ("Church").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (970, 450, 450, 230, bModel.getRoom ("Hotel"));
    rv.setupShots(shotCounterLoc.get("Hotel"));
    sv = new SceneView (965, 735, 125, 215, bModel.getRoom ("Hotel").getScene());
    this.add(sv, new Integer (2));
    this.add(rv, new Integer (1));

    rv = new RoomView (980, 250, 200, 220, bModel.getRoom ("Trailers"));
    this.add(rv, new Integer (1));

    rv = new RoomView (0, 450, 230, 225, bModel.getRoom ("Casting Office"));
    this.add(rv, new Integer (1));

    /*
    SceneView sv;

    sv = new SceneView (15, 65, 125, 215, bModel.getRoom ("Train Station").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (275, 25, 125, 215, bModel.getRoom ("Jail").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (965, 25, 125, 215, bModel.getRoom ("Main Street").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (365, 280, 125, 215, bModel.getRoom ("General Store").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (625, 275, 125, 215, bModel.getRoom ("Saloon").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (245, 470, 125, 215, bModel.getRoom ("Ranch").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (20, 725, 125, 215, bModel.getRoom ("Secret Hideout").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (618, 470, 125, 215, bModel.getRoom ("Bank").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (618, 730, 125, 215, bModel.getRoom ("Church").getScene());
    this.add(sv, new Integer (2));

    sv = new SceneView (965, 735, 125, 215, bModel.getRoom ("Hotel").getScene());
    this.add(sv, new Integer (2));
    */

    /*
    rv = new RoomView (0, 0, 450, 230, bModel.getRoom ("Train Station"));
    rv.setupShots(shotCounterLoc.get("Train Station"));
    rv.addSceneView (15, 65, 125, 215, bModel.getRoom ("Train Station").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (250, 0, 250, 350, bModel.getRoom ("Jail"));
    rv.setupShots(shotCounterLoc.get("Jail"));
    rv.addSceneView (275, 25, 125, 215, bModel.getRoom ("Jail").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (600, 0, 200, 600, bModel.getRoom ("Main Street"));
    rv.setupShots(shotCounterLoc.get("Main Street"));
    rv.addSceneView (965, 25, 125, 215, bModel.getRoom ("Main Street").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (230, 250, 200, 370, bModel.getRoom ("General Store"));
    rv.setupShots(shotCounterLoc.get("General Store"));
    rv.addSceneView (365, 280, 125, 215, bModel.getRoom ("General Store").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (600, 200, 250, 380, bModel.getRoom ("Saloon"));
    rv.setupShots(shotCounterLoc.get("Saloon"));
    rv.addSceneView (625, 275, 125, 215, bModel.getRoom ("Saloon").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (225, 450, 250, 375, bModel.getRoom ("Ranch"));
    rv.setupShots(shotCounterLoc.get("Ranch"));
    rv.addSceneView (245, 470, 125, 215, bModel.getRoom ("Ranch").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (0, 700, 200, 600, bModel.getRoom ("Secret Hideout"));
    rv.setupShots(shotCounterLoc.get("Secret Hideout"));
    rv.addSceneView (20, 725, 125, 215, bModel.getRoom ("Secret Hideout").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (600, 450, 200, 370, bModel.getRoom ("Bank"));
    rv.setupShots(shotCounterLoc.get("Bank"));
    rv.addSceneView (618, 470, 125, 215, bModel.getRoom ("Bank").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (600, 650, 250, 340, bModel.getRoom ("Church"));
    rv.setupShots(shotCounterLoc.get("Church"));
    rv.addSceneView (618, 730, 125, 215, bModel.getRoom ("Church").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (970, 450, 450, 230, bModel.getRoom ("Hotel"));
    rv.setupShots(shotCounterLoc.get("Hotel"));
    rv.addSceneView (965, 735, 125, 215, bModel.getRoom ("Hotel").getScene());
    this.add(rv, new Integer (2));

    rv = new RoomView (980, 250, 200, 220, bModel.getRoom ("Trailers"));
    this.add(rv, new Integer (2));

    rv = new RoomView (0, 450, 230, 225, bModel.getRoom ("Casting Office"));
    this.add(rv, new Integer (2));
    */
  }

}
