class Edge
{
	private Point src;
	private Point dest;

	public Edge(Point src, Point dest)
	{
		this.src = src;
		this.dest = dest;
	}

	public Point getSrc()
	{
		return this.src;
	}

	public Point getDest()
	{
		return this.dest;
	}
}

class SlowConvexHull
{
	private Point points[];
	private int numPoints;
	private int numPointsAdded;
	private Edge convexHullEdges[];
	private int edgesAdded;

	public SlowConvexHull(int n)
	{
		this.numPoints = n;
		this.numPointsAdded = 0;
		this.points = new Point[numPoints];
		this.convexHullEdges = new Edge[numPoints];
		this.edgesAdded = 0;
	}

	public void add(double x, double y)
	{
		if(numPoints - numPointsAdded <= 0)
			throw new RuntimeException("Cannot add any more points.");

		this.points[numPointsAdded++] = new Point(x,y);
	}

	public void addPoints()
	{
		for(int i = 0; i < this.numPoints; i++)
		{
			String readLine = StdIn.readLine();
			String line[] = readLine.split(" ");
			add(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
		}
	}

	public int whichSide(double x0, double y0, double x1, double y1, double x, double y)
	{
		if(((x1-x0)*(y-y0))-((y1-y0)*(x-x0)) > 0)
			return 1;
		else if (((x1-x0)*(y-y0))-((y1-y0)*(x-x0)) == 0)
			return 0;
		else
			return -1;
	}

	public int whichSide(Point a, Point b, Point c)
	{
		return whichSide(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
	}

	private void computeConvexHull()
	{
		int i,j,k;

		for(i = 0; i < numPoints; i++)
		{
			for(j = 0; j < numPoints; j++)
			{
				for(k = 0; k < numPoints; k++)
				{
					if(k == i || k == j)
						continue;

					if(whichSide(points[i], points[j], points[k]) > 0)
						continue;
					else
						break;
				}

				if(k == numPoints)
				{	
					this.convexHullEdges[edgesAdded++] = new Edge(points[i], points[j]);
				}
			}
		}
	}

	public void printConvexHullSet()
	{
		computeConvexHull();

		Point initial = this.convexHullEdges[0].getSrc();
		Point src = this.convexHullEdges[0].getSrc();
		Point dest = this.convexHullEdges[0].getDest();
		
		System.out.print("id");
		System.out.print(src.getIndex());
		System.out.print("(");
		System.out.print(src.getX());
		System.out.print(",");
		System.out.print(src.getY());
		System.out.print(")");
		System.out.print(",");

		while(dest.getIndex() != initial.getIndex())
		{
			System.out.print("id");
			System.out.print(dest.getIndex());
			System.out.print("(");
			System.out.print(dest.getX());
			System.out.print(",");
			System.out.print(dest.getY());
			System.out.print(")");
			
			int j = 0;
			while(this.convexHullEdges[j].getSrc().getIndex() != dest.getIndex())
			{
				j++;
			}
			
			src = this.convexHullEdges[j].getSrc();
			dest = this.convexHullEdges[j].getDest();
			
			if(dest.getIndex() == initial.getIndex())
				System.out.println();
			else
				System.out.print(",");
		}
	}
	public static void main(String args[])
	{
		SlowConvexHull convexHull = new SlowConvexHull(Integer.parseInt(StdIn.readLine()));
		convexHull.addPoints();
		convexHull.printConvexHullSet();
	}
}
