import java.util.Iterator;

class Palindrome
{
	public static void main(String args[])
	{
		Stack<Character> stack = new Stack<Character>();
		Iterator iterator = stack.iterator();
		int n = Integer.parseInt(StdIn.readLine());
		if(n < 0)
		{
			System.out.println("Invalid input: Number of strings cannot be non-negative");
			System.exit(0);
		}
		else if(n == 0)
		{
			System.out.println("No strings to check");
			System.exit(0);
		}
		else
		{
			System.out.println();
		}
		
		while(n>0)
		{
			String string = StdIn.readLine();
			char[] array = string.toCharArray();
			int i = 0;
			while(i < array.length)
			{
				stack.push(array[i]);
				i++;
			}
			
			i=0;
			int size = stack.size();
			boolean palindrome = true;
			while(i < size && i < array.length)
			{
				if(stack.pop() == array[i])
				{	
					i++;
					continue;
				}
				else
				{
					palindrome = false;
					break;
				}
			}
		  	if(palindrome)	
				System.out.println("Yes");
			else
				System.out.println("No");
			n--;
		}
	}
}
