import java.awt.*;


public class Point{

  int posX;
  int posY;
  Color color;

  public Point(int posX, int posY,int color){
    this.posX = posX;
    this.posY = posY;
    this.color = ColorPalette.getColor(color);
  }

  public int getX(){
    return posX;
  }

  public int getY(){
    return posY;
  }

  public Color getColor(){
    return color;
  }

  public String toString(){
    return posX + " " + posY;
  }

  public boolean isEqual(Point p){
    return ((p.getX() == this.posX)&&(p.getY() == this.posY));
  }

  public static void main(String args[]){
    Point l = new Point(0,0,0);
    System.out.println(l);
  }




}
