/**
 * MyThread
 * Creates a Thread object and an instance of MyThread(class).
 * Prints information about each thread
 *
 * @author Mihir jham
 * @cslogin mjham
 *
 * @recitation L04 Friday, 330-520pm
 *
 * @date 04/13/2012
 */

public class MyThread extends Thread
{
    public static void main(String[] args) 
    {
        Thread t1 = Thread.currentThread();
        MyThread t2 = new MyThread();
        System.out.println(t1);
        System.out.println(t2);
          
    }
}
