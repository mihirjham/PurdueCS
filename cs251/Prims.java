import java.util.ArrayList;
import java.util.Iterator;
/**
 * Finds the spanning tree of a undirected graph using prim's(dijsktra's) algorithm
 * 
 * @author srimadha
 * 
 */
public class Prims {

    public static void main(String args[]) {
        Prims p = new Prims();

        // populate vertices

        // set U
        ArrayList<Integer> u = new ArrayList<Integer>(1);
        u.add(1);
        p.setU_Vertices(u);

        // set V
        ArrayList<Integer> v = new ArrayList<Integer>(1);
        v.add(0);
        v.add(2);
        v.add(3);
        v.add(4);
        v.add(5);
        p.setV_Vertices(v);

        // populate Edges
        ArrayList<Edges> edges = new ArrayList<Edges>(1);

        Edges e0 = new Edges(0, 2, 300);
        edges.add(e0);
        Edges e1 = new Edges(0, 3, 80);
        edges.add(e1);
        Edges e2 = new Edges(0, 4, 50);
        edges.add(e2);

        Edges e4 = new Edges(1, 2, 70);
        edges.add(e4);
        Edges e5 = new Edges(1, 3, 75);
        edges.add(e5);
        Edges e6 = new Edges(1, 4, 200);
        edges.add(e6);

        Edges e7 = new Edges(2, 3, 60);
        edges.add(e7);

        Edges e8 = new Edges(3, 4, 90);
        edges.add(e8);

        p.setEdges(edges);

        p.computeSpanningEdges();

        ArrayList<Edges> result = p.getSpanning_edges();
        System.out.println("Below are the edges of Minimum spanning tree with respective wieghts");
        for (Iterator iterator = result.iterator(); iterator.hasNext();) {
            Edges edges2 = (Edges) iterator.next();
            System.out.println(edges2.getM() + "," + edges2.getN() + " = "
                    + edges2.getWeight());
        }
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