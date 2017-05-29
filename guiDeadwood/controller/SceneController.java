package controller;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class SceneController extends JPanel{

  private model.Scene sModel;

  public SceneController (int x, int y, int h, int w, model.Scene scene){
    sModel = scene;
    setBounds (x, y, w, h);
    setOpaque (false);
  }


  public void signalWrapping (){
  }


  // if scene is flipped, make role controllers
  /*public void flipSceneCard (model.Scene s){

    RoleController rlc;
    switch (scene.getSRoleListSize()){
      if there is one role, position it accordingly
      case 1:

    }


  }*/



}
