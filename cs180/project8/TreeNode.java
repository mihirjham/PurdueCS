/**
 * TreeNode
 * This class holds the objects and the respective positions in the Tree,
 * and it stores the value of the Object current.
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

class TreeNode
{
  TreeNode leftNode;  //Left leaf of the Tree
  TreeNode rightNode; //Right leaf of the tree
  double result;      //Numerical value pf the object, if any
  Object value;       //Generic value of the current stored object
  
  /**
   * Constructor()
   * Calls the other constructor with the specific position reference
   *
   * @param TreeNode leftNode
   * @param TreeNode rightNode
   * @param Object Generic object
   * 
   * @return TreeNode object
   */
  public TreeNode(TreeNode leftNode, TreeNode rightNode, Object value)
  {
    this(leftNode, rightNode, value, 0);
  }
  
  /**
   * Constructor()
   * Constructor that creates the Reference object of both leafs, and stores the object, and its numerical value(if any)
   *
   * @param TreeNode leftNode
   * @param TreeNode rightNode
   * @param Object Generic object
   * @param Numerical value, if any
   * 
   * @return TreeNode object
   */
  public TreeNode(TreeNode leftNode, TreeNode rightNode, Object value, double result)
  {
    this.leftNode = leftNode;
    this.rightNode = rightNode;
    this.value = value;
    this.result = result;
  }
}
