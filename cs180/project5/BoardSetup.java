/**
 * BoardSetup
 * This class is used to initialize the board setup according to the specified format.
 * It does this by creating a 10x10 array of Board objects, storing the each in a random
 * grid.
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
 * @date 03/08/2012
 *
 */

import java.util.Random;
import java.util.*;

class BoardSetup
{
  static Random random = new Random(); //Random class initialized
  static int playerRow, playerCol, robotRow, robotCol; //Variables to hold the grid coordinates of the robot and the player
  static Board[][] board = new Board[10][10]; //10x10 Array of board objects
  static int currentStatus = 0; //Current status of the game(win = 1, lose = -1)

  
  public static void main(String[] args)
  {
     initializeBoard(); //Initializes the board with 10 debris items
 
     do
     {
      updatePlayerPosition(); //Updates the player's position and check's the game's status
      if(currentStatus == 0)
      {
        updateRobotPosition(); //Updates the player's position and check's the game's status
      }
      printBoard(); //Prints the board according to the specified format
     }while(currentStatus == 0);
     
     //If else to see the game's current status and print the appropriate method
     if(currentStatus == 1)
     {
       System.out.println("You win!");
     }
     else if(currentStatus == -1)
     {
       System.out.println("You lose!");
     }
  }
  
 /**
 * initializeBoard()
 * Initializes the array of Board objects with the debris, player and robots being placed in random locations
 *
 * @param none
 * 
 * @return none
 */
  
  public static void initializeBoard()
  {
   int row, column; //variables to store grid locations

    //Loop to initialize 10 debris items at random locations
    for(int i=0; i<10; i++)
    {
      do      //The do while loop checks whether or not the random location is already occupied by another object
      {
        row = random.nextInt(10);
        column = random.nextInt(10);
        if(board[row][column] == null)
        {
          board[row][column] = new Board('*', row, column);
        }
      }while(board[row][column] == null);
    }
    
    do   //The do while loop checks whether or not the random location is already occupied by another object
    {
      row = random.nextInt(10);
      column = random.nextInt(10);
      if(board[row][column] == null)
      {
        board[row][column] = new Board('@', row, column);
        playerRow = row;
        playerCol = column;
      }
    }while(board[row][column].getType() != '@');
    
    do  //The do while loop checks whether or not the random location is already occupied by another object
    { 
      row = random.nextInt(10);
      column = random.nextInt(10);
      if(board[row][column] == null)
      {
        board[row][column] = new Board('+', row, column);
        robotRow = row;
        robotCol = column;
      }
    }while(board[row][column].getType() != '+');
    
    printBoard();
  }
  
 /**
 * printBoard()
 * Prints the board in the specified way
 *
 * @param none
 * 
 * @return none
 */
  
  public static void printBoard()
  {
    System.out.println("+----------+");
    
    //The for loop prints the board according to the specified format
    for(int i=0; i<10; i++)
    {
      System.out.print("|");
      for(int j=0; j<10; j++)
      {
        if(board[i][j] != null)
          System.out.print(board[i][j].getType());
        else
          System.out.print(" ");
      }
      System.out.println("|");
    }
    System.out.println("+----------+");
  }
  
 /**
 * getInput()
 * Asks the user for the numerical direction
 *
 * @param none
 * 
 * @return input Returns the given input of the user
 */
  
  public static int getInput()
  {
    int input;
    Scanner scanner = new Scanner(System.in);
 
    System.out.println("Please input a direction on the numeric keypad (0 for teleport): ");
    input = Integer.parseInt(scanner.next());
  
    return input;
  }
  
 /**
 * updatePlayerPosition()
 * According to the user's input, this method calculates the new grid coordinates
 * Checks to see if the player is in bounds, if not, it remains in the same location
 * Checks to see if the player moves either on the robot or the debris, and changes the current status of the game
 * If user selects to teleport, makes sure it doesn't place the player on debris or robot positions
 * 
 *
 * @param none
 * 
 * @return none
 */
  
  public static void updatePlayerPosition()
  {
    int input, newPlayerRow, newPlayerCol; //Temporary variables to hold grid coordinates and the user's input
    
    input = getInput();
    
    //Condition to check whether the player has chosen to teleport or not
    if(input !=0)
    {
      board[playerRow][playerCol] = null; //Clears the space of the original location
      newPlayerRow = playerRow  - ((input-1)/3 - 1);
      newPlayerCol = playerCol + (((input - 1)%3) - 1);
      
      //Condition to check if the players in bounds of the board
      if(newPlayerRow >9 || newPlayerRow <0 || newPlayerCol >9 || newPlayerCol < 0)
      { 
        newPlayerRow = playerRow;
        newPlayerCol = playerCol;
      }
      //Condition to check the current status of the game  
      if(board[newPlayerRow][newPlayerCol] != null)
      {
        if(board[newPlayerRow][newPlayerCol].getType() == '*' || board[newPlayerRow][newPlayerCol].getType() == '+')
        {
          currentStatus = -1;
        }
      }
        board[newPlayerRow][newPlayerCol] = new Board('@', newPlayerRow, newPlayerCol);
        playerRow = newPlayerRow;
        playerCol = newPlayerCol;
      }
    else if(input == 0)
    {
      board[playerRow][playerCol] = null;
      do
      {
        newPlayerRow = random.nextInt(10); //Random row location
        newPlayerCol = random.nextInt(10); //Random column location
      }while(board[newPlayerRow][newPlayerCol] != null);

      board[newPlayerRow][newPlayerCol] = new Board('@', newPlayerRow, newPlayerCol);
      
      playerRow = newPlayerRow;
      playerCol = newPlayerCol;
    }
  }
  
 /**
 * updateRobotPosition()
 * According to the player's updated position, the robot grid coordinates are calculated accordingly
 * Checks if the robot has landed on debris or on the player, and changes the game's status accordingly
 * 
 *
 * @param none
 * 
 * @return none
 */
  
  public static void updateRobotPosition()
  {
    int newRobotRow, newRobotCol; //Temporary variables to hold grid coordinates
    
    board[robotRow][robotCol] = null; //Clears the space of the original location
    
    //Condition to calculate the new row value of the robot
    if(robotRow < playerRow)
    {
      newRobotRow = robotRow + 1;
    }
    else if(robotRow > playerRow)
    {
      newRobotRow = robotRow - 1;
    }
    else
      newRobotRow = robotRow;
    
    //Condition to calculate the new column value of the robot
    if(robotCol < playerCol)
    {
      newRobotCol = robotCol + 1;
    }
    else if(robotCol > playerCol)
    {
      newRobotCol = robotCol - 1;
    }
    else
      newRobotCol = robotCol;
        
   //Condition to check if the robot has landed on debris or the player, and changes the game's status accordingly  
   if(board[newRobotRow][newRobotCol] != null)
   {
     if(board[newRobotRow][newRobotCol].getType() == '*')
     {  
          robotRow = newRobotRow;
          robotCol = newRobotCol;
          currentStatus = 1;
        }
        else if(board[newRobotRow][newRobotCol].getType() == '@')
        {       
          robotRow = newRobotRow;
          robotCol = newRobotCol;
          currentStatus = -1;
        }
      }
      else if(board[newRobotRow][newRobotCol] == null)
      {
        board[newRobotRow][newRobotCol] = new Board('+', newRobotRow, newRobotCol);
        robotRow = newRobotRow;
        robotCol = newRobotCol;
        currentStatus = 0;
      }
  }
}


    
    
    
   
  
    
    
  
  
  

      
