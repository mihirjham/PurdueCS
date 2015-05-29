/**
 * Node
 * This class holds the objects and their respective positions in the stack data structure.
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
class Node
{
  public Node next;    //The reference to the next object in the stack
  public Object value; //The Object that is being stored at the current location
  
  /**
   * Constructor()
   * Calls the other constructor with the specific position reference
   *
   * @param Object that is being stored
   * 
   * @return Node object
   */
  public Node(Object object)
  { 
    this(object, null);
  }
  
  /**
   * Constructor()
   * Constructor that creates the Reference object, that stores both the object and the reference to the next object
   *
   * @param Object that is being stored
   * @param Reference to the next object in the stacks
   * 
   * @return Node object
   */
  public Node(Object object, Node next)
  {
    this.next = next;
    this.value = object;
  }
} 
