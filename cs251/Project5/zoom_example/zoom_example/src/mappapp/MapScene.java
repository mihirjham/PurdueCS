/* Class name: Location
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Processor Class
 * 
 */
package mappapp;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.lang.Math;


/**
 * This class is where you keep track of all your locations and edges
 * and you draw them in the draw() method.
 */
public class MapScene implements Scene {
  private ChangeListener _listener;
  private Image _image;

  private static final int SIZE = 20;
  
  private Point _lineStart;
  private Point _lineEnd;
  private Point mouseL;
  public Location _loc,_lcopy;
  private boolean changeOccured = false;
  private boolean releaseOnLoc = false;

  ArrayList<Location> locationList = new ArrayList();
  ArrayList<Path> pathList = new ArrayList();
  ArrayList<Path> spanningPathList = new ArrayList();
  ArrayList<Path> shortestPathList = new ArrayList();
 
  private Graphics g;

  public MapScene(Image image) {
    _image = image;
  }
  
  /**
   * Call this method whenever something in the map has changed that
   * requires the map to be redrawn.
   */
  public void changeNotify() {
    if (_listener != null) _listener.stateChanged(null);
  }

  public boolean changeOccured()
  {
    return changeOccured;
  }

  /**
   * This method will draw the entire map.
   */
  public void draw(Graphics2D g) {
    // Draw the map image
    g.drawImage(_image, 0, 0, null);
    
    if(MapEditor.modeType==3)
    {
      drawLineVisual(g);
    }    
    
    if(spanningPathList != null )
    { 
      for(int i=0; i<spanningPathList.size();i++)
      {
        g.setColor(Color.YELLOW);
        g.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Path temp = (Path)spanningPathList.get(i);
        
        int idF = spanningPathList.get(i).idFrom;
        int idT = spanningPathList.get(i).idTo;

        Point start = new Point();
        start = getPoint(idF);
        Point end = new Point();
        end=getPoint(idT);
        if(start != null && end != null)
        {
          g.drawLine(start.x, start.y, end.x, end.y);
        }
        else
        {
          pathList.remove(i);
        }
      }
    }
   
    if(shortestPathList != null)
    { 
      for(int i=0; i<shortestPathList.size();i++)
      {
        g.setColor(Color.ORANGE);
        g.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Path temp = (Path)shortestPathList.get(i);
        
        int idF = shortestPathList.get(i).idFrom;
        int idT = shortestPathList.get(i).idTo;

        Point start = new Point();
        start = getPoint(idF);
        Point end = new Point();
        end = getPoint(idT);
        if(start != null && end != null)
        {
          g.drawLine(start.x, start.y, end.x, end.y);
        }
        else
        {
          pathList.remove(i);
        }
      }
    }
    
    try
    {
        //_loc.draw(g);
      
      for(int i=0;i<_loc.points.size();i++)
      {
        g.setColor(Color.RED);
        g.fillOval((int) (_loc.points.get(i)).getX() - SIZE/2, 
                   (int) (_loc.points.get(i)).getY() - SIZE/2, 
                   SIZE, SIZE);
        g.setColor(Color.green);
        g.drawString(Integer.toString(locationList.get(i).id),(int) (_loc.points.get(i)).getX() - SIZE/2, 
                   (int) (_loc.points.get(i)).getY() - SIZE/2);
        
      }
        _loc.drawSelect(g); 
        
    }
    catch(NullPointerException e)
    {
      
    }   
    
    for(int i=0; i<pathList.size();i++)
    {
      g.setColor(Color.BLUE);
      g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      Path temp = (Path)pathList.get(i);
      
      int idF = pathList.get(i).idFrom;
      int idT = pathList.get(i).idTo;
      
      Point start = new Point();
      start = getPoint(idF);
      Point end = new Point();
      end = getPoint(idT);
      if(start != null && end!= null)
      {
        g.drawLine(start.x, start.y, end.x, end.y);
      }
      else
      {
        pathList.remove(i);
      }
    }
    
    for(int i=0; i<pathList.size();i++)
    {
      
      g.setColor(Color.BLUE);
      g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      Path temp = (Path)pathList.get(i);
      
      int idF = pathList.get(i).idFrom;
      int idT = pathList.get(i).idTo;
      
      
      Point start = new Point();
      start=getPoint(idF);
      Point end = new Point();
      end=getPoint(idT);
      if(start!= null && end!= null)
      {
        g.drawLine(start.x, start.y, end.x, end.y);
        
      }
      else
      {
        pathList.remove(i);
      }
    }
  }
  
