/* Class name: Prims
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Computes the minimum spanning tree of the connected graph.
 * 
 * Referenced from http://srimadha1.blogspot.com/2013/01/prims-algorithm-implementation-in-java.html?view=classic
 */
package mappapp;
import java.util.ArrayList;
import java.util.Iterator;

public class Prims {
  
  public Prims(ArrayList<Location> locationList, ArrayList<Path> pathList)
  {
    ArrayList<Integer> u = new ArrayList<Integer>();
    u.add(locationList.get(0).id);
    setU_Vertices(u);
    
    ArrayList<Integer> v = new ArrayList<Integer>();
    for(int i = 1; i < locationList.size(); i++)
    {
      v.add(locationList.get(i).id);
    }
    setV_Vertices(v);
    
    ArrayList<Edges> edges = new ArrayList<Edges>();
    for(int i = 0; i < pathList.size(); i++)
    {
      edges.add(new Edges(pathList.get(i).idFrom, pathList.get(i).idTo, (int)pathList.get(i).distance));
    }
    setEdges(edges);
    computeSpanningEdges();
        
    ArrayList<Edges> result = getSpanning_edges();
    resultPath = getSpanningTree();
  }
  
  public ArrayList<Path> getSpanningTree()
  {
    ArrayList<Edges> result = getSpanning_edges();
    
    ArrayList<Path> spanningPathList= new ArrayList<Path>();
    
    for(int i = 0; i < result.size(); i++)
    {
      spanningPathList.add(new Path("undirected", result.get(i).getM(), result.get(i).getN()));
    }
    return spanningPathList;
  }

  private void computeSpanningEdges() {
    //V_vertices is empty then spanning tree is formed
    while (!V_vertices.isEmpty()) {
      int vertex_with_minweight = findMinEdgeVertex();
      V_vertices.remove((Integer) vertex_with_minweight);
      U_vertices.add((Integer) vertex_with_minweight);
    }
  }
  
  private int findMinEdgeVertex() {
    int min = INF;
    Edges minEdge = new Edges(0, 0, INF);
    for (int i = 0; i < U_vertices.size(); i++) {
      int u = U_vertices.get(i);
      Edges mEdge = getMinWeight(u);
      if (mEdge.getWeight() < min) {
        if (U_vertices.contains(mEdge.getM())
              && U_vertices.contains(mEdge.getN())) {
          //mEdge is already part of spanning tree so do nothing :-)
        } else {
          min = mEdge.getWeight();
          minEdge.setM(mEdge.getM());
          minEdge.setN(mEdge.getN());
          minEdge.setWeight(mEdge.getWeight());
        }
      }
    }
    edges.remove(minEdge);
    spanning_edges.add(minEdge);
    if (U_vertices.contains(minEdge.getN()))
      return minEdge.getM();
    else
      return minEdge.getN();
  }
  
  private Edges getMinWeight(int u) {
    int value = INF;
    Edges minEdge = new Edges(0, 0, INF);
    for (Iterator iterator2 = edges.iterator(); iterator2.hasNext();) {
      Edges type = (Edges) iterator2.next();
      if (type.getM() == u || type.getN() == u) {
        if (U_vertices.contains(type.getM())
              && U_vertices.contains(type.getN())) {
          //mEdge is already part of spanning tree so do nothing :-)
        } else {
          if (value > type.getWeight()) {
            value = type.getWeight();
            minEdge.setM(type.getM());
            minEdge.setN(type.getN());
            minEdge.setWeight(type.getWeight());
          }
        }
      }
    }
    return minEdge;
  }
  
  public ArrayList<Edges> getSpanning_edges() {
    return spanning_edges;
  }
  
  public void setSpanning_edges(ArrayList<Edges> spanning_edges) {
    this.spanning_edges = spanning_edges;
  }
  
  private ArrayList<Integer> U_vertices = new ArrayList<Integer>(1);
  private ArrayList<Integer> V_vertices = new ArrayList<Integer>(1);
  private ArrayList<Edges> edges = new ArrayList<Edges>();
  private ArrayList<Edges> spanning_edges = new ArrayList<Edges>();
  public ArrayList<Path> resultPath = null;
  
  public static int INF = 9999;
  
  public ArrayList<Integer> getU_Vertices() {
    return U_vertices;
  }
  
  public void setU_Vertices(ArrayList<Integer> vertices) {
    this.U_vertices = vertices;
  }
  
  public ArrayList<Edges> getEdges() {
    return edges;
  }
  
  public void setEdges(ArrayList<Edges> edges) {
    this.edges = edges;
  }
  
  public ArrayList<Integer> getV_Vertices() {
    return V_vertices;
  }
  
  public void setV_Vertices(ArrayList<Integer> v_vertices) {
    V_vertices = v_vertices;
  }
  
}

class Edges {
    private int m, n, weight;

    public Edges() {

    }

    public Edges(int m, int n, int weight) {
        this.m = m;
        this.n = n;
        this.weight = weight;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
