import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lab10 extends JFrame implements ActionListener
{
    Container myContainer;
    JLabel myLabel;
    GridLayout myLayout;
    JButton myButton, changeName;
    int status;
    String name;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem reset;
    
    // Lab10 Constructor
    Lab10()
    {
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      myContainer = getContentPane();
      status = -1;
      name = "World";
            
      myLabel = new JLabel();
      myLabel.setText("Hello, " + name + "!");
      
      myButton = new JButton("Change to morning");
      myButton.addActionListener(this);

      changeName = new JButton("Change Name");
      changeName.addActionListener(this);
      
      menuBar = new JMenuBar();
      options = new JMenu("Options");
      reset = new JMenuItem("Reset");
      reset.addActionListener(this);
      options.add(reset);
      menuBar.add(options);
      setJMenuBar(menuBar);
      
      
      myLayout = new GridLayout(3,2);
            
      myContainer.add(myLabel);
      myContainer.add(myButton);
      myContainer.add(changeName);
      myContainer.setLayout(myLayout);
      
      pack();
      setVisible(true);
      
    }
    
    // Program starts here
    public static void main(String[] args)
    {
      Lab10 lab = new Lab10();
    }
    
    // Called when an action listener is triggered
    public void actionPerformed(ActionEvent e)
    {
      Object source = e.getSource();
      
      if(source instanceof JButton)
      {
        source = (JButton)source;
        
        if(source == myButton)
        {
          if(status == 0)
          {
            myLabel.setText("Good morning, "+ name +"!");
            myButton.setText("Change to evening");
            status = 1;
          }
          else
          {
            myLabel.setText("Good evening, "+name+"!");
            myButton.setText("Change to morning");
            status = 0;
          }
        }
        else if(source == changeName)
        {
          name = JOptionPane.showInputDialog(null,"Please enter a name: ");
        
          if(status == -1)
          {
            myLabel.setText("Hello, "+name+"!");
          }
          else if(status == 1)
          {
            myLabel.setText("Good morning, "+name+"!");
          }
          else
          {
            myLabel.setText("Good evening, "+name+"!");
          }
        }
      }
      else if(source instanceof JMenuItem)
      {
        source = (JMenuItem)source;
        
        if(source == reset)
        {
          name = "World";
          status = -1;
          myButton.setText("Change to morning");
          myLabel.setText("Hello, "+name+"!");
        }
      }
    }
}
