class ConvexHullVisualizer
{
	private FastConvexHull fastConvexHull;
	private Point[] points;
	private Point[] convexHull;
	private int convexPoints;

	public ConvexHullVisualizer()
	{
		this.fastConvexHull = new FastConvexHull(Integer.parseInt(StdIn.readLine()));
		this.fastConvexHull.addPoints();
		this.fastConvexHull.computeConvexHull();
		this.convexHull = this.fastConvexHull.getConvexHull();
		this.convexPoints = this.fastConvexHull.getConvexPoints();
		this.points = this.fastConvexHull.getPoints();

		double maxX = this.points[0].getX();
		double minX = this.points[0].getX();
		double maxY = this.points[0].getY();
		double minY = this.points[0].getY();

		int i = 1;
		while(i < this.points.length)
		{
			if(maxX < this.points[i].getX())
				maxX = this.points[i].getX();

			if(minX > this.points[i].getX())
				minX = this.points[i].getX();

			if(maxY < this.points[i].getY())
				maxY = this.points[i].getY();

			if(minY > this.points[i].getY())
				minY = this.points[i].getY();

			i++;
		}


		if(maxX > 1.0 || minX > 1.0 || maxY > 1.0 || minY > 1.0)
		{
			double scale;
			if((maxX - minX) >= (maxY - minY))
				scale = maxX - minX;
			else
				scale = maxY - minY;

			for(i = 0; i < this.points.length; i++)
			{
				//this.points[i].setX(this.points[i].getX() - (maxX-minX)/2.0);
				this.points[i].setX(this.points[i].getX()/scale);
				//this.points[i].setY(this.points[i].getY() - (maxY-minY)/2.0);
				this.points[i].setY(this.points[i].getY()/scale);
			}

			for(i = 0; i < this.convexPoints; i++)
			{
				//this.convexHull[i].setX(this.convexHull[i].getX() - (maxX-minX)/2.0);
				this.convexHull[i].setX(this.convexHull[i].getX()/scale);
				//this.convexHull[i].setY(this.convexHull[i].getY() - (maxY-minY)/2.0);
				this.convexHull[i].setY(this.convexHull[i].getY()/scale);
			}
		}
	}

	public void draw()
	{
		for(int i = 0; i < this.points.length; i++)
		{
			StdDraw.filledCircle(this.points[i].getX(), this.points[i].getY(), 0.005);
		}

		int i = 1;
		for(i = 1; i < this.convexPoints; i++)
		{
			if(this.convexHull[i] == null)
				continue;

			StdDraw.line(this.convexHull[i-1].getX(), this.convexHull[i-1].getY(), this.convexHull[i].getX(), this.convexHull[i].getY());
			StdDraw.text(this.convexHull[i-1].getX(), this.convexHull[i-1].getY(), ""+this.convexHull[i-1].getIndex());
		}
		StdDraw.line(this.convexHull[i-1].getX(), this.convexHull[i-1].getY(), this.convexHull[0].getX(), this.convexHull[0].getY());
		StdDraw.text(this.convexHull[0].getX(), this.convexHull[0].getY(), ""+this.convexHull[0].getIndex());
		StdDraw.text(this.convexHull[i-1].getX(), this.convexHull[i-1].getY(),""+this.convexHull[i-1].getIndex());
	}
	public static void main(String args[])
	{
		ConvexHullVisualizer visualizer = new ConvexHullVisualizer();
		visualizer.draw();
	}
}	
