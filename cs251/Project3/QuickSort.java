import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;
import java.io.*;

public class QuickSort
{
	int numberOfIntegers, swaps, comparisions;
	int array[];
	long timeTaken;
	Random rnd;
	SortAnalysis analysis;

	public QuickSort()
	{
		Scanner scanner = new Scanner(System.in);

		this.numberOfIntegers = scanner.nextInt();

		this.array = new int[this.numberOfIntegers];

		for(int i=0; i<this.numberOfIntegers; i++)
		{
			this.array[i] = scanner.nextInt();
		}
		
		this.rnd = new Random();
		this.analysis = new SortAnalysis();
	}
	
	public int partition(int a[], int first, int last)
	{
		int pivot = first + rnd.nextInt(last - first);
		
		swap(a, pivot, first);
		pivot = first;
		
		while(compare(first, last) == -1)
		{
			if(compare(a[first], a[last]) == -1 || compare(a[first],a[last])==0)
			{
				swap(a, pivot, first);
				pivot++;
			}

			first++;
		}

		swap(a,pivot,last);

		return pivot;
	}
	
	public void sort()
	{
		this.analysis.startTime();
		sort_r(this.array, 0, numberOfIntegers-1);
		this.timeTaken = this.analysis.timeTaken();
		this.swaps = this.analysis.getSwaps();
		this.comparisions = this.analysis.getComparisions();
	}

	public void sort_r(int a[], int first, int last)
	{	
		if(compare(first,last) == -1)
		{	
			int pivot = partition(a, first, last);

			sort_r(a, first, pivot -1);
			sort_r(a, pivot+1, last);
		}
	}

	public void swap(int a[], int dex1, int dex2)
	{
		int temp = a[dex1];
		a[dex1] = a[dex2];
		a[dex2] = temp;
		this.analysis.incrementSwap();
	}

	public int compare(int dex1, int dex2)
	{
		this.analysis.incrementComparisions();
		if(dex1 < dex2)
		{	
			return -1;
		}
		else if(dex1 > dex2)
		{
			return 1;
		}
		else
		{	
			return 0;
		}
	}

	public void print()
	{
	      	DecimalFormat df = new DecimalFormat("#.###");
	      	double timetaken = (double)this.timeTaken / 1000000000.0;
	      	String time = df.format(timetaken);
	      	System.out.println(time);
	      	System.out.println(this.comparisions);
	        System.out.println(this.swaps);
		
		FileOutputStream outStream = null;
		File inFile;
		PrintWriter pw;

		try
		{
			inFile = new File("quicksort.txt");
			outStream = new FileOutputStream(inFile);
		}
		catch(FileNotFoundException fnfevt)
		{
			System.out.println("File creation error");
		}

		try
		{
			pw = new PrintWriter(outStream);
			pw.println(this.numberOfIntegers);
			for(int i=0; i<this.numberOfIntegers; i++)
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
		QuickSort qsort = new QuickSort();
		qsort.sort();
		qsort.print();
	}
}


