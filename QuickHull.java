import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.event.*;


public class QuickHull extends JFrame{

   final int xSize = 1200;
   final int ySize = 700;

   SimulationView simulationView;
   SimulationController simulationController;
   SimulationModel simulationModel;
   Thread simulationModelThread;
   Timer timer;

   JSlider speedSlider;

   public static boolean started;
   public static boolean finished;
   public static int speed;


  public QuickHull(){
    this.simulationView = new SimulationView(xSize, ySize);
    this.simulationController = new SimulationController(xSize, ySize);
    this.simulationModel = new SimulationModel(simulationView.points, simulationView.convexHull);
    this.simulationModelThread = new Thread(simulationModel);

    this.started = false;
    this.finished = false;
    QuickHull.speed = 1000;
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

    GridBagConstraints c2 = new GridBagConstraints();
    c2.gridx = 0;
    c2.gridy = 5;


    JButton b = new JButton("ConvexHull");
    b.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent click){
        QuickHull.started = true;
        simulationModelThread.start();
      }
    });

    this.speedSlider = new JSlider(0,5000,1000);
    this.speedSlider.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent event) {
        QuickHull.speed = 5000 - speedSlider.getValue();
        timer.setDelay(QuickHull.speed);
        System.out.println("Changing speed to: " + QuickHull.speed );
      }
    });

    simulationController.add(b);
    simulationController.add(speedSlider);


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

    frame.timer = new Timer(QuickHull.speed,new ActionListener(){
      public void actionPerformed(ActionEvent e) {
      if(QuickHull.finished){
          ((Timer)e.getSource()).stop();
      } else {
          if(QuickHull.started){
            finished = frame.simulationModel.resume();
            frame.simulationView.repaint();
          }
        }
    }
  });

    frame.timer.setRepeats(true);
    frame.timer.start();

  }


}
