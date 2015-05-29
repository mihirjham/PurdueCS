/**
 * Speedup
 * Use multiple threads to speed the computation of computeRange(...).
 *
 * @author Mihir jham
 * @cslogin mjham
 *
 * @recitation L04 Friday, 330-520pm
 *
 * @date 04/13/2012
 */

/*
 * How to devide the computational work?
 * 
 * Single Thread:
 * call computeRange(0, LIMIT); 
 * 
 * Multithread:
 * If there are 10 threads, for example.
 * Thread 1 calls computeRange(0, LIMIT/10)
 * Thread 2 calls computeRange(LIMIT/10, 2 * LIMIT/10)
 * Thread 3 calls computeRange(2 * LIMIT/10, 3 * LIMIT/10) ...
 * 
 */ 
public class Speedup extends Thread
{
    static int threads; // the total number of threads
    final static int LIMIT = 10000000;
    
    int block; // It can be understood as the index of thread. block ranges from 0 to (threads - 1).
   
    public static void main(String[] args) 
    {
        threads = 4;
        Thread[] threadArray = new Thread[threads];
        for(int i=0; i<threads; i++)
        {
          threadArray[i] = new Thread(new Speedup(i));
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
          
    }
    
    public static void computeRange(int lower, int upper) 
    {
        for (int i = lower; i < upper; i++)
            Math.hypot(Math.sin(i), Math.cos(i));
    }
        
    public Speedup(int block) 
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

