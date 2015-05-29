import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;

import java.awt.image.*;

class MapPanel extends JPanel
{
  private ArrayList l;
  private int size;
  
  public MapPanel()
  {
    Icon image = new ImageIcon("purdue-map.jpg");
    JLabel label = new JLabel(image);
    
    add(label);
    size = 16;
  }
  
  public void paint(Graphics g)
  {
    super.paint(g);
    
    Location loc;
    
    for(int i = 0; l != null && i < l.size(); i++)
    {
      loc = (Location)l.get(i);
      
      if(i == l.size()-1)
      {
        g.setColor(Color.red);
      }
      else
      {
        g.setColor(Color.blue);
      }
      
      g.fillOval(loc.getXCoordinate() - (size/2) , loc.getYCoordinate() - (size/2), size, size);
    }
  }
  
  public void setLists(ArrayList loc, Path g[][])
  {
    this.l = loc;
  }
}