import java.util.Comparator;
import java.util.Arrays;

class FastConvexHull 
{
	private Point points[];
	private int numPoints;
	private int numPointsAdded;
	private Point convexHull[];
	private int convexPoints;

	public Point[] getConvexHull()
	{
		return this.convexHull;
	}
	
	public Point[] getPoints()
	{
		return this.points;
	}	
	public int getConvexPoints()
	{
		return this.convexPoints;
	}
	private Comparator<Point> byXAscending = new Comparator<Point>()
	{
		public int compare(Point a, Point b)
		{
			if(a.getX() <  b.getX())
				return -1;
			else if(a.getX() > b.getX())
				return 1;
			else
			{
				if(a.getY() < b.getY())
					return -1;
				else if(a.getY() > b.getY())
					return 1;
			}
			return 0;
		}
	};

	private Comparator<Point> byXDescending = new Comparator<Point>()
	{
		public int compare(Point a, Point b)
		{
			if(b.getX() <  a.getX())
				return -1;
			else if(b.getX() > a.getX())
				return 1;
			else
			{
				if(b.getY() < a.getY())
					return -1;
				else if(b.getY() > a.getY())
					return 1;
			}
			return 0;
		}
	};

	public FastConvexHull(int n)
	{
		this.numPoints = n;
		points = new Point[this.numPoints];
		this.numPointsAdded = 0;
		this.convexHull = new Point[this.numPoints];
		this.convexPoints = 0;
	}

	public void add(double x, double y)
	{
		if(numPoints - numPointsAdded <= 0)
			throw new RuntimeException("Cannot add any more points.");

		this.points[numPointsAdded++] = new Point(x,y);
	}

	public void addPoints()
	{
		for(int i=0; i < this.numPoints; i++)
		{
			String readLine = StdIn.readLine();
			String line[] = readLine.split(" ");
			add(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
			this.convexHull[i] = null;
		}
	}

	public void sort(boolean ascending)
	{
		if(ascending)
			Arrays.sort(this.points, byXAscending);
		else
			Arrays.sort(this.points, byXDescending);
	}

	public void printPoints()
	{
		for(int i=0; i < this.convexPoints; i++)
		{
			if(this.convexHull[i] == null)
				continue;

			System.out.print("id");
			System.out.print(this.convexHull[i].getIndex());
			System.out.print("(");
			System.out.print(this.convexHull[i].getX());
			System.out.print(",");
			System.out.print(this.convexHull[i].getY());
			System.out.print(")");

			if(i == convexPoints-1)
				System.out.println();
			else
				System.out.print(",");
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

	public void computeConvexHull()
	{
		sort(true);
		Deque<Point> lower = new Deque<Point>();
		Deque<Point> upper = new Deque<Point>();

		for(int i = 0; i < this.numPoints; i++)
		{
			while(lower.size() >=2 && whichSide((Point)lower.tail.prev.item, (Point)lower.tail.item, this.points[i]) <= 0)
				lower.removeLast();
			lower.addLast(this.points[i]);
		}
		lower.removeLast();

		sort(false);
		for(int i = 0; i < this.numPoints; i++)
		{
			while(upper.size() >=2 && whichSide((Point)upper.tail.prev.item, (Point)upper.tail.item, this.points[i]) <= 0)
				upper.removeLast();
			upper.addLast(this.points[i]);
		}
		upper.removeLast();
		
		int uSize = upper.size();
		
		for(int i = 0; i < uSize; i++)
		{
			lower.addLast(upper.removeFirst());
		}
		
		Node<Point> start = lower.head;
		Node<Point> elem = start.next;

		int i = 1;
		int lSize = lower.size();
		while(i < lSize)
		{
			if(elem.item.getIndex() < start.item.getIndex())
				start = elem;
			
			elem = elem.next;
			i++;
		}
		
		Node<Point> src = start;
		Node<Point> dest = start.next;
		
		this.convexHull[convexPoints++] = (Point) src.item;
		
		if(dest == null)
			dest = lower.head;

		while(dest.item.getIndex() != start.item.getIndex())
		{
			this.convexHull[convexPoints++] = (Point) dest.item;

			dest = dest.next;
			if(dest == null)
				dest = lower.head;
		}

	}

		public static void main(String args[])
		{
			FastConvexHull convex = new FastConvexHull(Integer.parseInt(StdIn.readLine()));
			convex.addPoints();
			convex.computeConvexHull();
			convex.printPoints();
		}
	}
