/**
 * ExpressionTree
 * This class holds the root node, and methods to traverse the tree
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
 * @date 04/19/2012
 *
 */
class ExpressionTree
{
  TreeNode node; //Root node
  
  /**
   * Constructor()
   * Creates the beginning of a tree(or a sub-tree)
   *
   * @param TreeNode root node
   * 
   * @return ExpressionTree object
   */
  public ExpressionTree(TreeNode node)
  {
    this.node = node;
  }
 
  /**
   * inOrder()
   * Creates an expression in the in-order format, and does this recursively
   *
   * @param TreeNode root node
   * 
   * @return String that is in-order notation
   */
  public static String inOrder(TreeNode node)
  {
    String inorder = null;
    
    if(node == null)
    {
      return "";
    }
    
    inorder = "(" + inOrder(node.leftNode) + (String)node.value + inOrder(node.rightNode) + ")";
    
    return inorder;
  }
  
  /**
   * preOrder()
   * Creates an expression in the pre-order format, and does this recursively
   *
   * @param TreeNode root node
   * 
   * @return String that is pre-order notation
   */
  public static String preOrder(TreeNode node)
  {
    String preorder = null;
    
    if(node == null)
    {
      return "";
    }
    preorder = " " + (String)node.value + preOrder(node.leftNode) + preOrder(node.rightNode);
    
    return preorder;
  }
  
  /**
   * postOrder()
   * Creates an expression in the post-order format, and does this recursively
   *
   * @param TreeNode root node
   * 
   * @return String that is post-order notation
   */
  public static String postOrder(TreeNode node) 
  {
    String postorder = null;
    
    if(node == null)
    {
      return "";
    }
    
    postorder = postOrder(node.leftNode) + postOrder(node.rightNode) + (String)node.value + " ";
    
    return postorder;
  }
}
