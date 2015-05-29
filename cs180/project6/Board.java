/**
 * Board
 * This class is built to store data of each grid co-ordinate
 * The class stores the grid's type(debris, player or robot), and
 * it stores the location in terms of rows and columns.
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
class Board
{
  private char type; //Player, Debris or Robot (@, * or +)
  private int row, column; //Grid coordinates
  /**
 * Constructor
 * Initializes the data members of the instance
 *
 * @param componentType Player, Debris or Robot
 * @param r Row coordinate
 * @param c Column coordinate
 * 
 * @return Board object with initialized values
 */

  public Board(char componentType, int r, int c)
  {
    this.type = componentType;
    this.row = row;
    this.column = column;
  }
  
 /**
 * getType()
 * Accessor method to get the type of player occupuying the grid
 *
 * @param none
 * 
 * @return char the current value of the type of player
 */
  
  public char getType()
  {
    return this.type;
  }
  
 /**
 * getRow()
 * Accessor method to get the row coordinate
 *
 * @param none
 * 
 * @return int the current value of the row coordinate
 */
  
  public int getRow()
  {
    return this.row;
  }
  
 /**
 * getColumn()
 * Accessor method to get the type of play
 *
 * @param none
 * 
 * @return char the current value of the column coordinate
 */
  
  public int getColumn()
  {
    return this.column;
  }
}
