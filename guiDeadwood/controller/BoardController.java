package controller;

import javax.swing.JLayeredPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BoardController extends JLayeredPane{

  //private model.Room rmModel;

  public BoardController (model.Board bModel){
    setBounds (0, 0, 1200, 900);

    // create new room controllers
    RoomController rc;
    SceneController sc;

    rc = new RoomController (0, 0, 450, 230, bModel.getRoom ("Train Station"));
    sc = new SceneController (15, 65, 125, 215, bModel.getRoom ("Train Station").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (250, 0, 250, 350, bModel.getRoom ("Jail"));
    sc = new SceneController (275, 25, 125, 215, bModel.getRoom ("Jail").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (600, 0, 200, 600, bModel.getRoom ("Main Street"));
    sc = new SceneController (965, 25, 125, 215, bModel.getRoom ("Main Street").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (230, 250, 200, 370, bModel.getRoom ("General Store"));
    sc = new SceneController (365, 280, 125, 215, bModel.getRoom ("General Store").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (600, 200, 250, 380, bModel.getRoom ("Saloon"));
    sc = new SceneController (625, 275, 125, 215, bModel.getRoom ("Saloon").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (225, 450, 250, 375, bModel.getRoom ("Ranch"));
    sc = new SceneController (245, 470, 125, 215, bModel.getRoom ("Ranch").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (0, 700, 200, 600, bModel.getRoom ("Secret Hideout"));
    sc = new SceneController (20, 725, 125, 215, bModel.getRoom ("Secret Hideout").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (600, 450, 200, 370, bModel.getRoom ("Bank"));
    sc = new SceneController (618, 470, 125, 215, bModel.getRoom ("Bank").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (600, 650, 250, 340, bModel.getRoom ("Church"));
    sc = new SceneController (618, 730, 125, 215, bModel.getRoom ("Church").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (970, 450, 450, 230, bModel.getRoom ("Hotel"));
    sc = new SceneController (965, 735, 125, 215, bModel.getRoom ("Hotel").getScene());
    this.add(rc, new Integer (3));
    this.add (sc, new Integer (4));

    rc = new RoomController (980, 250, 200, 220, bModel.getRoom ("Trailers"));
    this.add(rc, new Integer (3));

    rc = new RoomController (0, 450, 230, 225, bModel.getRoom ("Casting Office"));
    this.add(rc, new Integer (3));

  }

}
