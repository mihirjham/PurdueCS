import java.util.Scanner;

public class BMV
{
	public static void main(String args[])
	{
		SimpleQueue<String> queue = new SimpleQueue();
		Scanner scanner = new Scanner(System.in);
		String inputChoice;
		String name; 

		do
		{
			System.out.print("(1) Enter a name into the queue\n(2) Get the next person in line\n(3) Display the entire queue\n(4) Shutdown\n");
			inputChoice = scanner.next();

			if(inputChoice.equals("1"))
			{
				System.out.print("Enter name: ");
				name = new Scanner(System.in).nextLine();
				queue.enqueue(name);
			}
			else if(inputChoice.equals("2"))
			{
				try
				{
					System.out.println("Next visitor: " + queue.dequeue());
				}
				catch(RuntimeException e)
				{
					System.out.println("There are no more visitors");
				}
			}
			else if(inputChoice.equals("3"))
			{
				if(queue.iterator().hasNext())
				{
					System.out.println("Current line:");
					for(String nextInLine : queue)					
					{
						System.out.println(nextInLine);
					}
				}
				else
				{
					System.out.println("There are no more visitors");
				}
			}
			else if(inputChoice.equals("4"))
			{
				System.out.println("Goodbye!");
				System.exit(0);
			}
			else
			{
				System.out.println("Please choose a valid choice from the list");
			}
		}while(!(inputChoice.equals("4")));
	}
}



