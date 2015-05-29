/**
 * StackEmptyException
 * This is an Exception class, which is called when the stack is empty
 * 
 * @author Enchong Wang 
 * @cs login Wang977
 * @recitation REC4 recitation instructor's name Sriram, Dinesh
 * 
 * @author Haoran Lin
 * @cs login lin251
 * @* @recitation REC1 recitation instructor's name Ganasekaran,Bala
 * 
 * @author Mihir Jham
 * @cs login mjham
 * @recitation REC4 recitation instructor's name Sriram, Dinesh
 * 
 * @date 04/26/2012
 *
 */
public class StackEmptyException extends Exception
{
  public StackEmptyException()
  {
    super("Stack is empty");
  }
}