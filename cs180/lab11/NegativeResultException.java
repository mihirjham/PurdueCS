public class NegativeResultException extends Exception
{
  int result; 
  
  public NegativeResultException(int result)
  {
      super("A negative result was obtained by this operation!");
      this.result = result;
  }
}
