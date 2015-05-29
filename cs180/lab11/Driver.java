import java.util.*;

public class Driver
{
 public static void main(String [] args) 
 {
  Scanner scan = new Scanner(System.in);
  
  Calculator calc = new Calculator();
  int input = 10;
  int arg1 = 0, arg2 = 0;
 
  System.out.println("Welcome to this simple calculator");
  while(input != 0) 
  {
   System.out.println("\n0) Exit");
   System.out.println("1) Add");
   System.out.println("2) Subtract");
   System.out.println("3) Multiply");
   System.out.println("4) Divide\n");
   System.out.print("Please select a menu item: ");
   
   try
   {
     input = scan.nextInt();
   }
   catch(InputMismatchException e)
   {
     System.out.println("Invalid input");
     scan.next();
   }
   
   if(input > 0 && input < 5) 
   {
    try
    {
      System.out.print("Please enter the first argument of the operation: ");
      arg1 = scan.nextInt();
    }
    catch(InputMismatchException e)
    {
      System.out.println("Invalid Input");
      scan.next();
    }
    try
    {
      System.out.print("Please enter the second argument of the operation: ");
      arg2 = scan.nextInt();
    }
    catch(InputMismatchException e)
    {
      System.out.println("Invalid input");
      scan.next();
    }
   }
   switch(input)
   {
    case 0:
     System.out.println("Exiting!");
     break;
    case 1:
     try
     {
      System.out.println(calc.add(arg1, arg2));
      break;
     }
     catch(NegativeArgumentException e)
     {
       System.out.println("Negative arguments present");
     } 
    case 2:
     try
     {
      System.out.println(calc.subtract(arg1, arg2));
      break;
     }
     catch(NegativeArgumentException e)
     {
       System.out.println("Negative arguments present");
       break;
     }
     catch(NegativeResultException e)
     {
       System.out.println("Negative results obtained");
       break;
     }
    case 3:
     try
     {
      System.out.println(calc.multiply(arg1, arg2));
      break;
     }
     catch(NegativeArgumentException e)
     {
       System.out.println("Negative arguments present");
       break;
     }
    case 4:
     try
     {
      System.out.println(calc.divide(arg2, arg2));
      break;
     }
     catch(ArithmeticException e)
     {
       System.out.println("Cannot divide by zero");
       break;
     }
     catch(NegativeArgumentException e)
     {
       System.out.println("Negative arguments present");
       break;
     }
    default:
     System.out.println("Invalid input!");
   }
  }
 }
}
