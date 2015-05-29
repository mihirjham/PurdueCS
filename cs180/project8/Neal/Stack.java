public class Stack{
  private int size = 100;
  private int top = -1;
  private Node[] data = new Node[size];
  
  
  public void push (Node element)/* throws Exception*/{ 
//    if(top==(size-1))
//    {
//      throw new Exception ("stack is null " ) ;
//    }
    top ++; 
    data[top]=element;
  }
  public Node pop() /*throws Exception*/{ 
//    if(top==-1)
//    { 
//      throw new Exception ("stack is null") ;
//    }
    int curTop=top;
    top--;
    return data[curTop];
  }
  
  public boolean isEmpty () {
    return top == -1; 
  }
}
