import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent.*;
import java.awt.event.*;
import java.util.Random;

public class SimulationController extends JPanel{

  final int xSize;
  final int ySize;

  public SimulationController(int xSize, int ySize){
    this.xSize = xSize;
    this.ySize = 200;
    this.setBackground(Color.blue);
    this.setPreferredSize(new Dimension(1200, 200));
    this.setLayout(new GridBagLayout());
  }

  // protected void paintComponent(Graphics g){
  //   super.paintComponent(g);
  //   Graphics2D g2 = (Graphics2D)g;
  //   g2.drawLine(0,160,1200,160);
  //   g2.drawLine(0,320,1200,320);
  // }

  protected void paintComponent(Graphics g){
    super.paintComponent(g);
  }





  public static void main(String args[]){
  }




}
