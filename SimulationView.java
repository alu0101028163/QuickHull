import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent.*;
import java.awt.event.*;
import java.util.Random;

public class SimulationView extends JPanel{

  ArrayList<Point> points = new ArrayList<Point>();
  ArrayList<Point> convexHull = new ArrayList<Point>();
  ArrayList<Point> segments = new ArrayList<Point>();

  final int xSize;
  final int ySize;

  public SimulationView(int xSize, int ySize){
    this.xSize = xSize;
    this.ySize = ySize - 200;
    this.setBackground(Color.white);
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

    for(int i = 0; i < convexHull.size() - 1; i++){
      g2.setColor(Color.black);
      g2.drawLine(convexHull.get(i).getX(), convexHull.get(i).getY(),convexHull.get(i + 1).getX(), convexHull.get(i + 1).getY());
    }
      if(convexHull.size() != 0)
      g2.drawLine(convexHull.get(convexHull.size() - 1).getX(), convexHull.get(convexHull.size() - 1).getY(), convexHull.get(0).getX(), convexHull.get(0).getY());

  }

 public void quickHull(){

   this.convexHull = new ArrayList<Point>();

   if(points.size() < 3){
     convexHull = (ArrayList)points.clone();
     return;
   }

   int minP = -1, maxP = -1;
       int minX = Integer.MAX_VALUE;
       int maxX = Integer.MIN_VALUE;
       for (int i = 0; i < points.size(); i++){
           if (points.get(i).getX() < minX){
               minX = points.get(i).getX();
               minP = i;
           }

           if (points.get(i).getX() > maxX){
               maxX = points.get(i).getX();
               maxP = i;
           }
       }

   Point maxPoint = points.get(maxP);
   Point minPoint = points.get(minP);

   convexHull.add(maxPoint);
   convexHull.add(minPoint);

   points.remove(maxPoint);
   points.remove(minPoint);

   ArrayList<Point> s1 = new ArrayList<Point>();
   ArrayList<Point> s2 = new ArrayList<Point>();

   for (int i = 0; i < points.size(); i++){
     if (pointLocation(minPoint, maxPoint, points.get(i)) == -1){
       s1.add(points.get(i));
     } else if (pointLocation(minPoint, maxPoint, points.get(i)) == 1){
       s2.add(points.get(i));
     }
   }

   findHull(convexHull, s2, minPoint, maxPoint);
   findHull(convexHull, s1, maxPoint, minPoint);
   // hullSet(minPoint, maxPoint, s2, convexHull);
   // hullSet(maxPoint, minPoint, s1, convexHull);

   repaint();

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

//  public void quickHull()
//    {
//        this.convexHull = new ArrayList<Point>();
//        if (points.size() < 3){
//            this.convexHull = (ArrayList) points.clone();
//            return;
//          }
//
//        int minPoint = -1, maxPoint = -1;
//        int minX = Integer.MAX_VALUE;
//        int maxX = Integer.MIN_VALUE;
//        for (int i = 0; i < points.size(); i++)
//        {
//            if (points.get(i).getX() < minX)
//            {
//                minX = points.get(i).getX();
//                minPoint = i;
//            }
//            if (points.get(i).getX() > maxX)
//            {
//                maxX = points.get(i).getX();
//                maxPoint = i;
//            }
//        }
//        Point A = points.get(minPoint);
//        Point B = points.get(maxPoint);
//        convexHull.add(A);
//        convexHull.add(B);
//        points.remove(A);
//        points.remove(B);
//
//        ArrayList<Point> leftSet = new ArrayList<Point>();
//        ArrayList<Point> rightSet = new ArrayList<Point>();
//
//        for (int i = 0; i < points.size(); i++)
//        {
//            Point p = points.get(i);
//            if (pointLocation(A, B, p) == -1)
//                leftSet.add(p);
//            else if (pointLocation(A, B, p) == 1)
//                rightSet.add(p);
//        }
//        hullSet(A, B, rightSet, convexHull);
//        hullSet(B, A, leftSet, convexHull);
//
//        repaint();
//    }
// //
// public void hullSet(Point A, Point B, ArrayList<Point> set,
//         ArrayList<Point> hull)
// {
//     int insertPosition = hull.indexOf(B);
//     if (set.size() == 0)
//         return;
//     if (set.size() == 1)
//     {
//         Point p = set.get(0);
//         set.remove(p);
//         hull.add(insertPosition, p);
//         return;
//     }
//     int dist = Integer.MIN_VALUE;
//     int furthestPoint = -1;
//     for (int i = 0; i < set.size(); i++)
//     {
//         Point p = set.get(i);
//         int distance = distance(A, B, p);
//         if (distance > dist)
//         {
//             dist = distance;
//             furthestPoint = i;
//         }
//     }
//     Point P = set.get(furthestPoint);
//     set.remove(furthestPoint);
//     hull.add(insertPosition, P);
//
//     // Determine who's to the left of AP
//     ArrayList<Point> leftSetAP = new ArrayList<Point>();
//     for (int i = 0; i < set.size(); i++)
//     {
//         Point M = set.get(i);
//         if (pointLocation(A, P, M) == 1)
//         {
//             leftSetAP.add(M);
//         }
//     }
//
//     // Determine who's to the left of PB
//     ArrayList<Point> leftSetPB = new ArrayList<Point>();
//     for (int i = 0; i < set.size(); i++)
//     {
//         Point M = set.get(i);
//         if (pointLocation(P, B, M) == 1)
//         {
//             leftSetPB.add(M);
//         }
//     }
//     hullSet(A, P, leftSetAP, hull);
//     hullSet(P, B, leftSetPB, hull);
//
// }
//
//
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


  }


  public void print(){

    for(int i = 0; i < convexHull.size(); i++){
      System.out.println("P-> " + convexHull.get(i));
    }
  }

  public static void main(String args[]){

    SimulationView sm = new SimulationView(1200,500);
    sm.setPoints();
    sm.quickHull();
  }




}
