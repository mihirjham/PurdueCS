import java.util.Scanner;
import java.text.DecimalFormat;
import java.io.*;

public class BubbleSort
{
	int numberOfIntegers;
	int array[];
	int swaps, comparisions;
	long timeTaken;
	
	public BubbleSort()
	{
		Scanner scanner = new Scanner(System.in);
	
		this.numberOfIntegers = scanner.nextInt();
		this.array = new int[numberOfIntegers];
		
		for(int i = 0; i < numberOfIntegers; i++)
		{
			this.array[i] = scanner.nextInt();
		}
	}

	public void sort()
	{
		SortAnalysis analysis = new SortAnalysis();
		analysis.startTime();

		for(int i = 0; i < numberOfIntegers; i++)
		{
			boolean done = true;

			for(int j = numberOfIntegers-1; j > 0; j--)
			{
				analysis.incrementComparisions();

				if(array[j] < array[j-1])
				{
					int temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
					done = false;
					analysis.incrementSwap();
				}
			}

			if(done)
			{
				timeTaken = analysis.timeTaken();
				swaps = analysis.getSwaps();
				comparisions = analysis.getComparisions();
				break;
			}
		}
	}

	public void print()
	{
		DecimalFormat df = new DecimalFormat("#.###");
		double timetaken = (double)timeTaken / 1000000000.0;
		String time = df.format(timetaken);
		System.out.println(time);
		System.out.println(comparisions);
		System.out.println(swaps);

		FileOutputStream outStream = null;
		File inFile;
		PrintWriter pw;

		try
		{
			inFile = new File("bubblesort.txt");
			outStream = new FileOutputStream(inFile);
		}
		catch(FileNotFoundException fnfevt)
		{
			System.out.println("File creation error!");
		}

		try
		{
			pw = new PrintWriter(outStream);
			pw.println(numberOfIntegers);
			for(int i = 0; i < numberOfIntegers; i++)
			{
				pw.println(array[i]);
			}
		
			pw.close();
			outStream.close();
		}
		catch(IOException e)
		{	
			System.out.println("Writing error");
		}
	}

	public static void main(String args[])
	{
		BubbleSort bsort = new BubbleSort();
		bsort.sort();
		bsort.print();
	}
}
		

							
