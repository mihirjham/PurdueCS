class SAP
{
	private Digraph digraph;

	public SAP(Digraph digraph)
	{
		this.digraph = digraph;
	}

	public int length(int v, int w)
	{
		BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph, w);

		int min, dist;
		min = -1;

		for(int i = 0; i < digraph.V(); i++)
		{
			if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i))
			{
				dist = bfs1.distTo(i) + bfs2.distTo(i);
				if(min < 0 || dist < min)
				{
					min = dist;
				}
			}
		}

		return min;
	}

	public int ancestor(int v, int w)
	{
		BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, v);
		BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph, w);

		int min = -1;
		int dist;
		int ancestor = -1;

		for(int i = 0; i < digraph.V(); i++)
		{
			if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i))
			{
				dist = bfs1.distTo(i) + bfs2.distTo(i);
				if(min < 0 || dist < min)
				{
					min = dist;
					ancestor = i;		
				}
			}
		}

		return ancestor;
	}


	public static void main(String args[])
	{
		In graph = new In(args[0]);
		Digraph digraph = new Digraph(graph);
		In input = new In(args[1]);
		
		SAP sap = new SAP(digraph);
		
		while(input.hasNextLine())
		{
			String line = input.readLine();
			String values[] = line.split(" ");
			int v= Integer.parseInt(values[0]);
			int w = Integer.parseInt(values[1]);
			
			int min = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);

			System.out.printf("sap = %d, ancestor = %d\n", min, ancestor);
		}
	}
}
