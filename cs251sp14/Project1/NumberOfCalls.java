public class NumberOfCalls
{
	private RandomCollection<Integer> instantiate(int n)
	{
		RandomCollection<Integer> collection = new RandomCollection<Integer>();

		for(int i=1; i<=n; i++)
		{
			collection.addAnywhere(i);	
		}
		
		return collection;
	}
	
	private int getNumberOfCallsForComprehensiveCallout(RandomCollection<Integer> test, int n)
	{
		int count=0;

		boolean called[] = new boolean[n];
		for(int i=0; i < n; i++)
		{
			called[i] = false;
		}
		
		while(true)
		{	
			int value = test.callout();
			called[value-1] = true;
			count++;
			
			int i;
			for(i=0; i < n; i++)
			{
				if(called[i] == false)
					break;	
			}
			
			if(i==n)
				return count;
		}
	}
	
	public static void main(String args[])
	{
		NumberOfCalls program = new NumberOfCalls();
		
		while(StdIn.hasNextLine())
		{
			int n = Integer.parseInt(StdIn.readLine());
			RandomCollection<Integer> collection = program.instantiate(n);
			double mean = 0;
			for(int i=0; i<100000; i++)
			{
				mean = mean + program.getNumberOfCallsForComprehensiveCallout(collection, n);
			}
			
			mean = mean/100000;
			System.out.println(mean);
		}
	}
}
