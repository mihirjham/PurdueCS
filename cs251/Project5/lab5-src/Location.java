import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.Color;
import java.awt.BorderLayout;
//
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;

class Location extends JPanel
{
  private String name;
  private static int id = 0;
  private int x,y;
  
  public Location(String name, int id, int x, int y)
  {
    this.name = name;
    this.id++;
    this.x = x;
    this.y = y;
  }
  
  public Location(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setId()
  {
    this.id++;
  }
  
  public void setCoordinates(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public int getXCoordinate()
  {
    return this.x;
  }
  
  public int getYCoordinate()
  {
    return this.y;
  }
  
  public void setXCoordinate(int x)
  {
    this.x = x;
  }
  
  public void setYCoordinate(int y)
  {
    this.y = y;
  }
  
  public void paint(Graphics g)
  {
    g.fillOval(x-8,y-8,16,16);
  }  
}
  
  
  