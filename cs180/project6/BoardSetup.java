/**
 * BoardSetup
 * This class is used to initialize the board setup according to the specified format.
 * It does this by creating a 30x45 array of Board objects, storing the each in a random
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
 * @date 04/05/2012
 *
 */

import java.util.Random;
import java.util.*;

class BoardSetup
{
  Random r = new Random(); //Random class initialized
  int playerRow, playerCol; //Variables to hold the grid coordinates of the robot and the player
  int moveCheck;
  int numRows = 30; 
  int numCols = 45;
  Board[][] board = new Board[numRows][numCols]; //10x10 Array of board objects
  int currentStatus = 0; //Current status of the game(win = 1, lose = -1)
  int level = 1;
  int numRobots = level*10;
  int numDeathRobots = 0;
  int robotBrokenCount[];
  boolean[] robotAlive = new boolean[numRobots];
  boolean[] robotBroken = new boolean[numRobots];
  int[] robotRow = new int[numRobots];
  int[] robotCol = new int[numRobots];
  int error;
  int c,c2,re;
  
   //Accessor Methods
   public Board[][] getBoard() {return this.board;}
   public int getPlayerRow(){return this.playerRow;}
   public int getPlayerCol(){return this.playerCol;}
   public int getCurrentStatus(){return this.currentStatus;}
   public int getLevel(){return this.level;}
   public int getNumRobots(){return this.numRobots;}
   public int levelUp(){this.level++; return this.level;}
   public int initializeLevel(){this.level=1;return this.level;}

  /**
   * initializeBoard()
   * Initializes the array of Board objects with the debris, player and robots being placed in random locations
   *
   * @param none
   * 
   * @return none
   */
  
