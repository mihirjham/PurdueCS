/**
 * MyRunnable
 * Creates an instance of MyRunnable, which implements the Runnable interface
 * A Thread object is created and the MyRunnable object is passed as an argument.
 * A message is printed to the screen.
 *
 * @author Mihir jham
 * @cslogin mjham
 *
 * @recitation L04 Friday, 330-520pm
 *
 * @date 04/13/2012
 */

public class MyRunnable implements Runnable
{
    public static void main(String[] args) 
    {
      
      MyRunnable myRunnable = new MyRunnable();
      Thread t1 = new Thread(myRunnable);
      t1.start();
    }
    
    public void run() 
    {
        System.out.println("hello runnable thread");
    }
}
