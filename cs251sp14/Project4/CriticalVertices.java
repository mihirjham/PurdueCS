import java.util.Arrays;
import java.util.Comparator;

class CriticalVertices
{
	private static Comparator<VerticeCounter> byBetween = new Comparator<VerticeCounter>()
	{
		public int compare(VerticeCounter a, VerticeCounter b)
		{
			if(a.between < b.between)
				return 1;
			else if(a.between > b.between)
				return -1;
			else
				return 0;
		}
	};

	private static Comparator<VerticeCounter> byClose = new Comparator<VerticeCounter>()
	{
		public int compare(VerticeCounter a, VerticeCounter b)
		{
			if(a.close < b.close)
				return -1;
			else if(a.close > b.close)
				return 1;
			else
				return 0;
		}
	};

	public static void main(String args[])
	{
		In inputGraph = new In(args[0]);
		int n = Integer.parseInt(args[1]);

		EdgeWeightedDigraph G = new EdgeWeightedDigraph(inputGraph);
		VerticeCounter vertices[] = new VerticeCounter[G.V()];
		
		for(int i = 0; i < G.V(); i++)
		{
			vertices[i] = new VerticeCounter(i);
		}

		for(int i = 0; i < G.V(); i++)
		{
			DijkstraSP sp = new DijkstraSP(G, i);
			
			for(int j = 0; j < G.V(); j++)
			{
				if(sp.hasPathTo(j))
				{
					vertices[i].addClose(sp.distTo(j));
					for(DirectedEdge e : sp.pathTo(j))
					{
						//vertices[e.from()].incrementBetween();
						if(j != e.to())
							vertices[e.to()].incrementBetween();
					}
				}
			}
		}
		
		
		if(G.V() < n)
			n = G.V();

		Arrays.sort(vertices, byBetween);
		System.out.printf("Vertices with high betweenness centrality: ");
		for(int i = 0; i < n; i++)
		{
			System.out.printf("%d", vertices[i].v);
			if(i != n-1)
				System.out.printf(", ");
		}
		System.out.printf("\n");

		Arrays.sort(vertices, byClose);
		System.out.printf("Vertices with high closeness centrality: ");
		for(int i = 0; i < n; i++)
		{
			System.out.printf("%d", vertices[i].v);
			if(i != n-1)
				System.out.printf(", ");
		}
		System.out.printf("\n");
	}

}

class VerticeCounter
{
	int v;
	int between;
	double close;

	public VerticeCounter(int v)
	{
		this.v = v;
		this.between = 0;
		this.close = 0.0;
	}
	
	public void incrementBetween()
	{
		this.between++;
	}
	
	public void addClose(double close)
	{
		this.close = this.close + close;
	}
}
