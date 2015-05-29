class ExpressionTree {
  ExpressionTree tree;
  Node node;
  int evaluated;
  
  public static void inOrder(Node node){
    if(node==null){return;}
    System.out.print("(");
    inOrder(node.left);
    System.out.print(node.value);
    inOrder(node.right);
    System.out.print(")");
  }
  
  public static void preOrder(Node node) {
     if(node==null){return;}
    System.out.print(node.value);
    preOrder(node.left);
    preOrder(node.right);
  }
  
  public static void postOrder(Node node) {
    if(node==null){return;}
    postOrder(node.left);
    postOrder(node.right);
    System.out.print(node.value);
  }
}