/* Class name: Location
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Data Structure for Locations(Nodes). Also used to create Point objects on maps and fill the ovals.
 * 
 */
package mappapp;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Location {
  private static final int SIZE = 20;
  private final static float dash1[] = {3.0f};
  private final static BasicStroke dashed = new BasicStroke(1.0f, 
                                            BasicStroke.CAP_BUTT, 
                                            BasicStroke.JOIN_MITER, 
                                            3.0f, dash1, 0.0f);
  public static int atPos=0;
  public String name = null;
  int id;
  int x,y;

  private Point _point;
  private boolean _selected;
  static ArrayList<Point> points= new ArrayList();
  

  public Location(Point point) { 
    if(!isThisYou(point))
    {
      points.add(point);
      this.x = (int) point.getX();
      this.y = (int) point.getY();
      this.id = getId(point);
    }
  }
 
  public Location(int id, String name, int x, int y)
  {
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
  }
  
  public Location()
  {
  }
  
  public int getId(Point p)
  {
    int t=points.indexOf(p);
    if(MapEditor.maxSize > t)
      t = MapEditor.maxSize;
    return t;
  }
  
  

  public void draw(Graphics g) {
    for(int i=0;i<points.size();i++)
    {
      g.setColor(Color.RED);
      g.fillOval((int) (points.get(i)).getX() - SIZE/2, 
               (int) (points.get(i)).getY() - SIZE/2, 
               SIZE, SIZE);

    }

  }

  public void drawSelect(Graphics g) {

    try
    {
      
       Graphics2D g2 = (Graphics2D) g;
       g2.setColor(Color.BLACK);
       g2.setStroke(dashed);
       g.drawRect((int) points.get(atPos).getX() - SIZE/2,
                  (int) points.get(atPos).getY() - SIZE/2,
                  SIZE, SIZE);
    }
       catch(IndexOutOfBoundsException e)
       {
       
       }
     
  }
  
   public void drawLine(Graphics2D g )
  {
  // Draw the line
      g.setColor(Color.BLUE);
      g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
  }

  /**
   * Return true if this point is inside of this location.
   */
  public boolean isThisYou(Point p) 
  {
    int i=0;
    boolean found=false;
    atPos=0;
    while(i<points.size() && !found)
    {
      int x = (int) (points.get(i)).getX();
      int y = (int) (points.get(i)).getY();
      int px = (int) p.getX();
      int py = (int) p.getY();
      int radius = SIZE/2;
      found = px > x - radius && px < x + radius && 
        py > y - radius && py < y + radius;
      if(found==true)
      {
        atPos=i;
        return found;
      }
      else
      {
        i++;
      }
    }
    return found;
    
  }
}
