import java.util.Scanner;

public class HelloWorld
{
	public static void main(String args[])
	{
		Runner run = new Runner();
		run.readUserInput();
	}
}

class Runner
{
	int numberOfChars;
	int numberOfPrints;
	int asciiValues[];

	public void readUserInput()
	{
		Scanner input = new Scanner(System.in);
		
		while(input.hasNext())
		{
			numberOfChars = input.nextInt();
			asciiValues = new int[numberOfChars];

			for(int i=0; i<numberOfChars; i++)
			{
				asciiValues[i] = input.nextInt();
			}

			numberOfPrints = input.nextInt();

			displayData();
		}
	}
	
	public void displayData()
	{
		for(int i=0; i<numberOfPrints; i++)
		{
			System.out.print("HelloWorld");

			for(int j=0; j<numberOfChars; j++)
			{
				System.out.print((char)asciiValues[j]);
			}
			System.out.print(" ");
		}

		System.out.print("\n");
	}
}