  public  void initializeBoard()
  {
    int row, column; //variables to store grid locations
  
      numRobots = level*10;
      currentStatus = 0;
      numDeathRobots = 0;
      robotAlive = new boolean[numRobots]; 
      robotBroken = new boolean[numRobots];
      robotRow = new int[numRobots];
      robotCol = new int[numRobots];
    
    //loop to initialize board with empty spaces  
    for(int i=0; i<numRows; i++){
      for(int j=0; j<numCols; j++)
      {
        board[i][j] = new Board(' ', i , j);
      }}
    //Loop to initialize robots items at random locations
    for(int i=0; i<numRobots; i++)
    {
      row = r.nextInt(numRows);
      column = r.nextInt(numCols);
      if (board[row][column].getType() != '@' && board[row][column].getType() != '+' && board[row][column].getType() != 'x'){
        if (r.nextInt(100) < 15){
          board[row][column] = new Board('x', row, column);
          robotRow[i] = row;
          robotCol[i] = column; 
          robotAlive[i] = true;
          robotBroken[i] = true;
          moveCheck = r.nextInt(4)+1;
        }else{
          board[row][column] = new Board('+', row, column);
          robotRow[i] = row;
          robotCol[i] = column; 
          robotAlive[i] = true;
          robotBroken[i] = false;
        }
        
      }else{
        i--;
      }
      
      robotAlive[i]= true;
    }
    
    do   //The do while loop checks whether or not the random location is already occupied by another object
    {
      row = r.nextInt(numRows);
      column = r.nextInt(numCols);
      if(board[row][column].getType() == ' ')
      {
        board[row][column] = new Board('@', row, column);
        playerRow = row;
        playerCol = column;
      }
    }while(board[row][column].getType() != '@');

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
  
  public void updatePlayerPosition(int input)
  {
    int  newPlayerRow, newPlayerCol; //Temporary variables to hold grid coordinates and the user's input
    
    
    //Condition to check whether the player has chosen to teleport or not
    if(input !=0)
    {
      board[playerRow][playerCol]= new Board(' ',playerRow ,playerCol); //Clears the space of the original location
      newPlayerRow = playerRow  - ((input-1)/3 - 1);
      newPlayerCol = playerCol + (((input - 1)%3) - 1);
      
      //Condition to check if the players in bounds of the board
      if(newPlayerRow >(numRows-1) || newPlayerRow <0 || newPlayerCol >(numCols-1) || newPlayerCol < 0)
      { 
        newPlayerRow = playerRow;
        newPlayerCol = playerCol;
      }
      //Condition to check the current status of the game  
      if(board[newPlayerRow][newPlayerCol].getType() != ' ')
      {
        if(board[newPlayerRow][newPlayerCol].getType() == '*' || board[newPlayerRow][newPlayerCol].getType() == '+' || board[newPlayerRow][newPlayerCol].getType() == 'x')
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
      board[playerRow][playerCol] = new Board(' ', playerRow, playerCol);
      do
      {
        newPlayerRow = r.nextInt(numRows); //Random row location
        newPlayerCol = r.nextInt(numCols); //Random column location
      }while(board[newPlayerRow][newPlayerCol].getType() != ' ');
      
      board[newPlayerRow][newPlayerCol] = new Board('@', newPlayerRow, newPlayerCol);
      
      playerRow = newPlayerRow;
      playerCol = newPlayerCol;
    }
  }
  
  /**
   * updateRobotPosition()
   * According to the player's updated position, the robot grid coordinates are calculated accordingly
   * Checks if the robot has landed on debris or collide with the player or with another robot, \
   * and changes the game's status accordingly
   *
   * @param none
   * 
   * @return none
   */
  
  public void updateRobotPosition()
  {
    
    int ranMove, num, newRobotRow, newRobotCol;//Temporary variables to hold grid coordinates
    re=0; //Regular move check
    c=0; //2 robots colliding
    c2=0; //robot and debris colliding
    
    for(int j=0; j<numRows; j++){
      for(int k=0; k<numCols; k++)
      {
        board[j][k] = new Board(' ', j , k);
      }}
    
    for(int i = 0; i < numRobots + numDeathRobots ; i++){
      if(robotAlive[i]==true){
        board[robotRow[i]][robotCol[i]] = new Board(' ', robotRow[i], robotCol[i]); }//Clears the space of the original location
      else{
        board[robotRow[i]][robotCol[i]] = new Board('*', robotRow[i], robotCol[i]);
      }
    }
    
    board[playerRow][playerCol] = new Board('@', playerRow, playerCol);
    
    for(int i = 0; i < numRobots + numDeathRobots ; i++){
      
      if(robotAlive[i]==true){ //Robot can move
        if((robotBroken[i] == false) || (moveCheck>0)){ //Move regularly
          //Condition to calculate the new row value of the robot
          
          if(robotRow[i] < playerRow)  { newRobotRow = robotRow[i] + 1;}
          else if(robotRow[i] > playerRow)  { newRobotRow = robotRow[i] - 1;}
          else { newRobotRow = robotRow[i];}
          
          //Condition to calculate the new column value of the robot
          if(robotCol[i] < playerCol)  { newRobotCol = robotCol[i] + 1;}
          else if(robotCol[i] > playerCol) { newRobotCol = robotCol[i] - 1; }
          else {newRobotCol = robotCol[i];}}
        
        else { //Random Move
          ranMove = r.nextInt(9)+1;
          newRobotRow = robotRow[i]  - ((ranMove-1)/3 - 1);
          newRobotCol = robotCol[i] + (((ranMove - 1)%3) - 1);
          moveCheck = 5;
          //Condition to check if the BrokenRobots in bounds of the board
          if(newRobotRow >(numRows-1) || newRobotRow <0 || newRobotCol >(numCols-1) || newRobotCol < 0)
          { 
            newRobotRow = robotRow[i];
            newRobotCol = robotCol[i];
          }
        }
             
        if(board[newRobotRow][newRobotCol].getType() == '*')
        {  
          robotRow[i] = newRobotRow;
          robotCol[i] = newRobotCol;
          board[robotRow[i]][robotCol[i]] = new Board('*' , robotRow[i], robotCol[i]);
          robotAlive[i] = false;
          numRobots--;
          numDeathRobots++;
          c2++;
        }
        else if(board[newRobotRow][newRobotCol].getType() == '@')
        {       
          robotRow[i] = newRobotRow;
          robotCol[i] = newRobotCol;
          currentStatus = -1;
        }
        else if((board[newRobotRow][newRobotCol].getType() == '+')||(board[newRobotRow][newRobotCol].getType() == 'x'))
        {
          board[newRobotRow][newRobotCol] = new Board('*' , newRobotRow, newRobotCol);
          robotRow[i] = newRobotRow;
          robotCol[i] = newRobotCol;
          robotAlive[i] = false;
          numRobots = numRobots - 2; 
          numDeathRobots = numDeathRobots + 2;
          for (int j = 0; j < i; j++){
            if (robotRow[j] == newRobotRow && robotCol[j] == newRobotCol){
              
              robotAlive[j] = false;
            }
          }
          c++;
        }
        
        else if(board[newRobotRow][newRobotCol].getType() == ' ' && robotBroken[i] == true)
        {
          board[newRobotRow][newRobotCol] = new Board('x', newRobotRow, newRobotCol);
          robotRow[i] = newRobotRow;
          robotCol[i] = newRobotCol;
          re++;
        }
        else if(board[newRobotRow][newRobotCol].getType() == ' ' && robotBroken[i] == false)
        {
          board[newRobotRow][newRobotCol] = new Board('+', newRobotRow, newRobotCol);
          robotRow[i] = newRobotRow;
          robotCol[i] = newRobotCol;
          re++;
        }
        else{error++;}
      }
      
      else{
        newRobotRow = robotRow[i];
        newRobotCol = robotCol[i];
        board[newRobotRow][newRobotCol] = new Board('*' , newRobotRow, newRobotCol);
      }
    }
    moveCheck--;
    if (numRobots == 0)
    {currentStatus = 1;}
  }
  
}