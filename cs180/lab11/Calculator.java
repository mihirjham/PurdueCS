public class Calculator
{
 public int add(int arg1, int arg2) throws NegativeArgumentException
 {
  if(arg1 < 0 || arg2 < 0)
    throw new NegativeArgumentException();
  
  int result = arg1 + arg2;
  return result;
 }
 
 public int subtract(int arg1, int arg2) throws NegativeArgumentException, NegativeResultException
 {
   if(arg1 < 0 || arg2 < 0)
     throw new NegativeArgumentException();
   
   int result = arg1 - arg2;
   if(result < 0)
     throw new NegativeResultException(result);
   return result;
 }
 
 public int multiply(int arg1, int arg2) throws NegativeArgumentException
 {
   if(arg1 < 0 || arg2 < 0)
     throw new NegativeArgumentException();
   int result = arg1 * arg2;
   return result;
 }
 
 public int divide(int arg1, int arg2) throws NegativeArgumentException, ArithmeticException
 {
   if(arg1 < 0 || arg2 < 0)
     throw new NegativeArgumentException();
   
   int result = arg1 / arg2;
   return result;
 }
 
}
