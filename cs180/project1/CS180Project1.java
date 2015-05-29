/**
 * Program Name: CS180Project1
 * This program creates an invoice based on user inputs, such
 * as customer name, quantity, price of items, applicable tax,
 * etc, and it arrives at a total amount, through various
 * calculations. The program uses basic objects to store the data,
 * and is called in the main. User-defined methods are used for
 * each sub-task. 
 *
 * @author Mihir Jham
 * @CS login: mjham
 * 
 * @recitation R04F, Dinesh Sriram
 *
 * @date completed 01/30/2012
 *
 */

import java.util.Scanner;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class CS180Project1
{
  public static void main(String args[])
  {
    Customer customer = new Customer(); //Creates an instance of the Customer class(or creates an object of the Customer class)
    customer.readUserInput();           //Created customer object calls readUserInput()
    customer.processData(customer.item1Qty, customer.item2Qty, customer.item3Qty, customer.item1Price, customer.item2Price, customer.item3Price, customer.discountPercent);
    //Created customer object calls processData()
    customer.displayData(customer.firstName, customer.lastName, customer.item1Code, customer.item1Qty, customer.item1Price, customer.item2Code, customer.item2Qty, customer.item2Price, customer.item3Code, customer.item3Qty, customer.item3Price, customer.totalBeforeTax, customer.discountPercent, customer.discount, customer.taxRate, customer.tax, customer.total);
    //Created customer object calls displayData()
  } 
}

 /**
 * Customer
 * Class created to hold the invoice details of the specific customer
 * 
 * @method readUserInput() Method created to read input data from user
 * @method processData()   Method created to process all the calculations
 * @method displayData()   Method created to display the data in the specified format 
 */

class Customer
{
  String firstName;          //Variable for Customer's first name
  String lastName;           //Variable for Customer's last name
  String item1Code;          //Variable for Item 1's code
  String item2Code;          //Variable for Item 2's code
  String item3Code;          //Variable for Item 3's code
  int item1Qty;              //Variable for Item 1's quantity
  int item2Qty;              //Variable for Item 2's quantity
  int item3Qty;              //Variable for Item 3's quantity
  int discountPercent;       //Variable for the discount percent 
  int taxRate;               //Variable for tax rate
  double item1Price;         //Variable for Item 1's price
  double item2Price;         //Variable for Item 2's price
  double item3Price;         //Variable for Item 3's price
  double totalBeforeTax;     //Variable for the total calculated before tax 
  double discount;           //Variable for the calculated discount
  double totalAfterDiscount; //Variable for the total calculated after deducting the discount  
  double tax;                //Variable for the calculated tax
  double total;              //Variable for the calculted total amount
  
 
 /**
 * readUserInput()
 * Method created to allow the user to input customer details
 * 
 * @return void
 */
   
  public void readUserInput()
  {
 
    Scanner input = new Scanner(System.in);            //Creates Scanner object
    
    System.out.print("Enter Customer's First Name: ");
    firstName = input.next();                         
    System.out.print("Enter Customer's Last Name: ");
    lastName = input.next();
    System.out.print("Enter Item 1 Code: ");
    item1Code = input.next();
    System.out.print("Enter Item 1 Qty: ");
    item1Qty = input.nextInt();
    System.out.print("Enter Item 1 Price: ");
    item1Price = input.nextDouble();
    System.out.print("Enter Item 2 Code: ");
    item2Code = input.next();
    System.out.print("Enter Item 2 Qty: ");
    item2Qty = input.nextInt();
    System.out.print("Enter Item 2 Price: ");
    item2Price = input.nextDouble();
    System.out.print("Enter Item 3 Code: ");
    item3Code = input.next();
    System.out.print("Enter Item 3 Qty: ");
    item3Qty = input.nextInt();
    System.out.print("Enter Item 3 Price: ");
    item3Price = input.nextDouble();
    System.out.print("Enter Discount Percent: ");
    discountPercent = input.nextInt();
  }
  
