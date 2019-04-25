import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent.*;
import java.awt.event.*;
import java.util.Random;

public class SimulationView extends JPanel{

  ArrayList<Point> points = new ArrayList<Point>();
  final int xSize;
  final int ySize;

  public SimulationView(int xSize, int ySize){
    this.xSize = xSize;
    this.ySize = ySize - 200;
    this.setBackground(Color.red);
    this.setPreferredSize(new Dimension(1200, 500));
  }

  protected void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;

    for(int i = 0; i < points.size(); i++){
      g2.setColor(points.get(i).getColor());
      g2.setStroke(new BasicStroke(5));
      g2.drawLine(points.get(i).getX(), points.get(i).getY(),points.get(i).getX(),points.get(i).getY());
    }

  }

  public void drawPoint(int numberOfPoints){

    for (int i = 0; i < numberOfPoints; i++){
      Random rand = new Random();
      int x = rand.nextInt(xSize);
      int y = rand.nextInt(ySize);
      int c = rand.nextInt(7);

      points.add(new Point(x,y,c));
    }

    repaint();
  }


  public static void main(String args[]){
  }




}
