import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent.*;
import java.util.*;
import javax.swing.Timer;


public class QuickHull extends JFrame{

   final int xSize = 1200;
   final int ySize = 700;

   SimulationView simulationView;
   SimulationController simulationController;


  public QuickHull(){
    this.simulationView = new SimulationView(xSize, ySize);
    this.simulationController = new SimulationController(xSize, ySize);
  }

  public JButton createPointButton(String buttonName, int numberOfPoints){
    JButton b = new JButton(buttonName);
    b.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent click){
        simulationView.drawPoint(numberOfPoints);
      }
    });
    return b;
  }

  public void addButton(int gridx, int gridy, String buttonName, int numberOfPoints){

      GridBagConstraints c = new GridBagConstraints();
      c.gridx = gridx;
      c.gridy = gridy;

      JButton b = createPointButton(buttonName, numberOfPoints);

      simulationController.add(b);

  }

  public void addButtons(){
    addButton(0,0, "Place 1", 1);
    addButton(0,1, "Place 10", 10);
    addButton(0,2, "Place 100", 100);
    addButton(0,3, "Place 1000", 1000);
  }

  public static void main(String args[]){


    QuickHull frame = new QuickHull();
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
    frame.getContentPane().setBackground(Color.white);
    frame.setSize(frame.xSize,frame.ySize);
    frame.add(frame.simulationView);
    frame.add(frame.simulationController);
    frame.addButtons();
    frame.setVisible(true);

  }


}
