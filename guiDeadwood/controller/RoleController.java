package controller;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class RoleController extends JPanel{

  private model.Scene rlModel;

  public RoleController (int x, int y, int h, int w, model.Scene role){
    rlModel = role;
    setBounds (x, y, w, h);
    setOpaque (false);
  }
}
