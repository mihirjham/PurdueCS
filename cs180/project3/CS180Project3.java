/**
 * Project Name : CS180Project3
 * Class: CSProject3
 * 
 * This is the controller class, which has the main method, and executes the program
 *
 * @author Mihir Jham
 * @login mjham
 *
 * @recitation R04F Dinesh Sriran
 *
 * @date 02/12/2012
 *
 */

public class CS180Project3
{
  public static void main(String args[])
  {
    Computation computation = new Computation();  //Creates an object of Computation class
    
    //Calls all public methods
    computation.getInput();                       
    computation.computeResult();
    computation.displayComputation();
  }
}
