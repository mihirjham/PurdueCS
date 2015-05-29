/**
 * Program name CS180Project2
 * This program takes input the radius and the height values of 2
 * cylindrical tanks through a JOptionPane message dialog box, 
 * calculates their volumes in gallons, and shows the value of the
 * larger tank through a JOptionPane message dialog box
 *
 * @author Mihir Jham
 * @login mjham
 *
 * @recitation R04 Dinesh Sriram
 *
 * @date 02/04/2012
 *
 */

import javax.swing.JOptionPane;
import java.lang.Math;
import java.text.DecimalFormat;
import java.util.Scanner;

public class CS180Project2
{
  public static void main(String args[])
  {
    Tank tank = new Tank();  //Creates an instance of Class Tank
    tank.userInput();
    tank.calcVolume();
    tank.showOutput();
  }
}

class Tank
{
  private final double GALLONS = 0.000264172052;  //Constant value to convert to gallons from cubic cm
  private String input1, input2, str;             //Input strings
  private double radius1, radius2;                //Radius of both cylinders
  private double height1, height2;                //Heights of both cylinders
  private double volume1, volume2;                //Volume of both cylinders
  
 /**
 * userInput()
 * 
 * Takes input from user through JOptionPane
 * 
 * @no parameters
 * @return void
 */
  
  public void userInput()
  {
    input1 = JOptionPane.showInputDialog(null, "Enter the radius and height of cylindrical tank 1 in centimeters");
    input2 = JOptionPane.showInputDialog(null, "Enter the radius and height of cylindrical tank 2 in centimeters");
    str = input1 + " " +  input2;
    
    Scanner scanner = new Scanner(str); //Creates an object of Scanner and passes String 'str' through it
    radius1 = scanner.nextDouble();
    height1 = scanner.nextDouble();  
    radius2 = scanner.nextDouble();
    height2 = scanner.nextDouble();
  }
  
 /**
 * calcVolume()
 * 
 * Calculates the volume of both the cylinders and converts them
 * to gallons
 * 
 * @no parameters
 * @return void
 */
  public void calcVolume()
  {
    volume1 = Math.PI * radius1 * radius1 * height1 * GALLONS;
    volume2 = Math.PI * radius2 * radius2 * height2 * GALLONS;
  }
  
 /**
 * showOutput()
 * 
 * Checks which tank has the larger volume and outputs it accordingly
 * 
 * @no parameters
 * @return void
 */
  
  public void showOutput()
  {
    DecimalFormat df = new DecimalFormat("#.###");  //Creates an object of DecimalFormat, with the appropriate formatting
    
    if(volume1 > volume2)
    {
      JOptionPane.showMessageDialog(null, "The volume of the largest cylindrical tank in gallons is "+ df.format(volume1));
    }
    else
    {
      JOptionPane.showMessageDialog(null, "The volume of the largest cylindrical tank in gallons is "+ df.format(volume2));
    }
  }
}
                                      
  