 /**
 * processData()
 * Method created to process data, such as calculations for the total before tax,
 * discount, total after discount, tax rate, tax and total amount.
 * 
 * @param item1Qty Item 1's quantity
 * @param item2Qty Item 2's quantity
 * @param item3Qty Item 3's quantity
 * @param item1Price Item 1's price
 * @param item2Price Item 2's price
 * @param item3Price Item 3's price
 * @param discountPercent Discount Percent from user
 * @return void
 */
   
  public void processData(int item1Qty, int item2Qty, int item3Qty, double item1Price, double item2Price, double item3Price, int discountPercent)
  {
    Random rnd = new Random(); //Creates Random object
    
    totalBeforeTax = (item1Qty * item1Price) + (item2Qty * item2Price) + (item3Qty * item3Price); //Calculates the total amount before tax was added or discounts were deducted
    discount = totalBeforeTax * discountPercent * 0.01;                                           //Calculates the discount based on the discount percent from user 
    totalAfterDiscount = totalBeforeTax - discount;                                               //Calculates the total after discounts have been deducted
    taxRate = rnd.nextInt(6) + 2;                                                                 //Selects a random value for a tax percentage, ranging from 2 to 7
    tax = totalAfterDiscount * taxRate * 0.01;                                                    //Calculates the tax based on the selected taxRate
    total = totalAfterDiscount + tax;                                                             //Calculates the total amount after adding the tax
    
  }
  
 /**
 * displayData()
 * Method created to display the data in the required invoice format
 * 
 * @param firstName       Customer's first name
 * @param lastName        Customer's last name
 * @param item1Code       Item 1's code 
 * @param item1Qty        Item 1's quantity
 * @param item1Price      Item 1's Price
 * @param item2Code       Item 2's code
 * @param item2Qty        Item 2's quantity
 * @param item2Price      Item 2's price
 * @param item3Code       Item 3's code
 * @param item3Qty        Item 3's quantity
 * @param item3Price      Item 3's price
 * @param totalBeforeTax  Calculated value of the total before tax
 * @param discountPercent Discount Percent
 * @param discount        Calculated discount
 * @param taxRate         Tax Rate
 * @param tax             Calculated tax 
 * @param total           Calculated total amount
 * @return void
 */
   
  public void displayData(String firstName, String lastName, String item1Code, int item1Qty, double item1Price, String item2Code, int item2Qty, double item2Price, String item3Code, int item3Qty, double item3Price, double totalBeforeTax, int discountPercent, double discount, int taxRate, double tax, double total)  
  {
    Date date = new Date();                                       //Creates a Date object
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");      //Creates a SimpleDataFormat object, with the specified format
    Random rnd = new Random();                                    //Creates a Random object
    DecimalFormat df = new DecimalFormat("##.00");                //Creates a DecimalFormat object, with the specified format
    
    System.out.println(firstName + " " + lastName);
    System.out.println("Date:\t\t" + sdf.format(date));
    System.out.println("Invoice#:\t\t" + (rnd.nextInt(9000) + 1000)); //Gives a random value for the invoice, ranging from 1000 to 8999
    System.out.println("Item\tQty\tPrice");
    System.out.println("----\t---\t-----");
    System.out.println(item1Code + "\t" + item1Qty + "\t" + item1Price);
    System.out.println(item2Code + "\t" + item2Qty + "\t" + item2Price);
    System.out.println(item3Code + "\t" + item3Qty + "\t" + item3Price);
    System.out.println("");
    System.out.println("Before tax:\t"+ df.format(totalBeforeTax));
    System.out.println("Discount @" + discountPercent + "%:\t" + df.format(discount));
    System.out.println("Tax Rate:\t\t" + taxRate +"%");
    System.out.println("Tax:\t\t " + df.format(tax));
    System.out.println("Total:\t\t"+ df.format(total));
  }
}
