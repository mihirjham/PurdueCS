import java.lang.Math;
import java.util.Scanner;
import java.util.Random;

class RPS
{
  private static final int ROCK = 1;
  private static final int PAPER = 2;
  private static final int SCISSORS = 3;
  
  private int user_choice1, user_choice2, user_choice3, ai_choice1, ai_choice2, ai_choice3;
  private int userScore = 0;
  private int aiScore = 0;
  
  private int userChoice()
  {
    Scanner scanner = new Scanner(System.in);
    
    printTable();
    System.out.print("Enter your choice: ");
    return Integer.parseInt(scanner.next()); 
  }
  
  private void printTable()
  {
    System.out.println("1. Rock");
    System.out.println("2. Paper");
    System.out.println("3. Scissors");
  }
  
  private int aiChoice()
  {
    int choice;
    Random rnd = new Random();
    
    choice = rnd.nextInt(2) + 1;
    
    if(choice == ROCK)
    {
      System.out.println("AI: Rock");
    }
    else if(choice == PAPER)
    {
      System.out.println("AI: Paper");
    }
    else if(choice == SCISSORS)
    {
      System.out.println("AI: Scissors");
    }
        
    return choice;
  }
  
  private void checkScore(int user_choice, int ai_choice, int round)
  {
    if(user_choice == ai_choice)
    {
      roundCheck(round, "Tie");
    }
    else if(user_choice == ROCK && ai_choice == PAPER)
    {
      aiScore = aiScore + 1;
      roundCheck(round, "AI");
    }
    else if(user_choice == ROCK && ai_choice == SCISSORS)
    {
      userScore = userScore + 1;
      roundCheck(round, "Player");
    }
    else if(user_choice == PAPER && ai_choice == ROCK)
    {
      userScore = userScore + 1;
      roundCheck(round, "Player");
    }
    else if(user_choice == PAPER && ai_choice == SCISSORS)
    {
      aiScore += 1;
      roundCheck(round, "AI");
    }
    else if(user_choice == SCISSORS && ai_choice == PAPER)
    {
      userScore += 1;
      roundCheck(round, "Player");
    }
    else if(user_choice == SCISSORS && ai_choice == ROCK)
    {
      aiScore += 1;
      roundCheck(round, "AI");
    }
  }
  
  private void checkWinner()
  {
    System.out.println("Player vs AI: " + userScore + " vs. " + aiScore);
    if(userScore > aiScore)
    {
      System.out.println("Winner is Player!");
    }
    else if(aiScore > userScore)
    {
      System.out.println("Winner is AI!");
    }
    else
    {
      System.out.println("Tie!");
    }
  }
  
  private void roundCheck(int round, String result)
  {
    if (result.equals("Tie"))
    {
      System.out.println("Round " + round + ": Tie!\n");
    }
    else
    {
      System.out.println("Round " + round + ": Winner is "+ result +"!\n");
    }
  }
    
  public void game()
  {
    user_choice1 = userChoice();
    ai_choice1 = aiChoice();
    checkScore(user_choice1, ai_choice1, 1);
    
    user_choice2 = userChoice();
    ai_choice2 = aiChoice();
    checkScore(user_choice2, ai_choice2, 2);
    
    user_choice3 = userChoice();
    ai_choice3 = aiChoice();
    checkScore(user_choice3, ai_choice3, 3);
   
    checkWinner();
  }
}