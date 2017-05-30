package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.lang.NullPointerException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.HashMap;
import java.lang.String;
import java.util.Map;

// This singleton class loads the 7-segment images once and keeps track of them
// throughout the program's execution.
public class ResourcesDW {
  // This array actually holds the images, which are indexed by the displayed
  // number.  That is, sceneIcons[1] is the 7-segment number 1.
  private static Map<String, ImageIcon> sceneIcons = new HashMap <String, ImageIcon>();
  private static Map<int[], ImageIcon> playerIcons = new HashMap <int[], ImageIcon>();; // where int[] = [player id, rank], and ImageIcon is the coorresponding mover die
  private static ImageIcon background, backOfCard, shotIcon;
  static ResourcesDW instance = new ResourcesDW();

  // This constructor creates the only instance of the class and reads in the
  // images from the files.  This is hard coded to look for the resources in a
  // specific directory.  This could be improved in two ways.  the path could be
  // more flexible and the images could be loaded on demand.
  private ResourcesDW() {
    //sceneIcons = new HashMap <String, ImageIcon>();
    //playerIcons = new HashMap <int[], ImageIcon>();

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
      File diceImgFolder = new File ("./resources/dice");
      File[] listOfDImages = diceImgFolder.listFiles();
      for (int i = 0; i < listOfDImages.length; i++){
        String fileName = listOfDImages[i].getName();
        if (listOfDImages[i].isFile() && fileName.endsWith(".png")){
          ImageIcon rawDieImg = new ImageIcon (ImageIO.read(listOfDImages[i]));
          ImageIcon scaledImg = new ImageIcon(rawDieImg.getImage().getScaledInstance(30, 30, 1));
          //ImageIcon scaledImg = new ImageIcon (ImageIO.read(listOfDImages[i]).getScaledInstance(215, 125, 1));
          int dieID = -1;
          char firstChar = fileName.charAt(0);
          if (firstChar=='b'){ dieID = 0;}
          else if (firstChar=='c'){ dieID = 1;}
          else if (firstChar=='g'){ dieID = 2;}
          else if (firstChar=='o'){ dieID = 3;}
          else if (firstChar=='p'){ dieID = 4;}
          else if (firstChar=='r'){ dieID = 5;}
          else if (firstChar=='v'){ dieID = 6;}
          else if (firstChar=='y'){ dieID = 7;}
          int dieRank = Character.getNumericValue(fileName.charAt(1));
          System.out.println ("dieID: " + dieID);
          System.out.println ("dieRank: " + dieRank);
          System.out.println (scaledImg);
          int[] key = {dieID, dieRank};
          playerIcons.put (key, scaledImg);
        }
      }
      /************ LOAD BACKGROUND AND BACK OR SCENE CARD IMGS **********/

      background = new ImageIcon (ImageIO.read(new File("./resources/fullBoard.jpg")));

      ImageIcon cardBack = new ImageIcon (ImageIO.read(new File("./resources/cardBack.png")).getScaledInstance(215, 125, 1));
      //backOfCard = new ImageIcon(cardBack.getImage().getScaledInstance(215, 125, 1));
      shotIcon = new ImageIcon (ImageIO.read(new File("./resources/shot.png")));

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


  public static ImageIcon getShotIcon (){
    return shotIcon;
  }


  public static ImageIcon getPlayerIcon (int playerID, int rank){
    System.out.println ("in getPlayerIcon PLAYERID: " + playerID);
    System.out.println ("in getPlayerIcon RANK: " + rank);
    int[] key = {playerID, rank};
    System.out.println ("playerIcons (Hashmap) size: " + playerIcons.size());
    System.out.println ("key: " + key);
    //System.out.println ("Keyset for playerIcons (Hashmap): " + playerIcons.keySet().toArray();
    ImageIcon toRet = playerIcons.get(key);
    System.out.println ("toRet: " + toRet);
    if (toRet == null){
      System.out.println ("Player mover die icon for [playerID, rank] = " + key[0] + " " + key[1] + " not found. Exiting game.");
      System.exit(1);
    }
    return toRet;
  }


  public static ImageIcon getSceneIcon(String sceneName, int sceneNum) {
    String assembledKey = sceneName + " " + sceneNum;
    ImageIcon toRet = sceneIcons.get(assembledKey);
    if (toRet == null){
      System.out.println ("Image for scene: " + assembledKey + " not found. Exiting game.");
      System.exit(1);
    }
    return toRet;
  }

  public static ImageIcon getBG(){
    System.out.println (background);
    return background;
  }

  public static ImageIcon getBackOfCard(){
    return backOfCard;
  }

  public static ResourcesDW getInstance() {
    return instance;
  }

}
