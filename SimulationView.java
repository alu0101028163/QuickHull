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

 public void quickHull(){

   ArrayList<Point> convexHull = new ArrayList<Point>();

   // Obtener los puntos más alejados
   Point maxPoint = new Point(-999999, 0, 0);
   Point minPoint = new Point(999999, 0, 0);
   for (int i = 0; i < points.size(); i++){
     if(points.get(i).getX() > maxPoint.getX()){
       maxPoint = points.get(i);
     }
     if(points.get(i).getY() < minPoint.getX()){
       minPoint = points.get(i);
     }
   }

   convexHull.add(maxPoint);
   convexHull.add(minPoint);

   points.remove(maxPoint);
   points.remove(minPoint);

   // double slope = ((double)(maxPoint.getX() - minPoint.getX()) / (double)(maxPoint.getY() - minPoint.getY()));
   // double xi = slope*maxPoint.getX();
   // double yi = slope*maxPoint.getY();

   ArrayList<Point> s1 = new ArrayList<Point>();
   ArrayList<Point> s2 = new ArrayList<Point>();

   // for (int i = 0; i < points.size(); i++){
   //   // double y = slope*points.get(i).getX() - xi + yi;
   //   double y = getYRect(maxPoint, minPoint, points.get(i));
   //   if((!points.get(i).isEqual(maxPoint))&&(!points.get(i).isEqual(minPoint)))
   //   if(points.get(i).getY() > y){
   //     s1.add(points.get(i));
   //   }else s2.add(points.get(i));
   // }

   for (int i = 0; i < points.size(); i++){
     if (pointLocation(minPoint, maxPoint, points.get(i)) == -1){
       s1.add(points.get(i));
     } else if (pointLocation(minPoint, maxPoint, points.get(i)) == 1){
       s2.add(points.get(i));
     }
   }

   findHull(convexHull, s2, minPoint, maxPoint);
   findHull(convexHull, s1, maxPoint, minPoint);


   for(int i = 0; i < convexHull.size(); i++){
     System.out.println(convexHull.get(i));
   }

 }


  public int pointLocation(Point a, Point b, Point p)
  {
      int cp1 = (b.getX() - a.getX()) * (p.getY() - a.getY()) - (b.getY() - a.getY()) * (p.getX() - a.getX());
      if (cp1 > 0)
          return 1;
      else if (cp1 == 0)
          return 0;
      else
          return -1;
  }


 public int distance(Point A, Point B, Point C)
 {
     int ABx = B.getX() - A.getX();
     int ABy = B.getY() - A.getY();
     int num = ABx * (A.getY() - C.getY()) - ABy * (A.getX() - C.getX());
     if (num < 0)
         num = -num;
     return num;
 }

 public void findHull(ArrayList<Point> convexHull, ArrayList<Point> sk, Point minPoint, Point maxPoint){

   int insertPosition = convexHull.indexOf(maxPoint);

   if(sk.size() == 0){
     return;
   }

   if(sk.size() == 1){
     Point p = sk.get(0);
     convexHull.add(insertPosition,p);
     sk.remove(p);
     return;
   }

   int maxDistance = -9999999;
   int furthestP = -1;
   for(int i = 0; i < sk.size(); i++){
     if(distance(minPoint, maxPoint, sk.get(i)) > maxDistance){
        maxDistance = distance(minPoint, maxPoint, sk.get(i));
        furthestP = i;
     }
   }

   Point furthestPoint = sk.get(furthestP);
   sk.remove(furthestP);
   convexHull.add(insertPosition,furthestPoint);

   ArrayList<Point> s1 = new ArrayList<Point>();
   ArrayList<Point> s2 = new ArrayList<Point>();


   for (int i = 0; i < sk.size(); i++){
     if(pointLocation(minPoint, furthestPoint, sk.get(i)) == 1){
       s1.add(sk.get(i));
     }
   }

   for (int i = 0; i < sk.size(); i++){
     if( pointLocation(furthestPoint, maxPoint, sk.get(i)) == 1){
       s2.add(sk.get(i));
     }
   }

   findHull(convexHull, s1, minPoint, furthestPoint);
   findHull(convexHull, s2, furthestPoint, maxPoint);

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

  public void setPoints(){
    points.add(new Point(0,5,0));
    points.add(new Point(1,1,0));
    points.add(new Point(2,3,0));
    points.add(new Point(2,9,0));
    points.add(new Point(3,2,0));
    points.add(new Point(3,4,0));
    points.add(new Point(3,7,0));
    points.add(new Point(3,2,0));
    points.add(new Point(5,1,0));
    points.add(new Point(5,4,0));
    points.add(new Point(5,6,0));
    points.add(new Point(6,10,0));
    points.add(new Point(7,7,0));
    points.add(new Point(7,4,0));
    points.add(new Point(8,2,0));
    points.add(new Point(10,8,0));
    points.add(new Point(10,4,0));

    // points.add(new Point(0,5,0));
    // points.add(new Point(2,0,0));
    // points.add(new Point(5,2,0));
    // points.add(new Point(3,3,0));
    // points.add(new Point(3,8,0));


  }



  public static void main(String args[]){

    SimulationView sm = new SimulationView(1200,500);
    sm.setPoints();
    sm.quickHull();
  }




}
