/*
The gameboard class for Dmveadwood game
*/

public class Board{

    private int daysLeft;
    private int scenesLeft;

    private static Board boardObj = new Board();

    public static Board getBoard(int dayNum){
        boardObj.daysLeft = dayNum;
        return boardObj;
    }

    // creates 12 room objects, standard for Deadwood
    public void setupRooms(){

    }

    // moves players to Trailers, deals new scene cards to rooms, and replaces shot ctrs
    public void endDay(){

    }

    public void endScene(){

    }

    public int getDaysLeft(){
      return daysLeft;
    }

    public int getScenesLeft(){
      return scenesLeft;
    }

    public void decSceneNum(){

    }

}
