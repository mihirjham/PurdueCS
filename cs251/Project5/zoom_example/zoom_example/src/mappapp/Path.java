/* Class name: Path
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Data Structure for Paths(Edges)
 * 
 */
package mappapp;

class Path
{
   String type;
   int idFrom, idTo;
   double distance;
  
  public Path(String type, int idFrom, int idTo, double distance)
  {
    this.type = type;
    this.idFrom = idFrom;
    this.idTo = idTo;
    this.distance=distance;
  }
  public Path(String type, int idFrom, int idTo)
  {
    this(type, idFrom, idTo, 0.0);
  }
  public void setType(String type)
  {
    this.type = type;
  }
  
  public void setIdFrom(int idFrom)
  {
    this.idFrom = idFrom;
  }
  
  public void setIdTo(int idTo)
  {
    this.idTo = idTo;
  }
  
  public void setPath(int idFrom, int idTo)
  {
    this.idFrom = idFrom;
    this.idTo = idTo;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public int getIdFrom()
  {
    return this.idFrom;
  }
  
  public int getIdTo()
  {
    return this.idTo;
  }
}