  public void drawLine()
  {
    // Draw the line
    Path path = null;
    
    int  idFrom=getCurrentId(_lineStart);
    int  idTo=getCurrentId(_lineEnd);
    
    double x1=_lineStart.getX();
    double x2 = _lineEnd.getX();
    
    double y1=_lineStart.getY();
    double y2 = _lineEnd.getY();
    
    double distance = Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
    
    
    if(idFrom != idTo)
      path = new Path("undirected", idFrom, idTo,distance);
    
    if(path != null)
    {
      for(int i = 0; i < pathList.size(); i++)
      {
        if((path.idFrom == pathList.get(i).idTo && path.idTo == pathList.get(i).idFrom)||(path.idFrom == pathList.get(i).idFrom && path.idTo == pathList.get(i).idTo))
        {
          pathList.remove(i);
        }
      }
      this.changeOccured = true;
      pathList.add(path);
    }
  }
  
  public void drawLineVisual(Graphics2D g)
  {
    g.setColor(Color.GREEN);
    g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    if(_lineStart!= null && _lineEnd!= null)
    {
      g.drawLine(_lineStart.x, _lineStart.y, _lineEnd.x, _lineEnd.y);   
    }  
  }
  
  public void deleteLine()
  {
    int  idFrom=getCurrentId(_lineStart);
    int  idTo=getCurrentId(_lineEnd);
    
    for(int i=0;i<pathList.size();i++)
    {
      if(pathList.get(i).idFrom==idFrom && pathList.get(i).idTo==idTo || pathList.get(i).idFrom==idTo && pathList.get(i).idTo==idFrom)
      {
        pathList.remove(i);
        this.changeOccured = true;
      }
    }
  }

  
  public void mousePressed(Point p) 
  {
    // Mark the beginning of the line
    _lineEnd = null;
    _lineStart = _loc.points.get(_loc.atPos);
  }

  public void mouseDragged(Point p) 
  {
    // Mark the end of the line  
    if(MapEditor.modeType == 3)
    {
      _lineEnd = p;
      changeNotify(); // redraw the map
    }
  }
  
  public void mouseReleased(Point p) 
  {
    releaseOnLoc=true;
    
    _lineEnd = _loc.points.get(_loc.atPos);
    
    if(MapEditor.modeType==3)
    {
      drawLine();
    }
    else if(MapEditor.modeType==4)
    {
      deleteLine();
    }
    changeNotify();
  }
  
  public void mouseClicked(Point p) 
  {
    _loc = new Location(p);
    if(!(_loc.x==0 && _loc.y==0))
    {
      ++MapEditor.maxSize;
       locationList.add(_loc);
       this.changeOccured = true;
    }    
    changeNotify(); // redraw the map
  }
  
  public void locDeleted()
  {
    int id;
    id=locationList.get(_loc.atPos).id;
    locationList.remove(_loc.atPos);
    this.changeOccured = true;
    
    for(int i = 0; i < pathList.size(); i++)
    {
      if(id == pathList.get(i).idFrom)
      {
        pathList.remove(i);
      }
    }
    
    for(int i = 0; i < pathList.size(); i++)
    {
      if(id == pathList.get(i).idTo)
      {
        pathList.remove(i);
      }
    }
    changeNotify();
  }

  
  public void ref()
  {
    locationList.clear();
    _loc.points.clear();
    pathList.clear();
    MapEditor.maxSize = 0;
    if(spanningPathList != null)
      spanningPathList.clear();
    if(shortestPathList!=null)
      shortestPathList.clear();
    this.changeOccured = false;
    changeNotify();
  }
  
  public void curLoc()
  {
    changeNotify();
  }
  
  public Location locProperties()
  {
    this.changeOccured = true;
    return (Location)locationList.get(_loc.atPos);
  }
  
  public Point getPoint(int id)
  {
    int x,y;
    Point p = new Point(0,0);
    for(int i=0;i<locationList.size();i++)
    {
      if(locationList.get(i).id==id)
      {
        x =locationList.get(i).x;
        y =locationList.get(i).y;
        p.setLocation(x,y);
        return p;
      }
    }
    return null;
  }
  
  public int getCurrentId(Point p)
  {
    _loc= new Location();
    _loc.isThisYou(p);
    int id=locationList.get(_loc.atPos).id;
    return id;
  }
  
  public int getWidth() { return _image.getWidth(null); }
  public int getHeight() { return _image.getHeight(null); }

  public void addChangeListener(ChangeListener listener) {
    _listener = listener;
  }
}
