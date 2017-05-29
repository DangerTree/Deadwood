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
  private HashMap<int[], ImageIcon> playerIcons; // where int[] = [player id, rank], and ImageIcon is the coorresponding mover die
  private ImageIcon background, backOfCard;
  static ResourcesDW instance;

  // This constructor creates the only instance of the class and reads in the
  // images from the files.  This is hard coded to look for the resources in a
  // specific directory.  This could be improved in two ways.  the path could be
  // more flexible and the images could be loaded on demand.
  private ResourcesDW() {
    HashMap <String, ImageIcon> sceneIcons = new HashMap <String, ImageIcon>();

    // for every file in the imgFile folder, load it into the HashMap
    try{
      /************ LOAD SCENE CARD IMAGES *****************************/
      File sceneImgFolder = new File ("./resources/scenes");
      File[] listOfSImages = sceneImgFolder.listFiles();

      for (int i = 0; i < listOfSImages.length; i++){
        if (listOfSImages[i].isFile() && listOfSImages[i].getName().endsWith(".png")){
          String key = listOfSImages[i].getName().substring(0, listOfSImages[i].getName().length()-4);
          ImageIcon unscaledImg = new ImageIcon (ImageIO.read(listOfSImages[i]));
          ImageIcon img = new ImageIcon(unscaledImg.getImage().getScaledInstance(215, 125, 1));
          sceneIcons.put (key, img);
        }
      }
      /************ LOAD PLAYER MOVER DIE IMAGES ************************/
      /*File diceImgFolder = new File ("./resources/dice");
      File[] listOfDImages = diceImgFolder.listFiles();
      //[player id, rank]
      for (int i = 0; i < listOfDImages.length; i++){
        if (listOfDImages[i].isFile() && listOfDImages[i].getName().endsWith(".png")){

          String key = listOfDImages[i].getName().substring(0, listOfDImages[i].getName().length()-4);
          ImageIcon unscaledImg = new ImageIcon (ImageIO.read(listOfDImages[i]));
          ImageIcon img = new ImageIcon(unscaledImg.getImage().getScaledInstance(215, 125, 1));
          sceneIcons.put (key, img);
        }
      }*/
      /************ LOAD BACKGROUND AND BACK OR SCENE CARD IMGS **********/

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


  public ImageIcon getPlayerIcon (int playerID, int rank){
    int[] key = {playerID, rank};
    ImageIcon toRet = playerIcons.get(key);
    if (toRet == null){
      System.out.println ("Player mover die icon for [playerID, rank] = " + key + " not found. Exiting game.");
      System.exit(1);
    }
    return toRet;
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
