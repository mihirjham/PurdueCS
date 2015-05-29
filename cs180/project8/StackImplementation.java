/**
 * StackImplementation
 * This is the stack data structure we created for this milestone.
 * It contains the methods: isEmpty(), popElement(), pushElement()
 * This data structure is not limited to any type of class object,
 * thus, the reason for using the Object data type.
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
class StackImplementation
{
  private Node firstElement = null; //Setting the first element to null initially
  
  /**
   * isEmpty()
   * Checks whether the stack is empty or not
   *
   * @param none
   * 
   * @return the boolean value of whether the stack is empty or not
   */
  public boolean isEmpty()
  {
    return firstElement == null;
  }
  
  /**
   * popElement()
   * Removes elements from the stack data structure in the LIFO format.
   * Also checks whether the stack is empty or not, and throws appropriate messages
   * if trying to pop from the stack.
   *
   * @param none
   * 
   * @return the popped object
   */
  public Object popElement() throws StackEmptyException
  {
    if(isEmpty())
    {
      throw new StackEmptyException();
    }
    else
    {
      Object tempElement = firstElement.value;
      firstElement = firstElement.next;
      return tempElement;
    }
  }
  
  /**
   * pushElement()
   * Adds elements to the stack data structure in the LIFO format.
   *
   * @param the Object that is needed to be added to the stack data structure
   * 
   * @return none
   */
  public void pushElement(Object object)
  {
    firstElement = new Node(object, firstElement);
  }
}
