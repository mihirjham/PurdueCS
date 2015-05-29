import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;
import java.io.*;

public class QuickSortOpt
{
	int numberOfIntegers, swaps, comparisions;
	int array[];
	long timeTaken;
	Random rnd;
	SortAnalysis analysis;
	public final int THRESHOLD = 30;

	public QuickSortOpt()
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
			return 0;
	}

	public void sort()
	{
		this.analysis.startTime();
		sort_r(this.array, 0, numberOfIntegers-1);
		sort_selection(this.array, numberOfIntegers);
		this.timeTaken = this.analysis.timeTaken();
		this.swaps = this.analysis.getSwaps();
		this.comparisions = this.analysis.getComparisions();
	}

	public void sort_r(int a[], int first, int last)
	{
		if(compare(last-first, THRESHOLD) == 1)
		{	
			if(isSorted(a, this.numberOfIntegers))
				return;
				
			if(compare(last,first) == -1 || compare(last,first) == 0)
				return;

			if(compare(a[last],a[first]) == -1)
				swap(a, first, last);

			int lt = first+1;
			int gt = last-1;
			int i = first+1;
			while(compare(i,gt) == -1 || compare(i,gt) == 0)
			{
				if(compare(a[i],a[first]) == -1)
					swap(a, lt++, i++);
				else if(compare(a[last],a[i]) == -1)
					swap(a, i, gt--);
				else
					i++;
			}

			swap(a, first, --lt);
			swap(a, last, ++gt);

			sort_r(a, first, lt-1);
			if(compare(a[lt], a[gt]) == -1)
				sort_r(a, lt+1, gt-1);
			sort_r(a, gt+1, last);
		}
	}	

	public void sort_selection(int a[], int numberOfIntegers)
	{
		if(isSorted(a, numberOfIntegers))
			return;
		
		for(int currentPlace = 0; currentPlace < numberOfIntegers-1; currentPlace++)
		{
			int smallest = Integer.MAX_VALUE;
			int smallestAt = currentPlace + 1;
			
			for(int check = currentPlace; check < numberOfIntegers; check++)
			{
				if(compare(a[check], smallest) == -1)
				{
					smallestAt = check;
					smallest = a[check];
				}
			}

			swap(a, currentPlace, smallestAt);
		}
	}

	public boolean isSorted(int a[], int n)
	{
		for(int i=0; i < n-1; i++)
		{
			if(compare(a[i], a[i+1]) == 1)	
				return false;
		}

		return true;
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
			inFile = new File("quicksort_opt.txt");
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
		QuickSortOpt qsort = new QuickSortOpt();
		qsort.sort();
		qsort.print();
	}
}
