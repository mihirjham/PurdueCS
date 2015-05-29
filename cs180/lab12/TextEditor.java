import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * 
 */

/**
 * @author Mihir Jham
 *
 */
public class TextEditor extends JFrame implements ActionListener 
{
  
  JTextArea textArea;
  Container contentPane;
  JButton openButton, saveButton;
  JPanel buttonPanel;
  boolean hasOpened = false;
  String fileName;
    
    
    TextEditor()
    {
        //TODO constructor 
      
      contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());
      
      textArea = new JTextArea(50,50);
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      openButton = new JButton("Open");
      openButton.addActionListener(this);
      saveButton = new JButton("Save");
      saveButton.addActionListener(this);
      buttonPanel.add(openButton);
      buttonPanel.add(saveButton);
      contentPane.add(textArea, BorderLayout.CENTER);
      contentPane.add(buttonPanel, BorderLayout.SOUTH);
      pack();
      setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        //TODO Listener
      
      JButton button = (JButton)e.getSource();
      
      if(button == openButton)
      {
        fileName = JOptionPane.showInputDialog(null,"Please enter the filename you want to open");
        
        FileInputStream in = null;;
        File newFile;
        Scanner scanner;
        
        try
        {
          newFile = new File(fileName);
          in = new FileInputStream(newFile);
        }
        catch(FileNotFoundException evt)
        {
          JOptionPane.showMessageDialog(null,"File not Found!");
        }
        
        try
        {
         textArea.setText(null);
         scanner = new Scanner(in);
         while(scanner.hasNext() == true)
         {
           textArea.append(scanner.nextLine()+"\n");
         }
         in.close();
         hasOpened = true;
        }
        catch(IOException event)
        {
          JOptionPane.showMessageDialog(null,"Reading error!");
        }
      }
      else if(button == saveButton)
      {
        if(hasOpened)
        {
          FileOutputStream out = null;
          File newFile;
          PrintWriter pw;
          
          try
          {
            newFile = new File(fileName);
            out = new FileOutputStream(newFile);
          }
          catch(FileNotFoundException evt)
          {
            JOptionPane.showMessageDialog(null, "No such file exists!");
          }
          
          try
          {
            pw = new PrintWriter(out);
            pw.println(textArea.getText());
            pw.close();
            out.close();
          }
          catch(IOException event)
          {
            JOptionPane.showMessageDialog(null, "Writing error!");
          }
          finally
          {
            textArea.setText(null);
            hasOpened = false;
          }
        }
        else
        {
          fileName = JOptionPane.showInputDialog(null, "Please enter the file name you want to save your document to");
          FileOutputStream out = null;
          File newFile;
          PrintWriter pw;
          
          try
          {
            newFile = new File(fileName);
            out = new FileOutputStream(newFile);
          }
          catch(FileNotFoundException evt)
          {
            JOptionPane.showMessageDialog(null, "No such file exists!");
          }
          
          try
          {
            pw = new PrintWriter(out);
            pw.println(textArea.getText());
            pw.close();
            out.close();
          }
          catch(IOException event)
          {
            JOptionPane.showMessageDialog(null, "Writing error!");
          }
          finally
          {
            textArea.setText(null);
            hasOpened = false;
          }
        }
      }  
    }
    
    public static void main(String[] args)
    {
        TextEditor tx = new TextEditor();  
    }
}
