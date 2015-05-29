import java.util.Scanner;

public class RPNCalculator
{
	public static void main(String args[])
	{
		SimpleStack<String> stack = new SimpleStack();
		Scanner scanner = new Scanner(System.in);
		String input;

		try
		{
			while(scanner.hasNext())
			{
				input = scanner.next();
				if(input.equals("+") || input.equals("-") || input.equals("/") || input.equals("*"))
				{
					if(input.equals("+"))
					{
						int value1 = Integer.parseInt(stack.pop());
						int value2 = Integer.parseInt(stack.pop());
						int valnew = value2 + value1;
						stack.push(Integer.toString(valnew));
					}
				
					if(input.equals("-"))
					{
						int val1 = Integer.parseInt(stack.pop());
						int val2 = Integer.parseInt(stack.pop());
						int valnew = val2 - val1;
						stack.push(Integer.toString(valnew));
					}
				
					if(input.equals("/"))
					{
						int val1 = Integer.parseInt(stack.pop());
						int val2 = Integer.parseInt(stack.pop());
						int valnew = val2 / val1;
						stack.push(Integer.toString(valnew));
					}

					if(input.equals("*"))
					{
						int val1 = Integer.parseInt(stack.pop());
						int val2 = Integer.parseInt(stack.pop());
						int valnew = val1*val2;
						stack.push(Integer.toString(valnew));
					}
				}
				else if(input != null)
				{
					stack.push(input);
				}
				else
				{
					System.out.println("error");
				}	
			}

			System.out.println(stack.pop());
		}catch(RuntimeException e)
		{
			System.out.println("error");
		}
	}
}
			
			
