package controller;

import javax.swing.JLayeredPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class BoardController extends JLayeredPane{

  //private model.Room rmModel;

  public BoardController (model.Board bModel) throws Exception{
    setBounds (0, 0, 1200, 900);

    // create new room controllers
    RoomController rc;
    SceneController sc;

    makeRoomControllers(bModel);
    makeSceneControllers(bModel);

  }


  private void makeRoomControllers(model.Board bModel) throws Exception{
    RoomController rc;
    File RoomViewLocationsFile = null;
    Scanner scan = null;

    try{
      RoomViewLocationsFile = new File("resources/RoomLocationAndSizes.txt");
      scan = new Scanner(RoomViewLocationsFile);
      while(scan.hasNextLine() != false){
        String name = scan.nextLine();
        String [] location = scan.nextLine().split(" ");
        rc = new RoomController(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), bModel.getRoom(name));
        this.add(rc, new Integer (2));
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


  private void makeSceneControllers(model.Board bModel) throws Exception{
    SceneController sc;
    File SceneViewLocationsFile = null;
    Scanner scan = null;

    try{
      SceneViewLocationsFile = new File("resources/SceneLocationAndSizes.txt");
      scan = new Scanner(SceneViewLocationsFile);
      while(scan.hasNextLine() != false){
        String name = scan.nextLine();
        String [] location = scan.nextLine().split(" ");
        sc = new SceneController(Integer.parseInt(location[0]), Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3]), bModel.getRoom(name).getScene());
        this.add(sc, new Integer (3));
        model.Role[] roleList = bModel.getRoom(name).getScene().getSRoleList();
        //makeRoleControllers (roleList);

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


  // grab the role info from the scene: for every role in the scene, make a role controller and associate it with a model.Role
  private void makeRoleControllers (model.Role[] roleList){

    RoleController rl_c;

    switch (roleList.length){
      case 1: // make a role view positioned on the card for only 1 role
        rl_c = new RoleController (87, 51, 47, 47, roleList[0]);
        this.add (rl_c, new Integer (4));
        break;
      case 2:
        rl_c = new RoleController (122, 51, 47, 47, roleList[0]);
        this.add (rl_c, new Integer (4));
        rl_c = new RoleController (56, 51, 47, 47, roleList[1]);
        this.add (rl_c, new Integer (4));
        break;
      case 3:
        rl_c = new RoleController (152, 51, 47, 47, roleList[0]);
        this.add (rl_c, new Integer (4));
        rl_c = new RoleController (86, 51, 47, 47, roleList[1]);
        this.add (rl_c, new Integer (4));
        rl_c = new RoleController (21, 51, 47, 47, roleList[2]);
        this.add (rl_c, new Integer (4));
        break;
    }
  }

}
