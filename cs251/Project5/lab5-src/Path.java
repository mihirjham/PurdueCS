class Path
{
  private String type;
  private int idFrom, idTo;
  
  public Path(String type, int idFrom, int idTo)
  {
    this.type = type;
    this.idFrom = idFrom;
    this.idTo = idTo;
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
  
  
     
  