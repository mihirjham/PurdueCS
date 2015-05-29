import java.lang.Math;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ListIterator;

class CriticalEdges
{
	private static Comparator<EdgeCounter> byCount = new Comparator<EdgeCounter>()
	{
		public int compare(EdgeCounter a, EdgeCounter b)
		{
			if(a.count < b.count)
				return 1;
			else if(a.count > b.count)
				return -1;
			else
				return 0;
		}
	};
	public static void main(String args[])
	{
		In inputGraph = new In(args[0]);
		int n = Integer.parseInt(args[1]);	
		int nVertices = inputGraph.readInt();
		int nEdges = inputGraph.readInt();
		EdgeCounter edges[] = new EdgeCounter[nEdges];
		double clusteringCoefficient = 0.0;
		for(int i = 0; i < nEdges; i++)
		{
			edges[i] = new EdgeCounter(inputGraph.readInt(), inputGraph.readInt());	
		}
		

		for(int j = 0; j < 50; j++)
		{
			EdgeWeightedGraph graph = new EdgeWeightedGraph(nVertices);
		
			for(int i = 0; i < nEdges; i++)
			{
				double weight = Math.random();
				graph.addEdge(new Edge(edges[i].v, edges[i].w, weight));
			}

			PrimMST mst = new PrimMST(graph);
			for(Edge e : mst.edges())
			{
				for(int i = 0; i < nEdges; i++)
				{
					if(e.either() == edges[i].v)
					{
						if(e.other(e.either()) == edges[i].w)
						{
							edges[i].incrementCount();
						}
					}
				}
			}
			
			if(j == 49)
			{
				double coefficients[] = new double[graph.V()];
				int size = 0;
				for(int k = 0; k < graph.V(); k++)
				{
					for(Edge e : graph.adj(k))
					{
						size++;
					}
					
					if(size <= 1)
					{
						coefficients[k] = 0.0;
					}
					else
					{
						int edgesInNhd = 0;
						for(Edge e : graph.adj(k))
						{
							for(Edge f : graph.adj(k))
							{
								if(e != f)
								{
									int v1 = e.other(k);
									int v2 = f.other(k);
									
									for(Edge x : graph.edges())
									{
										if(v1 == x.either())
										{
											if(v2 == x.other(v1))
											{
												edgesInNhd++;
											}
										}
										else if(v2 == x.either())
										{
											if(v1 == x.other(v2))
											{
												edgesInNhd++;
											}
										}
									}
								}
							}
						}
						
						double possibleEdges = (size*(size-1))/2.0;
						coefficients[k] = (edgesInNhd)/possibleEdges;
						coefficients[k] = coefficients[k]/2;
					}
					
					size = 0;
				}
				for(int k = 0; k < graph.V(); k++)
				{
					clusteringCoefficient = clusteringCoefficient + coefficients[k];
				}
				clusteringCoefficient = clusteringCoefficient/nVertices;
			}
		}
		
		Arrays.sort(edges, byCount);
		
		System.out.println("Top edges:");
		if(nEdges < n)
		{
			for(int i = 0; i < nEdges; i++)
			{
				System.out.printf("Edge %d-%d\n", edges[i].v, edges[i].w);
			}
		}
		else
		{
			for(int i = 0; i < n; i++)
			{
				System.out.printf("Edge %d-%d\n", edges[i].v, edges[i].w);
			}
		}
		System.out.printf("Clustering coefficient of graph: %f\n", clusteringCoefficient);
	}
}

class EdgeCounter
{
	int v;
	int w;
	int count;

	public EdgeCounter(int v, int w)
	{
		this.v = v;
		this.w = w;
		this.count = 0;
	}
	
	public void incrementCount()
	{
		this.count++;
	}
}
