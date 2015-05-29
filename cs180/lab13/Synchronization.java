/**
 * Synchronization
 * Create a class that illustrates synchronization between two threads.
 *
 * @author Mihir jham
 * @cslogin mjham
 *
 * @recitation L04 Friday, 330-520pm
 *
 * @date 04/13/2012
 */

public class Synchronization extends Thread
{
    static int threads;
    final static int LIMIT = 10000000;
    
    int block;
    
    static int counter;
    static Object object;
    
    public static void main(String[] args) 
    {
      counter = 0;
      object = new Object();
      threads = 10;
      Thread[] threadArray = new Thread[threads];
      for(int i=0; i<threads; i++)
      {
        threadArray[i] = new Thread(new Synchronization(i));
      }
      
      long before = System.currentTimeMillis();
      for(int j=0; j<threads; j++)
      {
        threadArray[j].start();
      }
      
      try
      {
        for(int k=0; k<threads; k++)
        {
          threadArray[k].join();
        }
      }catch(InterruptedException e)
      {
        System.out.println("InterruptedException caught!");
      }
      
      long after = System.currentTimeMillis();
      System.out.println(after-before);
      System.out.println(LIMIT);
      System.out.println(counter);
    }
    
    void addOne() 
    {
      synchronized (object)
      {
        Synchronization.counter++;
      }
    }
    
    void computeRange(int lower, int upper) 
    {
      for (int i = lower; i < upper; i++)
      {
        Math.hypot(Math.sin(i), Math.cos(i));
        addOne();
      }
    }
        
    public Synchronization(int block)
    {
        this.block = block;
    }
    
    public void run() 
    {        
      int size = LIMIT/threads;
      int lower = block * size;
      int upper = lower + size;
      
      computeRange(lower,upper);
    }
}