import java.util.*;
import javax.swing.*;
/*
#name: Mihir Jham
#class: BuggyWelcome.java
#project: cs180_lab03
*/
public class BuggyWelcome 
{
 public static void main(String [] args) 
 {
  //Create Scanner Object
  Scanner s = new Scanner(System.in);
  //Welcome user with JOptionPane
  JOptionPane.showMessageDialog(null, "Welcome to BuggyWelcome");
  //Request and read a string from user using JOptionPane
  String guiResult = JOptionPane.showInputDialog(null, "Please enter a string");
  //print a request for a string via the terminal
  System.out.println("Please enter a string: ");
  //read a string from the terminal
  String terminalResult = s.nextLine();
  //print out the String read from the JOptionPane
  System.out.println("The following string was entered in the JOptionPane: \n" + guiResult);
  //print out the String read from the terminal
  System.out.println("The following string was entered in the terminal: \n" + terminalResult);
 }
}
