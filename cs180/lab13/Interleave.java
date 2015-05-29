/**
 * Interleave
 * Illustrate thread interaction by allowing two threads to output
 * simultaneously to the console.
 *
 * @author Mihir jham
 * @cslogin mjham
 *
 * @recitation L04 Friday, 330-520pm
 *
 * @date 04/13/2012
 */
public class Interleave implements Runnable
{
    char c;
    
    public static void main(String[] args) 
    {
      Interleave a = new Interleave('a');
      Interleave b = new Interleave('b');
      Thread t1 = new Thread(a);
      Thread t2 = new Thread(b);
      t1.start();
      t2.start();
    }
    
    public Interleave(char c) 
    {
        this.c = c;
    }
    
    public void run() 
    {
        for(int i=0; i<100; i++)
        {
          for(int j=0; j<10000; j++)
          {
            double sine = Math.sin(j);
          }
          System.out.print(c);
        }
    }
}