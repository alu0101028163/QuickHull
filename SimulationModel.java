import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent.*;
import java.awt.event.*;
import java.util.Random;




public class SimulationModel implements Runnable{

  ArrayList<Point> convexHull;
  ArrayList<Point> points;
  boolean finished;
  boolean stopped;

  public SimulationModel( ArrayList<Point> points, ArrayList<Point> convexHull){
      this.convexHull = convexHull;
      this.points = points;
      this.finished = false;
      this.stopped = true;
  }

  // public void resume(){
  //   stopped = false;
  // }

  public synchronized boolean resume(){
   stopped = false;
   notify();
   return finished;
 }

  public void run(){
    try{
      stopped = false;
      quickHull();
    }catch(Exception e){
      System.out.println(e);
    }
  }

  public void quickHull() throws Exception{

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

    // repaint();
    finished = true;

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

  public synchronized void findHull(ArrayList<Point> convexHull, ArrayList<Point> sk, Point minPoint, Point maxPoint) throws Exception{

    if(!stopped){
      stopped = true;
      wait();
    }

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

   public static void main(String args[]){
     SimulationView sv = new SimulationView(1200,500);
     sv.setPoints();

     SimulationModel sm = new SimulationModel(sv.points, sv.convexHull);
     Thread hilo = new Thread(sm);
     hilo.start();

     try{Thread.sleep(1000);}catch(Exception e){}

     sv.print();


   }
}
