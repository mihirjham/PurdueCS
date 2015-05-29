/* Class name: MapEditor
 * 
 * Written by Guntas Grewal and Mihir Jham
 * 
 * Runner class
 * 
 */
package mappapp;

import javax.swing.*; 
import javax.swing.event.*; 
import javax.swing.text.*; 
import javax.swing.border.*; 
import javax.swing.colorchooser.*; 
import javax.swing.filechooser.*; 
import javax.accessibility.*; 
import javax.imageio.*;

import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.beans.*; 
import java.util.*; 
import java.io.*; 
import java.applet.*; 
import java.net.*;

public class MapEditor extends JFrame implements ActionListener
{
  // The preferred size of the editor
  private final int PREFERRED_WIDTH = 680;
  private final int PREFERRED_HEIGHT = 600;

  private JScrollPane _scrollPane;
  private ZoomPane _zoomPane;
  private MapScene _map;
  private Location _loc,l;
  private Graphics dot;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem menuItem;
  private boolean locPropertyStatus = false;
  private JDialog locPropertyWindow;
  public static int maxSize=0;
  
  private JFileChooser chooser;
  private File file = null;
  private String bitmap;
  
  private JTextField nameField;
  
  private final int INSERT_LOCATION = 1;
  private final int DELETE_LOCATION = 2;
  private final int INSERT_PATH = 3;
  private final int DELETE_PATH = 4;
  private final int REFRESH = 5;
  private final int LOCATION_PROP = 6;
  private final int DIRECTIONS = 7;
  private final int MST = 8;
  public static int modeType = 0; //made static
  int origin, dest;
  double scale = 1.5;

  public static void main(String[] args) { 
    MapEditor mapEditor = new MapEditor(); 
    
    mapEditor.setVisible(true);
  } 

