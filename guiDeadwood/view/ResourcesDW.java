package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.lang.NullPointerException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.HashMap;

// This singleton class loads the 7-segment images once and keeps track of them
// throughout the program's execution.
public class ResourcesDW {
  // This array actually holds the images, which are indexed by the displayed
  // number.  That is, sceneIcons[1] is the 7-segment number 1.
  private HashMap<String, ImageIcon> sceneIcons;
  private ImageIcon background, backOfCard;
  static ResourcesDW instance;

  // This constructor creates the only instance of the class and reads in the
  // images from the files.  This is hard coded to look for the resources in a
  // specific directory.  This could be improved in two ways.  the path could be
  // more flexible and the images could be loaded on demand.
  private ResourcesDW() {
    HashMap <String, ImageIcon> sceneIcons = new HashMap <String, ImageIcon>();

    File imgFolder = new File ("./resources/scenes");
    File[] listOfImages = imgFolder.listFiles();

    // for every file in the imgFile folder, load it into the HashMap
    try{
      for (int i = 0; i < listOfImages.length; i++){
        if (listOfImages[i].isFile() && listOfImages[i].getName().endsWith(".png")){
          String key = listOfImages[i].getName().substring(0, listOfImages[i].getName().length()-4);
          ImageIcon unscaledImg = new ImageIcon (ImageIO.read(listOfImages[i]));
          ImageIcon img = new ImageIcon(unscaledImg.getImage().getScaledInstance(215, 125, 1));
          sceneIcons.put (key, img);
        }
      }


      background = new ImageIcon (ImageIO.read(new File("./resources/fullBoard.jpg")));

      ImageIcon cardBack = new ImageIcon (ImageIO.read(new File("./resources/cardBack.png")));
      backOfCard = new ImageIcon(cardBack.getImage().getScaledInstance(215, 125, 1));

    } catch (IOException e) {
      System.out.println("Image resource not found. Exiting program.");
      e.printStackTrace();
      System.exit(1);
    }
    catch (NullPointerException e){
      System.out.println ("Null resource file.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public ImageIcon getSceneIcon(String sceneName, int sceneNum) {
    String assembledKey = sceneName + " " + sceneNum;
    ImageIcon toRet = sceneIcons.get(assembledKey);
    if (toRet == null){
      System.out.println ("Image for scene: " + assembledKey + " not found. Exiting game.");
      System.exit(1);
    }
    return toRet;
  }

  public ImageIcon getBG(){
    System.out.println (background);
    return background;
  }

  public ImageIcon getBackOfCard(){
    return backOfCard;
  }

  public static ResourcesDW getInstance() {
    if (instance == null)
      instance = new ResourcesDW();
    return instance;
  }

}
