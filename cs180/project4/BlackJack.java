/**
 * Project 4 - BlackJack Simulator
 * 
 * @author Mihir Jham
 * @cslogin mjham
 *
 * @recitation R04F Dinesh Sriram
 *
 * @date 02/23/2012
 *
 */

import java.util.Random;
import java.text.DecimalFormat;

public class BlackJack
{
  static Random rnd = new Random(0);             //static Random object initialized with a seed of 0
  
  public static void main(String[] args)
  {

     int win, loss, tie, round;                  //Variables to hold number of wins, ties, losses
     int playerValue, dealerValue;               //Variables to hold the values of the current hand
     double winPercent, lossPercent, tiePercent; //Variables to hold percentage values
     
     DecimalFormat df = new DecimalFormat("##.000");     
     
     //Counters initialized
     win = 0; 
     loss = 0;
     tie = 0;
    
    //Loop to play the game 1,000,000 times
    for(int i = 0; i<1000000; i++)
    {
      playerValue = getHand();
      dealerValue = getHand();
      
      round = decideGame(dealerValue, playerValue);
      
      if(round == 1)
        win += 1;
      else if(round == -1)
        loss += 1;
      else
        tie += 1;
      
      round = 0;
      playerValue = 0;
      dealerValue = 0;
    }
    
    winPercent = (win/1000000.0)*100.0;
    lossPercent = (loss/1000000.0)*100.0;
    tiePercent = (tie/1000000.0)*100.0;
    
    System.out.println("Wins:\t"+(df.format(winPercent)) + "%");
    System.out.println("Losses:\t"+(df.format(lossPercent)) +"%");
    System.out.println("Ties:\t "+(df.format(tiePercent)) + "%");
  }
  
 /**
 * @method getCard
 * @param none
 * @return integer representing the value of the card
 * 
 * Uses a random number generator to select a card from the deck.
 * Combines the random values with a switch-case block to return the appropriate values.
 */
  
  public static int getCard()
  {
    int card; //Variable to hold the return value of the card
    
    card = rnd.nextInt(12) + 2;
    
    switch(card)
    {
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7: 
      case 8:
      case 9:
      case 10: 
        break;
      case 11:
      case 12:
      case 13: 
        card = 10;
        break;
      case 14: 
        card = 11;
        break;
    }
    return card;
  }
  
 /**
 * @method getHand
 * @param none
 * @return integer representing the value(sum) of the hand
 * 
 * Draws cards into the "hand", calculating its total value. Stops
 * when the total is at least 17. 
 * Handles Aces appropriately, with the intention of avoiding busts.
 */
  
  public static int getHand()
  {
    int value = 0; //Variable to hold the current hands total value
    int card;      //Variable to hold the value picked from the deck
    int limit = 2; //Variable to keep the limit of cards that can be drawn from the deck
    
    value = getCard();
    value = value + getCard();
    
    while(value < 17 && limit <= 4)
    {
      card = getCard();
      
      if(card == 11)
      {
        card = 1;
        value = value + card;
        limit = limit + 1;
      }
      else
      {
        value = value + card;
        limit = limit + 1;
      }
    }  
    return value;
  }
  
 /**
 * @method decideGame
 * @param dealerValue value of dealer's hand
 * @param playerValue value of player's hand
 * @return integer representing the decision of the round
 * 
 * Determines whether the round has been won by the dealer or the player
 * or if it has tied.
 */
  
  public static int decideGame(int dealerValue, int playerValue)
  {
    int decision = 0; //Variable to hold the return value of the decision
    
    if(playerValue > 21)
    {
      decision = -1;
    }
    else if(dealerValue > 21 && playerValue <= 21)
    {
      decision = 1;
    }
    else if(playerValue > dealerValue)
    {
      decision = 1;
    }
    else if(dealerValue > playerValue)
    {
      decision = -1;
    }
    else if(playerValue == dealerValue)
    {
      decision = 0;
    }
    
    return decision;
  }
}
  
  
      
    
    
    
    
 
    
    
      
              
  
  
  