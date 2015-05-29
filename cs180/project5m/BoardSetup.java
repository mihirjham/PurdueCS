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
 * @date 03/01/2012
 *
 */

import java.util.Random;

class BoardSetup
{
  static Random random = new Random(); //Random class initialized
  
  public static void main(String[] args)
  {
    Board[][] board; //Array of Board objects
    board = initializeBoard();
    
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
 * initializeBoard()
 * Initializes the array of Board objects with the debris, player and robots being placed in random locations
 *
 * @param none
 * 
 * @return Board[][] an array of board objects with initialized positions
 */
  
  public static Board[][] initializeBoard()
  {
    Board[][] bd = new Board[10][10]; //10x10 array of Board objects
    int row, column; //variables to store grid locations
    
    //Loop to initialize 10 debris items at random locations
    for(int i=0; i<10; i++)
    {
      do      //The do while loop checks whether or not the random location is already occupied by another object
      {
        row = random.nextInt(10);
        column = random.nextInt(10);
        if(bd[row][column] == null)
        {
          bd[row][column] = new Board('*', row, column);
        }
      }while(bd[row][column] == null);
    }
    
    do   //The do while loop checks whether or not the random location is already occupied by another object
    {
      row = random.nextInt(10);
      column = random.nextInt(10);
      if(bd[row][column] == null)
      {
        bd[row][column] = new Board('@', row, column);
      }
    }while(bd[row][column] == null);
    
    do  //The do while loop checks whether or not the random location is already occupied by another object
    { 
      row = random.nextInt(10);
      column = random.nextInt(10);
      if(bd[row][column] == null)
      {
        bd[row][column] = new Board('+', row, column);
      }
    }while(bd[row][column] == null);
    
    return bd;
  } 
}

      
