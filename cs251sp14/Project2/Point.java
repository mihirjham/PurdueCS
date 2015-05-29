class Point
{
	private double x;
	private double y;
	private int index;
	private static int indexCounter = 0;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.index = this.indexCounter++;
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public int getIndex()
	{
		return this.index;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}
}

