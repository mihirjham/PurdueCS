class SortAnalysis
{
	private long startTime;
	private int numberOfSwaps;
	private int numberOfComparisions;
	private long timeTaken;

	public SortAnalysis()
	{	
		this.numberOfSwaps = 0;
		this.numberOfComparisions = 0;
	}
	
	public void incrementSwap()
	{
		this.numberOfSwaps++;
	}
	
	public void decrementSwap()
	{
		this.numberOfSwaps--;
	}
	
	public void incrementComparisions()
	{	
		this.numberOfComparisions++;
	}
	
	public void decrementComparisions()
	{	
		this.numberOfComparisions--;
	}
	
	public void startTime()
	{
		this.startTime = System.nanoTime();
	}

	public long timeTaken()
	{	
		return System.nanoTime() - startTime;
	}
	
	public int getSwaps()
	{
		return this.numberOfSwaps;
	}
	
	public int getComparisions()
	{	
		return this.numberOfComparisions;
	}
}

