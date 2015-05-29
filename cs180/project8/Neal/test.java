import java.util.*;
import java.io.*;
class test{
 
  public static void main(String[] args) {
    Stack stack=new Stack();
    double evaluated=0;
    try{
      File infile=new File("in_file4");
      FileInputStream inStream=new FileInputStream(infile);
      Scanner src=new Scanner(inStream);
      while(src.hasNext()){
        String content=src.nextLine();      
        String[] input=content.split(" ");
        Node tree=null;
        
        for(int i=0;i<input.length;i++)
        {
          if(input[i].equals("+")||input[i].equals("-")||input[i].equals("*")||input[i].equals("/"))
          {          
            Node nodeRight=stack.pop();
            Node nodeLeft=stack.pop();
            
            if(input[i].equals("+"))
              evaluated=nodeLeft.result+nodeRight.result;
            if(input[i].equals("-"))
              evaluated=nodeLeft.result-nodeRight.result;
            if(input[i].equals("*"))
              evaluated=nodeLeft.result*nodeRight.result;
            if(input[i].equals("/"))
              evaluated=nodeLeft.result/nodeRight.result;
                        
            tree=new Node(nodeLeft,nodeRight,input[i],evaluated);
            stack.push(tree);
          }
          else
          {
            evaluated=Double.parseDouble(input[i]);
            stack.push(new Node(null,null,input[i],evaluated));
          }
          //System.out.print(input[i]);
        }
        //System.out.print(",");
        ExpressionTree.inOrder(tree);
        System.out.print(",");
        ExpressionTree.postOrder(tree);
        System.out.print(",");
        ExpressionTree.preOrder(tree);
        System.out.print(", "+tree.result+"\n");
      }
      inStream.close();
      
    }catch(FileNotFoundException e){System.out.println("no file");
    }catch(IOException a){}
    
  }
  
  
}