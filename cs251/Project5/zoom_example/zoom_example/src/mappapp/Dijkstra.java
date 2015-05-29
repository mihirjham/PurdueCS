/* Class name: Dijkstra
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Computes the shortest distance of 2 connected nodes using Dijkstra's Algorithm.
 * 
 * Referenced from: http://en.literateprograms.org/Dijkstra's_algorithm_(Java)?oldid=15444
 */
package mappapp;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Vertex implements Comparable<Vertex>
{
  public final String name;
  public int id;
  public ArrayList<Edge> adjacencies = new ArrayList<Edge>();
  public double minDistance = Double.POSITIVE_INFINITY;
  public Vertex previous;
  public Vertex(String argName, int id) { name = argName; this.id = id; }
  public String toString() { return name; }
  public int compareTo(Vertex other)
  {
    return Double.compare(minDistance, other.minDistance);
  }
  
}

class Edge
{
  public Vertex target = null;
  public final int targetId;
  public final double weight;
  public Edge(int argTarget, double argWeight, ArrayList<Vertex> vertices)
  { 
    targetId = argTarget; 
    weight = argWeight;
    updateTarget(vertices);
  }
  
  public void updateTarget(ArrayList<Vertex> vertices)
  {
    for(int i = 0; i < vertices.size(); i++)
    {
      if(targetId == vertices.get(i).id)
      {
        target = vertices.get(i);
        break;
      }
    }
  }
}

public class Dijkstra
{
  
  public ArrayList<Path> result = null;
  public double minDistance = 0.0;
  
  public static void computePaths(Vertex source)
  {
    source.minDistance = 0.;
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);
    
    while (!vertexQueue.isEmpty()) {
      Vertex u = vertexQueue.poll();
      
      // Visit each edge exiting u
      for (Edge e : u.adjacencies)
      {
        Vertex v = e.target;
        double weight = e.weight;
        double distanceThroughU = u.minDistance + weight;
        if (distanceThroughU < v.minDistance) {
          vertexQueue.remove(v);
          
          v.minDistance = distanceThroughU ;
          v.previous = u;
          vertexQueue.add(v);
        }
      }
    }
  }
  
  public static List<Vertex> getShortestPathTo(Vertex target)
  {
    List<Vertex> path = new ArrayList<Vertex>();
    for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
      path.add(vertex);
    
    Collections.reverse(path);
    return path;
  }
  
  public Dijkstra(ArrayList<Location> locationList, ArrayList<Path> pathList, int idFrom, int idTo)
  {
    ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    int idFromIndex = 0;
    int idToIndex = 0;
    
    for(int i = 0; i < locationList.size(); i++)
    {
      if(locationList.get(i).name == null)
        vertices.add(new Vertex("", locationList.get(i).id));
      else
        vertices.add(new Vertex(locationList.get(i).name, locationList.get(i).id));
    }
    
    for(int i = 0; i < vertices.size(); i++)
    {
      for(int j = 0; j < pathList.size(); j++)
      {
        if(vertices.get(i).id == pathList.get(j).idFrom)
          vertices.get(i).adjacencies.add(new Edge(pathList.get(j).idTo, pathList.get(j).distance, vertices));
        else if(vertices.get(i).id == pathList.get(j).idTo)
          vertices.get(i).adjacencies.add(new Edge(pathList.get(j).idFrom, pathList.get(j).distance, vertices));
      }
    }
    
    for(int i = 0; i < vertices.size(); i++)
    {
      if(idFrom == vertices.get(i).id)
      {
        idFromIndex = i;
        break;
      }
    }
    
    for(int i = 0; i < vertices.size(); i++)
    {
      if(idTo == vertices.get(i).id)
      {
        idToIndex = i;
        break;
      }
    }
  
    
    computePaths(vertices.get(idFromIndex));
    
    List<Vertex> path = getShortestPathTo(vertices.get(idToIndex));
    result = new ArrayList<Path>();
    int i = 1;
    while(i < path.size())
    {
      result.add(new Path("undirected", path.get(i-1).id, path.get(i).id));
      i++;
    }
    minDistance = vertices.get(idToIndex).minDistance;
  }
}
