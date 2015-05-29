
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

class MapEditor extends JFrame implements ActionListener, MouseListener
{
    // The preferred size of the demo
    private int PREFERRED_WIDTH = 680;
    private int PREFERRED_HEIGHT = 600;
    private JScrollPane scrollPane;
    private JMenuBar menuBar;
    private JMenu fileMenu, pathMenu, locationMenu;
    private JMenuItem menuItem;
    private final int INSERT_LOCATION = 1;
    private final int DELETE_LOCATION = 2;
    private final int INSERT_PATH = 3;
    private final int DELETE_PATH = 4;
    private int modeType = 0;
    
    private JFileChooser chooser;
    private File file;
    private String bitmap;
    private boolean changeOccured = false;
    
    int numLocations = 0;
    int numPaths = 0;
    int numAnonymous = 0;
    Path graph[][];
    ArrayList locationList;
    Location location;
    
    MapPanel mapPanel;
    int gX, gY;
    final int SIZE = 16;
    int clickX;
    int clickY;
    
    
    
    
    public static void main(String[] args) 
    { 
      MapEditor mapEditor = new MapEditor(); 
      mapEditor.setVisible(true);
    } 

    public MapEditor() 
    {
      setTitle("Map Editor");
      setSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
      setBackground(Color.gray);

      JPanel panel = new JPanel();
      panel.setLayout( new BorderLayout()); 
      getContentPane().add(panel);
      //Icon image = new ImageIcon("purdue-map.jpg");
      this.bitmap = "purdue-map.jpg";
      
      mapPanel = new MapPanel();
      
 
      //JLabel label = new JLabel(image);
      scrollPane = new JScrollPane(mapPanel);
      scrollPane.getViewport().add(mapPanel);
      scrollPane.addMouseListener(this);
      panel.add(scrollPane, BorderLayout.CENTER);
      String fileList[] = {"New","Open","Save","Save As","Quit"};
      String locationList[] = {"Insert Location Mode", "Delete Location Mode"};
      String pathList[] = {"Insert Path Mode", "Delete Path Mode"};
      
      panel.add(createMenuBar(createMenu("File", fileList), createMenu("Location",locationList), createMenu("Path", pathList)), BorderLayout.NORTH);
      
      this.locationList = new ArrayList();
      
    }
    
    public JMenu createMenu(String name, String list[])
    {
      if(name.equals("File"))
      {
        fileMenu = new JMenu(name);
        
        for(int i = 0; i < list.length; i++)
        {
          menuItem = new JMenuItem(list[i]);
          menuItem.addActionListener(this);
          fileMenu.add(menuItem);
        }
        
        return fileMenu;
        
      }
      else if(name.equals("Location"))
      {
        locationMenu = new JMenu(name);
        
        for(int i = 0; i < list.length; i++)
        {
          menuItem = new JMenuItem(list[i]);
          menuItem.addActionListener(this);
          locationMenu.add(menuItem);
        }
        
        return locationMenu;
      }
      else
      {
        pathMenu = new JMenu(name);
        
        for(int i = 0; i < list.length; i++)
        {
          menuItem = new JMenuItem(list[i]);
          menuItem.addActionListener(this);
          pathMenu.add(menuItem);
        }
        
        return pathMenu;
      }
    }
    
    public JMenuBar createMenuBar(JMenu fileMenu, JMenu locationMenu, JMenu pathMenu)
    {
      menuBar = new JMenuBar();
      menuBar.add(fileMenu);
      menuBar.add(locationMenu);
      menuBar.add(pathMenu);
      
      return menuBar;
    }
    
    public void actionPerformed(ActionEvent e)
    {
      String item = e.getActionCommand();
      
      if(item.equals("Quit"))
      {
        System.exit(0);
      }
      else if(item.equals("New"))
      {
        newFile();
      }
      else if(item.equals("Open"))
      {
        //openFile();
      }
      else if(item.equals("Save"))
      {
        //save();
      }
      else if(item.equals("Save As"))
      {
        //saveAs();
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
    }
    
    public void newFile()
    {
      chooser = new JFileChooser(System.getProperty("user.dir"));
      
      if(changeOccured)
      {
        int response = JOptionPane.showConfirmDialog(this, "Save the current file(overwrite existing file)?", "Save Option", 1);
        System.out.println(response);
        if(response == 0)
        {
          //save();
        }
        else if(response == 2)
        {
          return;
        }
      }
    }
    
    public void mouseClicked(MouseEvent e)
    {}
    public void mouseEntered(MouseEvent e)
    {}
    public void mouseExited(MouseEvent e)
    {}
    
    public void mousePressed(MouseEvent e)
    {
      Point p = scrollPane.getViewport().getViewPosition();
      clickX = e.getX() + p.x;
      clickY = e.getY() + p.y;
    }
    
    public void mouseReleased(MouseEvent e)
    {
      Point p = scrollPane.getViewport().getViewPosition();
      gX = e.getX() + p.x;
      gY = e.getY() + p.y;
      
      if(modeType == INSERT_LOCATION)
      {
        if(clickX == gX && clickY == gY)
        {
          boolean alreadyHave = false;
          Location loc;
          
          for(int i = 0; locationList != null && i < locationList.size() ; i++)
          {
            loc = (Location)locationList.get(i);
            
            if((gX + (SIZE) > loc.getXCoordinate() && gX-(SIZE) < loc.getXCoordinate()) && (gY+(SIZE) > loc.getYCoordinate() && gY-(SIZE) < loc.getYCoordinate()))
                    alreadyHave = true;     
          }
          
          if(alreadyHave == false)
          {
            location = new Location(gX, gY);
            locationList.add(location);
            mapPanel.setLists(locationList, graph);
            mapPanel.repaint();
          }
            
        }
      }
      //else
      //{
        //Location
    }
    
}




