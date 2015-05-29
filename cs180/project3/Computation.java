/**
 * Project Name : CS180Project3
 * Class: Computation
 * 
 * This class asks the user for input, handles the input validation, computes the result
 * and displays the info in the specified manner.
 *
 * @author Mihir Jham
 * @login mjham
 *
 * @recitation R04F Dinesh Sriran
 *
 * @date 02/12/2012
 *
 */

import java.util.Scanner;
import java.text.DecimalFormat;

class Computation
{
  private double operand1, operand2, result;  //Variables to hold numerical values
  private String operator;                    //Variable to hold the operator
  
  public void getInput()
  {
    Scanner scanner = new Scanner(System.in);
    
    System.out.println("Enter the operator:");
    operator = scanner.next();
    checkOperator();  //Function call to check if the operator is valid
    System.out.println("Enter the first operand:");
    operand1 = Double.parseDouble(scanner.next());
    System.out.println("Enter the second operand:");
    operand2 = Double.parseDouble(scanner.next());
    checkZero();//Function call to check if the second operand is zero and the operator is a division sign
  }
  
  /**
  * checks to see if the operator is valid, if not valid, prints a message and exits the program.
  *
  * @param none
  * @return void
  */
  private void checkOperator()
  {
    if(!((operator.equals("+")) || (operator.equals("-")) || (operator.equals("/")) || (operator.equals("*"))))
    { 
      System.out.println("Invalid Operator");
      System.exit(0);
    }
  }
  
  /**
  * checks to see if the operand2 is a 0 and if the operation is division, if not valid, prints a message and exits
  * the program
  *
  * @param none
  * @return void
  */
  private void checkZero()
  {
    if(operator.equals("/") && operand2 == 0)
    {
      System.out.println("Cannot divide by zero");
      System.exit(0);
    }
  }
  
  /**
  * calculates the result based on the operator input.
  *
  * @param none
  * @return void
  */
  public void computeResult()
  {
    if(operator.equals("+"))
      result = operand1 + operand2;
    else if(operator.equals("-"))
      result = operand1 - operand2;
    else if(operator.equals("/"))
      result = operand1/operand2;
    else
      result = operand1*operand2;
  }
  
  /**
  * displays the result in the specified manner
  *
  * @param none
  * @return void
  */
  public void displayComputation()
  {
    DecimalFormat df1 = new DecimalFormat("#.###");
    DecimalFormat df2 = new DecimalFormat("#.000");
    System.out.println(df1.format(operand1)+" "+operator+" "+df1.format(operand2) +" = "+df2.format(result));
  }
  
}
  
  