  public MapEditor() {
    
    setTitle("Map Editor");
    setSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    setBackground(Color.gray);

    // Close when closed. For reals.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Image image = new ImageIcon("maps/purdue-map.jpg").getImage();

    _map = new MapScene(image);
    _zoomPane = new ZoomPane(_map);
    _loc = new Location();

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(_zoomPane);
    getContentPane().add(_zoomPane.getJSlider(), BorderLayout.SOUTH);
    getContentPane().add(menuBar(), BorderLayout.NORTH);

    MouseAdapter listener = new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        Point point = _zoomPane.toViewCoordinates(e.getPoint());
        if(modeType == 3 || modeType ==4)
        {
          if(_loc.isThisYou(point))
          {
            _map.mousePressed(point);
          }
        }
      }
    };

    MouseMotionAdapter motionListener = new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        Point point = _zoomPane.toViewCoordinates(e.getPoint());
        _map.mouseDragged(point);
      }
    };
    MouseAdapter release = new MouseAdapter(){
      public void mouseReleased(MouseEvent e){
        Point point = _zoomPane.toViewCoordinates(e.getPoint());
         if(modeType == 3 || modeType ==4)
        {          
           if(_loc.isThisYou(point))
           {
             _map.mouseReleased(point);
           }
         }
      }
    };
    
    MouseAdapter click = new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        Point point = _zoomPane.toViewCoordinates(e.getPoint());
        
        if(modeType == INSERT_LOCATION)
        {
          _map.mouseClicked(point);
        }
        else if(modeType == DELETE_LOCATION)
        {
          if(_loc.isThisYou(point))
          {
            _loc.points.remove(_loc.atPos);
            _map.locDeleted();
          }
        }
        else if(modeType == LOCATION_PROP)
        {
          if(_loc.isThisYou(point))
          {
            if(locPropertyStatus)
            {  
              locPropertyWindow.dispose();
              locPropertyStatus = false;
            }
            
            Location temp = _map.locProperties();
            locPropertyWindow = locationProperties(temp.name, temp.id, temp.x, temp.y);
            _map.curLoc();
          }
        } 
        else
        {
          if(_loc.isThisYou(point))
          {
            _map.curLoc();
          }
        }

        if(e.getClickCount()==2)
        {
          if(_loc.isThisYou(point))
          {
            if(locPropertyStatus)
            {  
              locPropertyWindow.dispose();
              locPropertyStatus = false;
            }
            Location temp = _map.locProperties();
            locPropertyWindow = locationProperties(temp.name, temp.id, temp.x, temp.y);
            _map.curLoc(); 
          }
        }
      }
    };

    _zoomPane.getZoomPanel().addMouseListener(click);
    _zoomPane.getZoomPanel().addMouseListener(release);
    _zoomPane.getZoomPanel().addMouseListener(listener);
    _zoomPane.getZoomPanel().addMouseMotionListener(motionListener);
  }
   
  public JMenuBar menuBar()
  {
    menuBar = new JMenuBar();
    String menuList[] = {"File", "Path", "Location","Map Viewer"};
    for(int j = 0; j < menuList.length; j++)
    {
      menu = new JMenu(menuList[j]);
      
      if(j==0)
      {
        String file[] = {"New","Open","Save","Save As"};
        for(int i = 0; i < file.length; i++)
        {
          menuItem = new JMenuItem(file[i]);
          menuItem.addActionListener(this);
          menu.add(menuItem);
        }
          menuBar.add(menu);
      }
      if(j == 1)
      {
        String path[] = {"Insert Path Mode","Delete Path Mode"};
        for(int i = 0; i<path.length; i++)
        {
          menuItem = new JMenuItem(path[i]);
          menuItem.addActionListener(this);
          menu.add(menuItem);
        }
        menuBar.add(menu);
      }
      if(j == 2)
      {
        String location[] = {"Insert Location Mode", "Delete Location Mode", "Location Properties"};
        for(int i = 0; i<location.length; i++)
        {
          menuItem = new JMenuItem(location[i]);
          menuItem.addActionListener(this);
          menu.add(menuItem);
        }
        menuBar.add(menu);
      }
      if(j == 3)
      {
        menuItem = new JMenuItem("MST");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Directions");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuBar.add(menu);                           
      }
    }
    return menuBar;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    String item = e.getActionCommand();
    
      if(item.equals("New"))
      {
        newFile();
      }
      else if(item.equals("Open"))
      {
        openFile();
      }
      else if(item.equals("Save"))
      {
        save();
      }
      else if(item.equals("Save As"))
      {
        saveAs();
      }
      else if(item.equals("Insert Location Mode"))
      {
        modeType = INSERT_LOCATION;       
      }
      else if(item.equals("Delete Location Mode"))
      {
        modeType = DELETE_LOCATION;
      }
      else if(item.equals("Insert Path Mode"))
      {
        modeType = INSERT_PATH;        
      }
      else if(item.equals("Delete Path Mode"))
      {
        modeType = DELETE_PATH;
      }
      else if(item.equals("Location Properties"))
      {
        modeType =  LOCATION_PROP;     
      }
      else if(item.equals("MST"))
      {
        boolean connected = false;
        for(int i=0; i < _map.locationList.size(); i++)
        {
          for(int j = 0; j < _map.pathList.size(); j++)
          {
            if(_map.locationList.get(i).id == _map.pathList.get(j).idFrom || _map.locationList.get(i).id == _map.pathList.get(j).idTo)
            {
              connected = true;
              break;
            }
            else
              connected = false;
          }
        }
          
        if(connected)
        {
          _map.shortestPathList.clear();
          Prims p = new Prims(_map.locationList, _map.pathList);
          _map.spanningPathList = p.resultPath;
          
          for(int i=0; i<_map.spanningPathList.size();i++)
          {
            int idFrom = _map.spanningPathList.get(i).idFrom;
            int idTo = _map.spanningPathList.get(i).idTo;
            
            Point p1 = _map.getPoint(idFrom);
            Point p2 = _map.getPoint(idTo);
            
            Double distance = 0.0;
            
            if(p1!=null && p2!=null)
            {
              double x1 = p1.getX();
              double x2 = p2.getX();
              
              double y1 = p1.getY();
              double y2 = p2.getY();
              
              distance = Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
            }    
            _map.spanningPathList.get(i).distance=distance;
          }
          _map.changeNotify();
        }
        else
          JOptionPane.showMessageDialog(null, "This graph is disconnected. Please connected all nodes.");
      }
      else if(item.equals("Directions"))
      {
        modeType=DIRECTIONS;
        _map.spanningPathList.clear();
        directionOptions();  
        
        Dijkstra dij = new Dijkstra(_map.locationList, _map.pathList, origin, dest);
        
        _map.shortestPathList = dij.result;
        double minDistance = dij.minDistance;
        
        for(int i=0; i<_map.shortestPathList.size();i++)
        {
          int idFrom = _map.shortestPathList.get(i).idFrom;
          int idTo = _map.shortestPathList.get(i).idTo;
          
          Point p1 = _map.getPoint(idFrom);
          Point p2 = _map.getPoint(idTo);
          
          Double distance = 0.0;
          
          if(p1!=null && p2!=null)
          {
            double x1 = p1.getX();
            double x2 = p2.getX();
            
            double y1 = p1.getY();
            double y2 = p2.getY();
            
            distance = Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
          }    
          _map.shortestPathList.get(i).distance=distance;
        }
        if(Double.toString(minDistance*this.scale).equals("Infinity"))
          JOptionPane.showMessageDialog(null, "There is no path between the two points selected.");
        else
          JOptionPane.showMessageDialog(null, "The distance is "+Double.toString(minDistance*this.scale));
        _map.changeNotify();
      }
      else 
      {
        Location temp = _map.locationList.get(_loc.atPos);
        temp.name = item;
      }
  }
  
  public JDialog directionOptions(ArrayList<Path> pathList)
  {
    JDialog prop = new JDialog();
    return prop;
  }
  
  
  public JDialog locationProperties(String name, int id, int x, int y)
  {
    JDialog prop = new JDialog();
    JLabel lname = new JLabel("Location Name:");
    JLabel xLabel = new JLabel("The X Co-ordinate:");
    JLabel yLabel = new JLabel("The Y Co-ordinate:");    
    JLabel xVal = new JLabel(Integer.toString(x));
    JLabel yVal = new JLabel(Integer.toString(y));
    JLabel idLabel = new JLabel("Location ID:");
    JLabel idVal = new JLabel(Integer.toString(id));
    JLabel msg = new JLabel("Hit Enter to Save");
    
    
    nameField = new JTextField();
    
    if(name == null)
    {
      nameField.setText("");
    }
    else
    {
      nameField.setText(name);
    }
    
    nameField.addActionListener(this);
 
          
    
    prop.setLayout(new GridLayout(5,4));
    prop.add(lname);
    prop.add(nameField);
    prop.add(idLabel);
    prop.add(idVal);
    prop.add(xLabel);
    prop.add(xVal);
    prop.add(yLabel);
    prop.add(yVal);
    prop.add(msg);
    prop.setSize(300,100);
    prop.setTitle("Location Properties");
    prop.setVisible(true);
    this.locPropertyStatus = true;

    return prop;
  }
  
  public void newFile()
  {
    chooser = new JFileChooser(System.getProperty("user.dir"));
    if(_map.changeOccured())
    {
      int response = JOptionPane.showConfirmDialog(this, "Save the current file(overwrite existing file)?", "Save Option", 1);
      if(response == 0)
      {
        saveAs();
      }
      else if(response == 2)
      {
        return;
      }
    }
    _map.ref();
  }
  
  public void saveAs()
  {
    chooser = new JFileChooser(System.getProperty("user.dir"));
    int status = chooser.showSaveDialog(null);
    
    if(status == JFileChooser.APPROVE_OPTION)
    {
      file = chooser.getSelectedFile();
      String fileName = file.getName();
      FileOutputStream outStream = null;
      PrintWriter pw = null;
      
      try
      {
        outStream = new FileOutputStream(file);
      }
      catch(FileNotFoundException evt)
      {
        JOptionPane.showMessageDialog(null, "File not found!", "error", JOptionPane.ERROR_MESSAGE);
      }
      
      try
      {
        pw = new PrintWriter(outStream);
        
        pw.print("<mapfile bitmap=\"purdue-map.jpg\" scale-feet-per-pixel=\""+Double.toString(this.scale)+"\">\n");
        for(int i = 0; i < _map.locationList.size(); i++)
        {
          Location temp = (Location)_map.locationList.get(i);
          pw.print("<location id=\""+temp.id+"\" name=\"");
          if(temp.name == null)
          {
            pw.print("\" x=\""+temp.x+"\" y=\""+temp.y+"\"/>\n");
          }
          else
          {
            pw.print(temp.name+"\" x=\""+temp.x+"\" y=\""+temp.y+"\"/>\n");
          }
        }
        
        for(int i = 0; i < _map.pathList.size(); i++)
        {
          Path temp = (Path)_map.pathList.get(i);
          pw.print("<path idfrom=\""+temp.idFrom+"\" idto=\""+temp.idTo+"\" type=\"");
          if(temp.type == null)
          {
            pw.print("\"/>\n");
          }
          else
          {
            pw.print(temp.type+"\"/>\n");
          }
        }
        pw.println("</mapfile>");
        
        pw.close();
        outStream.close();
      }
      catch(IOException io)
      {
        JOptionPane.showMessageDialog(null, "Writing error!", "error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void save()
  {
    if(file == null)
    {
      saveAs();
    }
    else
    {
      file = chooser.getSelectedFile();
      String fileName = file.getName();
      FileOutputStream outStream = null;
      PrintWriter pw = null;
      
      try
      {
        outStream = new FileOutputStream(file);
      }
      catch(FileNotFoundException evt)
      {
        JOptionPane.showMessageDialog(null, "File not found!", "error", JOptionPane.ERROR_MESSAGE);
      }
      
      try
      {
        pw = new PrintWriter(outStream);
        
        pw.print("<mapfile bitmap=\"purdue-map.jpg\" scale-feet-per-pixel=\""+Double.toString(this.scale)+"\">\n");
        for(int i = 0; i < _map.locationList.size(); i++)
        {
          Location temp = (Location)_map.locationList.get(i);
          pw.print("<location id=\""+temp.id+"\" name=\"");
          if(temp.name == null)
          {
            pw.print("\" x=\""+temp.x+"\" y=\""+temp.y+"\"/>\n");
          }
          else
          {
            pw.print(temp.name+"\" x=\""+temp.x+"\" y=\""+temp.y+"\"/>\n");
          }
        }
        
        for(int i = 0; i < _map.pathList.size(); i++)
        {
          Path temp = (Path)_map.pathList.get(i);
          pw.print("<path idfrom=\""+temp.idFrom+"\" idto=\""+temp.idTo+"\" type=\"");
          if(temp.type == null)
          {
            pw.print("\"/>\n");
          }
          else
          {
            pw.print(temp.type+"\"/>\n");
          }
        }
        
        pw.println("</mapfile>");
        
        pw.close();
        outStream.close();
      }
      catch(IOException io)
      {
        JOptionPane.showMessageDialog(null, "Writing error!", "error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  public void openFile()
  {
    chooser = new JFileChooser(System.getProperty("user.dir"));
    if(_map.changeOccured())
    {
      int response = JOptionPane.showConfirmDialog(this, "Save the current file(overwrite existing file)?", "Save Option", 1);
      if(response == 0)
      {
        saveAs();
      }
      else if(response == 2)
      {
        return;
      }
    }
    
    chooser = new JFileChooser(System.getProperty("user.dir"));
    int status = chooser.showOpenDialog(null);
    ArrayList<Location> locationList = new ArrayList();
    ArrayList<Point> points = new ArrayList();
    ArrayList<Path> pathList = new ArrayList();
    
    if(status == JFileChooser.APPROVE_OPTION)
    {
      FileInputStream in = null;
      Scanner scanner;
      
      try
      {
        file = chooser.getSelectedFile();
        in = new FileInputStream(file);
      }
      catch(FileNotFoundException e)
      {
        JOptionPane.showMessageDialog(null, "File not found!", "error", JOptionPane.ERROR_MESSAGE);
      }
      
      try
      {
        scanner = new Scanner(in);
        String str = scanner.nextLine();
        str = str.substring(str.indexOf('"')+1, str.length());
        this.bitmap = str.substring(0, str.indexOf('"'));
        str = str.substring(str.indexOf('"')+1, str.length());
        str = str.substring(str.indexOf('"')+1, str.length());
        this.scale = Double.parseDouble(str.substring(0, str.indexOf('"')));  
        
        str = scanner.nextLine();
        
        while(!str.equals("</mapfile>"))
        {
          String temp = str.substring(1, str.indexOf(' '));
          
          if(temp.equals("location"))
          {
            str = str.substring(str.indexOf('"')+1, str.length());
            temp = str.substring(0, str.indexOf('"'));
            int id, x, y;
            
            id = Integer.parseInt(temp);
            
            str = str.substring(str.indexOf('"')+1, str.length());
            str = str.substring(str.indexOf('"')+1, str.length());
            String name = str.substring(0, str.indexOf('"'));
            str = str.substring(str.indexOf('"')+1, str.length());
            str = str.substring(str.indexOf('"')+1, str.length());
            temp = str.substring(0, str.indexOf('"'));
            x = Integer.parseInt(temp);
            str = str.substring(str.indexOf('"')+1, str.length());
            str = str.substring(str.indexOf('"')+1, str.length());
            temp = str.substring(0, str.indexOf('"'));
            y = Integer.parseInt(temp);
            
            if(name.equals(""))
            {
              name = null;
            }
            
            locationList.add(new Location(id, name, x, y));
            points.add(new Point(x,y));
            
            str = scanner.nextLine();
          }
          
          else if(temp.equals("path"))
          {
            int idFrom, idTo;
            str = str.substring(str.indexOf('"')+1, str.length());
            temp = str.substring(0, str.indexOf('"'));
            idFrom = Integer.parseInt(temp);
            str = str.substring(str.indexOf('"')+1, str.length());
            str = str.substring(str.indexOf('"')+1, str.length());
            temp = str.substring(0, str.indexOf('"'));
            idTo = Integer.parseInt(temp);
            str = str.substring(str.indexOf('"')+1, str.length());
            str = str.substring(str.indexOf('"')+1, str.length());
            temp = str.substring(0, str.indexOf('"'));

            pathList.add(new Path(temp, idFrom, idTo));
            
            str = scanner.nextLine();
          }
        }
        _map.ref();
        _map.locationList = locationList;
        
        _map._loc.points = points;
        
        maxSize = _map.locationList.get(_map.locationList.size()-1).id+1;
        _map.pathList = pathList;

        for(int i=0; i<_map.pathList.size();i++)
        {
          int idFrom = _map.pathList.get(i).idFrom;
          int idTo = _map.pathList.get(i).idTo;

          Point p1=_map.getPoint(idFrom);
          Point p2=_map.getPoint(idTo);
          
          Double distance = 0.0;
          
          if(p1 != null && p2 != null)
          {
            double x1 =  p1.getX();
            double x2 = p2.getX();
            
            double y1 = p1.getY();
            double y2 = p2.getY();
            
            distance = Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
          }    
          _map.pathList.get(i).distance=distance;
        }
        
        _map.changeNotify();
        _map.changeNotify();
        in.close();
      }
      catch(IOException evt)
      {
        JOptionPane.showMessageDialog(null, "File reading error", "error", JOptionPane.ERROR_MESSAGE);
      }
    }
    else
    {
      _map.changeNotify();
      return;
    }
  }

  public void directionOptions()
  {
    JLabel toList = new JLabel("To List");
    JLabel fromList = new JLabel("From List");
    
    JPanel panel = new JPanel(new BorderLayout());
    
    String to[] = new String[_map.locationList.size()];
    String from[]= new String[_map.locationList.size()];
    
    for(int i = 0;i<_map.locationList.size();i++)
    {
      String temp = Integer.toString(_map.locationList.get(i).id);
      
      to[i]=temp;
      from[i]=temp;
    }

    JList toL = new JList(to);
    JList fromL = new JList(from);
    
    JScrollPane scrollTo = new JScrollPane();
    JScrollPane scrollFrom = new JScrollPane();
    
    scrollTo.getViewport().add(toL);
    scrollFrom.getViewport().add(fromL);
    
    panel.add(scrollTo, BorderLayout.EAST);
    panel.add(scrollFrom,BorderLayout.WEST);
    
    int reply = JOptionPane.showConfirmDialog(null, panel, "Location Options", JOptionPane.OK_CANCEL_OPTION);
    
    if(reply == JOptionPane.OK_OPTION)
    {
      dest=Integer.parseInt(to[toL.getSelectedIndex()]);
      origin=Integer.parseInt(from[fromL.getSelectedIndex()]);
    }
  }
}