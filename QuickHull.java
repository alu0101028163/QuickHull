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

  //
  // public static void addButtons(JPanel p, PantallaPrincipal pp){
  //
  //
  //   // ArrayList<Line> arr = new ArrayList<Line>();
  //
  //   JTextField textArea = new JTextField(20);
  //   JTextArea info = new JTextArea(5,20);
  //
  //
  //   JButton drop1 = new JButton("Drop1");
  //   drop1.addActionListener(new ActionListener(){
  //     public void actionPerformed(ActionEvent click){
  //       ArrayList<Line> arr = pp.drawPoint(1);
  //       String str = "";
  //       for(int i = 0; i < arr.size(); i++){
  //         str += String.valueOf(arr.get(i).getX()) + ", " + String.valueOf(arr.get(i).getY()) + "\n";
  //       }
  //       info.setText(str);
  //     }
  //   });
  //
  //   JButton drop10 = new JButton("Drop10");
  //   drop10.addActionListener(new ActionListener(){
  //     public void actionPerformed(ActionEvent click){
  //       ArrayList<Line> arr = pp.drawPoint(10);
  //       String str = "";
  //       for(int i = 0; i < 5; i++){
  //         str += String.valueOf(arr.get(i).getX()) + ", " + String.valueOf(arr.get(i).getY()) + "\n";
  //       }
  //       info.setText(str);
  //     }
  //   });
  //
  //   JButton drop100 = new JButton("Drop100");
  //   drop100.addActionListener(new ActionListener(){
  //     public void actionPerformed(ActionEvent click){
  //       ArrayList<Line> arr = pp.drawPoint(100);
  //       String str = "";
  //       for(int i = 0; i < 5; i++){
  //         str += String.valueOf(arr.get(i).getX()) + ", " + String.valueOf(arr.get(i).getY()) + "\n";
  //       }
  //       info.setText(str);
  //     }
  //   });
  //
  //   JButton drop1000 = new JButton("Drop1000");
  //   drop1000.addActionListener(new ActionListener(){
  //     public void actionPerformed(ActionEvent click){
  //       ArrayList<Line> arr = pp.drawPoint(1000);
  //       String str = "";
  //       for(int i = 0; i < 5; i++){
  //         str += String.valueOf(arr.get(i).getX()) + ", " + String.valueOf(arr.get(i).getY()) + "\n";
  //       }
  //       info.setText(str);
  //     }
  //   });
  //
  //   JButton set = new JButton("Set");
  //   set.addActionListener(new ActionListener(){
  //     public void actionPerformed(ActionEvent click){
  //
  //     }
  //   });
  //
  //
  //   GridBagConstraints c = new GridBagConstraints();
  //   c.gridx = 0;
  //   c.gridy = 0;
  //
  //   GridBagConstraints c2 = new GridBagConstraints();
  //   c2.gridx = 1;
  //   c2.gridy = 0;
  //
  //   GridBagConstraints c3 = new GridBagConstraints();
  //   c3.gridx = 2;
  //   c3.gridy = 0;
  //
  //   GridBagConstraints c4 = new GridBagConstraints();
  //   c4.gridx = 3;
  //   c4.gridy = 0;
  //
  //   GridBagConstraints c5 = new GridBagConstraints();
  //   c5.gridx = 1;
  //   c5.gridy = 1;
  //
  //   GridBagConstraints c6 = new GridBagConstraints();
  //   c6.gridx = 2;
  //   c6.gridy = 1;
  //
  //   GridBagConstraints c7 = new GridBagConstraints();
  //   c7.gridx = 2;
  //   c7.gridy = 3;
  //
  //
  //   p.add(drop1,c);
  //   p.add(drop10,c2);
  //   p.add(drop100,c3);
  //   p.add(drop1000,c4);
  //   p.add(set,c5);
  //   p.add(textArea, c6);
  //   p.add(info,c7);
  // }

}
