import javax.swing.*;
import java.util.*;
import java.text.*;
/*
#name: Mihir Jham
#class: CurrentTime.java
#project: cs180_lab03
*/
public class CurrentTime 
{
 public static void main(String [] args) 
 {
   
   Date currentDate = new Date();
   //String date = currentDate.toString();
   SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");
   
   //JOptionPane.showMessageDialog(null, date);  
   JOptionPane.showMessageDialog(null, formattedDate.format(currentDate));
   
 }
}
