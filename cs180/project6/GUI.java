/**
 * GUI
 * This class creates the GUI for the Robots game and handles all the GUI related activity
 * 
 * @author Enchong Wang 
 * @cs login Wang977
 * @recitation REC4 recitation instructor's name Sriram, Dinesh
 * 
 * @author Haoran Lin
 * @cs login lin251
 * @* @recitation REC1 recitation instructor's name Ganasekaran,Bala
 * 
 * @author Mihir Jham
 * @cs login mjham
 * @recitation REC4 recitation instructor's name Sriram, Dinesh
 * 
 * @date 04/05/2012
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{
  
  String[] direction={"upLeft","up","upRight","left","wait","right","downLeft","down","downRight","teleport"}; //Button names
  JButton[] button=new JButton[10];
  JLabel levelText,robotRemaining;
  JMenuItem menuItem;
  JMenuBar menuBar;
  JMenu menu;
  JPanel gameBoard=new JPanel(new GridLayout(30,45) );
  static JLabel[][] label=new JLabel[30][45];
  JPanel buttonPanel,statusBar;
  ImageIcon    robot=new ImageIcon("robot.png");    
  ImageIcon  robot2=new ImageIcon("robot2.png");
  ImageIcon  player=new ImageIcon("player.png");
  ImageIcon  dead=new ImageIcon("dead.png");
  ImageIcon  debris=new ImageIcon("debris.png");
  BoardSetup boardSetup=new BoardSetup();
  
  /**
  * Constructor
  * Initializes the GUI of the game
  *
  * @param none
  * 
  * @return none
  */
  public GUI()
  {
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setSize(400,400);
    this.setTitle("Robots");
    Container contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(menuBar(),BorderLayout.NORTH);
    contentPane.add(gameBoard(),BorderLayout.CENTER);
    contentPane.add(button(),BorderLayout.EAST);
    contentPane.add(statusBar(),BorderLayout.SOUTH);    
    this.pack();
    this.setVisible(true);
  }
  
  /**
  * gameBoard()
  * Initializes the board of the game and the positions
  *
  * @param none
  * 
  * @return the initialized board
  */
  public JPanel gameBoard ()
  { 
    //Initializes the grids of the game
    for(int i=0;i<30;i++)
    {
      for(int j=0;j<45;j++)
      { 
        label[i][j]=new JLabel();
        label[i][j].setPreferredSize(new Dimension(24,24));
        label[i][j].setOpaque(true);
        gameBoard.add(label[i][j]);
        if((i+j)%2==0)   
        {
          label[i][j].setBackground(Color.lightGray);
        }
        else{
          label[i][j].setBackground(Color.GRAY);
        }
      }
    }
    
    initialized();
    setLabel();    
    
    return gameBoard;
  }
  
  /**
  * initialized()
  * Initializes the object's initial positions
  *
  * @param none
  * 
  * @return the initialized board
  */
  public void initialized()
  {
       boardSetup.initializeBoard();  
  }
  
  /**
  * setLabel()
  * Sets the players icons with the images and other lables
  *
  * @param none
  * 
  * @return none
  */
  public void setLabel()
  {
    for(int i=0;i<30;i++)
    {
      for(int j=0;j<45;j++)
      {  
        if(boardSetup.getBoard()[i][j].getType() == ' ')
          label[i][j].setIcon(null);
        if(boardSetup.getBoard()[i][j].getType() == '@')        
          label[i][j].setIcon(player);
        if(boardSetup.getBoard()[i][j].getType()=='+')
          label[i][j].setIcon(robot);
        if(boardSetup.getBoard()[i][j].getType()=='x')
          label[i][j].setIcon(robot2);
        if(boardSetup.getBoard()[i][j].getType()=='*')
          label[i][j].setIcon(debris);        
      }
    }
  }
  
  /**
  * button()
  * Creates a new buttonPanel and assigns ActionListeners
  *
  * @param none
  * 
  * @return the Button Panel
  */
  public JPanel button()
  {
    buttonPanel=new JPanel(new GridLayout(4,3));    
    
    for(int i=0;i<button.length;i++)
    { 
      button[i]=new JButton(direction[i]);
      buttonPanel.add(button[i]);
      button[i].addActionListener(this);
    }    
    
    return buttonPanel;
  }
  
  /**
  * actionPerformed()
  * Handles all the events of the game
  *
  * @param none
  * 
  * @return none
  */
  public  void actionPerformed(ActionEvent e) 
  {
    //Handles the each of the directions according to the buttons
    if(e.getSource().equals(button[0])){boardSetup.updatePlayerPosition(7);}    
    if(e.getSource().equals(button[1])){boardSetup.updatePlayerPosition(8);}    
    if(e.getSource().equals(button[2])){boardSetup.updatePlayerPosition(9);}    
    if(e.getSource().equals(button[3])){boardSetup.updatePlayerPosition(4);}    
    if(e.getSource().equals(button[4])){boardSetup.updatePlayerPosition(5);}    
    if(e.getSource().equals(button[5])){boardSetup.updatePlayerPosition(6);}    
    if(e.getSource().equals(button[6])){boardSetup.updatePlayerPosition(1);}    
    if(e.getSource().equals(button[7])){boardSetup.updatePlayerPosition(2);}    
    if(e.getSource().equals(button[8])){boardSetup.updatePlayerPosition(3);}    
    if(e.getSource().equals(button[9])){boardSetup.updatePlayerPosition(0);}
    
    //Updates the robots' positions accordingly
    boardSetup.updateRobotPosition();
    setLabel();
    updateStatusBar(); 
    
    if(boardSetup.getCurrentStatus() == 1)
      win();       
    if(boardSetup.getCurrentStatus() ==-1)
      lose();

      
  //Handles the event of the menu item  
    if(e.getSource().equals(menuItem))
    {
      boardSetup.initializeLevel();
      boardSetup.initializeBoard();
      updateStatusBar();
      setLabel();
    }
  }

  /**
  * menuBar()
  * Creates a menu bar.
  *
  * @param none
  * 
  * @return Menu bar that has a menu item, Game
  */
  public JMenuBar menuBar()
  {
    menuBar=new JMenuBar();
    menu=new JMenu("Game");
    menuItem=new JMenuItem("New Game");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    menuBar.add(menu);    
    
    return menuBar;
  }
  
  /**
  * statusBar()
  * Creates the status bar.
  *
  * @param none
  * 
  * @return The status bar, with the number of robots, and the level
  */
  public JPanel statusBar()
  {
    statusBar=new JPanel(new FlowLayout());
    levelText = new JLabel("Level:"+boardSetup.getLevel());
    robotRemaining=new JLabel("robot remaining:"+boardSetup.getNumRobots());
    statusBar.add(levelText);
    statusBar.add(robotRemaining);
    
    return statusBar;
  }
  
  /**
  * lose()
  * Handles the event of losing the game, shows appropriate messages.
  *
  * @param none
  * 
  * @return none
  */
  public void lose()
  {
    int a = JOptionPane.showConfirmDialog( null,
                                          "Game over.\nWould you like to play again?",
                                          "Game",
                                          JOptionPane.YES_NO_OPTION);
   
    if(a==JOptionPane.YES_OPTION)
    {
      boardSetup.initializeLevel();
      boardSetup.initializeBoard();
      updateStatusBar();
      setLabel();
    }
    
    if(a==JOptionPane.NO_OPTION)
    {
     System.exit(0);
    }
  }
  /**
  * win()
  * Handles the event of winning the game, shows appropriate messages.
  *
  * @param none
  * 
  * @return none
  */
  public void win()
  {
    JOptionPane.showMessageDialog(null,"You win!");
    boardSetup.levelUp();
    boardSetup.initializeBoard();
    updateStatusBar();
    setLabel();   
  }
  
  /**
  * main()
  * Main method, creates the GUI object
  *
  * @param none
  * 
  * @return none
  */
  public static void main(String[] args)
  {
    GUI myWindow=new GUI(); 
  }
  
  /**
  * updateStatusBar()
  * Updates the status bar, the level and the number of robots remaining
  *
  * @param none
  * 
  * @return none
  */
  public void updateStatusBar()
  {
    levelText.setText("Level:" +boardSetup.getLevel());
    robotRemaining.setText("Robot remaining:" + boardSetup.getNumRobots());
  }
}