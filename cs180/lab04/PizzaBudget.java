import java.util.Scanner;

public class PizzaBudget{
    public static void main (String[] args)
    {
      int start;
      int cost;
      int income; 
      float days;
      
      Scanner input = new Scanner(System.in);
      
      System.out.print("Please enter how much money was saved up: ");
      start = input.nextInt();
      System.out.print("Please enter how much each pizza costs: ");
      cost = input.nextInt();
      System.out.print("Please enter how much money is earned every 2 weeks: ");
      income = input.nextInt();
      
      days = start/(cost - (income /(float)14));
      
      System.out.println("You will run out of money in "+days);
    }   
}