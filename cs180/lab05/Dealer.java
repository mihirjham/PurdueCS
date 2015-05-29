import java.util.Scanner;

public class Dealer
{
  public static void main(String args[])
  {
    
    Car car1, car2, car3;
    String dealerName, car1Make, car1Model, car2Make, car2Model, car3Make, car3Model;
    int car1Price, car2Price, car3Price;
    
    Scanner input = new Scanner(System.in);
    
    System.out.print("Enter the dealer's name: ");
    dealerName = input.nextLine();
    
    System.out.print("Enter the make of Car #1: ");
    car1Make = input.nextLine();
    System.out.print("Enter the model of Car #1: ");
    car1Model = input.nextLine();
    System.out.print("Enter the price of Car #1: ");
    car1Price = Integer.parseInt(input.nextLine());
    
    System.out.print("Enter the make of Car #2: ");
    car2Make = input.nextLine();
    System.out.print("Enter the model of Car #2: ");
    car2Model = input.nextLine();
    System.out.print("Enter the price of Car #2: ");
    car2Price = Integer.parseInt(input.nextLine());
    
    System.out.print("Enter the make of Car #3: ");
    car3Make = input.nextLine();
    System.out.print("Enter the model of Car #3: ");
    car3Model = input.nextLine();
    System.out.print("Enter the price of Car #3: ");
    car3Price = Integer.parseInt(input.nextLine());
    
    car1 = new Car(car1Make, car1Model, car1Price);
    car2 = new Car(car2Make, car2Model, car2Price);
    car3 = new Car(car3Make, car3Model, car3Price);
    
    System.out.println("Welcome to "+dealerName);
    System.out.println("Below are our cars");
    car1.displayCarInfo();
    car2.displayCarInfo();
    car3.displayCarInfo();
    
  }
}
