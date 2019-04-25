import java.awt.*;

public class ColorPalette{


  public static Color getColor(int selection){
    Color color = null;

    switch(selection){
      case 0:
      color =  Color.RED;
      break;
      case 1:
      color =  Color.GREEN;
      break;
      case 2:
      color =  Color.BLUE;
      break;
      case 3:
      color =  Color.YELLOW;
      break;
      case 4:
      color =  Color.PINK;
      break;
      case 5:
      color =  Color.ORANGE;
      break;
      case 6:
      color =  Color.CYAN;
      break;
      default:
      color =  Color.RED;
    }

    return color;

  }

}
