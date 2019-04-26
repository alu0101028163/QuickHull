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
   SimulationModel simulationModel;
   Thread simulationModelThread;

   public static boolean started;
   public static boolean finished;


  public QuickHull(){
    this.simulationView = new SimulationView(xSize, ySize);
    this.simulationController = new SimulationController(xSize, ySize);
    this.simulationModel = new SimulationModel(simulationView.points, simulationView.convexHull);
    this.simulationModelThread = new Thread(simulationModel);

    this.started = false;
    this.finished = false;
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

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 4;

    JButton b = new JButton("ConvexHull");
    b.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent click){
        QuickHull.started = true;
        simulationModelThread.start();
      }
    });


    simulationController.add(b);


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


    final Timer timer = new Timer(500, new ActionListener(){
      public void actionPerformed(ActionEvent e) {
      if(QuickHull.finished){
          ((Timer)e.getSource()).stop();
          System.out.println("Finished");
      } else {
          if(QuickHull.started){
            finished = frame.simulationModel.resume();
            frame.simulationView.repaint();
          }
        }
    }
  });

    timer.setRepeats(true);
    timer.start();

  }


}
