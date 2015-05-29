/**
 * Slowdown
 * Create a method that runs slowly.
 *
 *
 * @author Mihir jham
 * @cslogin mjham
 *
 * @recitation L04 Friday, 330-520pm
 *
 * @date 04/13/2012
 */
public class Slowdown
{
    final static int LIMIT = 10000000;

    public static void main(String[] args)
    {
      long before = System.currentTimeMillis(); 
  
      computeRange(0,LIMIT);

      long after = System.currentTimeMillis();
      System.out.println(after-before);
    }
    
    public static void computeRange(int lower, int upper) 
    {
        for (int i = lower; i < upper; i++)
            Math.hypot(Math.sin(i), Math.cos(i));
    }
}