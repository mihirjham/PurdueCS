/**
 * StackTester
 * This is the controller class for this project.
 * This class creates threads according to the number of files present
 * in the directory. It also creates the final expressions that have to be saved into the file.
 * 
 * @author Enchong Wang 
 * @cs login Wang977
 * @recitation REC4 recitation instructor's name Sriram, Dinesh
 * 
 * @author Haoran Lin
 * @cs login lin251
 * @recitation REC1 recitation instructor's name Ganasekaran,Bala
 * 
 * @author Mihir Jham
 * @cs login mjham
 * @recitation REC4 recitation instructor's name Sriram, Dinesh
 * 
 * @date 04/26/2012
 *
 */

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

class StackTester extends Thread
{
  String fileName; //File name of the input file
  String finalExpression = ""; //Final expression that is to be saved in the respective files
  
  static int numFiles; //Number of input files in the directory
  
  public static void main(String args[])
  {
    File directory = new File("data");
    String fileArray[] = directory.list();
    
    //This loops checks for the files that are input
    for(int a = 0 ; a < fileArray.length; a++)
    {
      String tempText = fileArray[a];
      if(tempText.substring(0,7).equals("in_file"))
      {
        numFiles++;
      }
    }                            
    
    Thread threadArray[] = new Thread[numFiles]; //Thread array for the number of files
    
    //This loop creates the Thread objects, and initializes them with the Controller class objects
    for(int i = 0; i < numFiles; i++)
    {
      threadArray[i] = new Thread(new StackTester("in_file"+(i+1)));
    }                             
    
    //This loop begins each of the Threads, and their processing
    for(int j = 0; j < numFiles; j++)
    {
      threadArray[j].start();
    }
    
    //This block joins each of the threads at the end, of all the processing
    try
    {
      for(int k = 0; k < numFiles; k++)
      {
        threadArray[k].join();
      }
    }
    catch(InterruptedException interruptedException)
    {
      System.out.println("InterruptedException caught!");
    }
  }
  
  /**
   * run()
   * This is the run method that has been extended from the Thread class. It begins the processing
   * of each of the files.
   *
   * @param none
   * 
   * @return none
   */
  public void run()
  {
    processFile(this.fileName);
  }
  
  /**
   * Constructor()
   * Creates an object of the controller class, and initializes the input file name as well
   *
   * @param String Input file name
   * 
   * @return StackTester object of the controller class
   */
  public StackTester(String fileName)
  {
    this.fileName = fileName;
  }
  
  /**
   * processFile()
   * This processes the file, according to the input file name.
   * Creates a stack object, to hold the trees, creates the trees, and pushes it into the stack.
   * Creates the expression that have to be saved to the file, in the respective format.
   * Saves it to the appropriate output files
   *
   * @param String input file name
   * 
   * @return none
   */
  public void processFile(String fileName)
  {
    StackImplementation stack = new StackImplementation();  //Creates an object of the stack data structure
    
    FileInputStream in = null;
    File newFile;
    Scanner scanner;
    
    try
    {
      newFile = new File("data/"+fileName);
      in = new FileInputStream(newFile);
    }
    catch(FileNotFoundException evt)
    {
      System.out.println("File not found!");
    }
    
    try
    {
      scanner = new Scanner(in);
      while(scanner.hasNext() == true)
      {
        String expression = scanner.nextLine();
        String content[] = expression.split(" ");
        double evaluated = 0;
        
        try
        {
          for(int i = 0; i < content.length; i++)
          {
            if(content[i].equals("+") || content[i].equals("-") || content[i].equals("*") || content[i].equals("/"))
            {
              ExpressionTree tempRight = (ExpressionTree)stack.popElement();
              ExpressionTree tempLeft = (ExpressionTree)stack.popElement();
              
              if(content[i].equals("+"))
                evaluated = tempLeft.node.result + tempRight.node.result;
              if(content[i].equals("-"))
                evaluated = tempLeft.node.result - tempRight.node.result;
              if(content[i].equals("*"))
                evaluated= tempLeft.node.result * tempRight.node.result;
              if(content[i].equals("/"))
                evaluated = tempLeft.node.result / tempRight.node.result;
              
              ExpressionTree newTree = new ExpressionTree(new TreeNode(tempLeft.node, tempRight.node, content[i], evaluated));
              
              stack.pushElement(newTree);
            }
            else
            {  
              evaluated = Double.parseDouble(content[i]);
              ExpressionTree newTree = new ExpressionTree(new TreeNode(null, null, content[i], evaluated));
              stack.pushElement(newTree);
            }
          }
          
          do
          {
            DecimalFormat df = new DecimalFormat("#.##");
            
            ExpressionTree newTree = (ExpressionTree)stack.popElement();
            String inorder = ExpressionTree.inOrder(newTree.node);
            String postorder = ExpressionTree.postOrder(newTree.node);
            String preorder = ExpressionTree.preOrder(newTree.node);;
            
            finalExpression = finalExpression + inorder + "," + postorder + "," + preorder + "," + df.format(evaluated) + "\n";
            
          }while(!(stack.isEmpty()));
        }
        catch(StackEmptyException e)
        {
          System.out.println(e);
        }
      }
      in.close();
    }
    catch(IOException event)
    {
      System.out.println("Reading from file error!");
    }
    
    FileOutputStream outStream = null;
    File inFile;
    PrintWriter pw;
    
    try
    {
      inFile = new File("data/out_"+fileName);
      outStream = new FileOutputStream(inFile);
    }
    catch(FileNotFoundException fnfevt)
    {
      System.out.println("No such file exists!");
    }
    
    try
    {
      pw = new PrintWriter(outStream);
      
      pw.println(finalExpression.substring(0,finalExpression.length() - 1));
      pw.close();
      outStream.close();
    } 
    catch(IOException ioe)
    {
      System.out.println("Writing error!");
    }
  }
} 
