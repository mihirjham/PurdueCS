import java.util.Scanner;

public class Soccer
{
	int numberOfTeams;
	int numberOfGamesPlayed;
	Team team[];
	Match matches[];
	Scanner scanner;
	String resultsTable[][];
	String rowHeader;

	public Soccer()
	{
		scanner = new Scanner(System.in);

		this.numberOfTeams = Integer.parseInt(scanner.nextLine());
		if(this.numberOfTeams == 0)
			System.exit(0);

		team = new Team[this.numberOfTeams];
		resultsTable = new String[this.numberOfTeams+1][this.numberOfTeams+1];

		for(int i=0; i < this.numberOfTeams; i++)
		{
			String teamName = scanner.nextLine();
			team[i] = new Team(teamName);
		}

		this.numberOfGamesPlayed = Integer.parseInt(scanner.nextLine());
		matches = new Match[this.numberOfGamesPlayed];

		for(int j=0; j < this.numberOfGamesPlayed; j++)
		{
			matches[j] = new Match(scanner.nextLine());
		}

		int max = (team[0].getName()).length();
		for(int i=0; i < this.numberOfTeams-1; i++)
		{
			max = Math.max(max, (team[i+1].getName()).length());
		}

		resultsTable[0][0] = new String(new char[max]).replace('\0', ' ');
		for(int i=0; i<this.numberOfTeams; i++)
		{
			int numberOfSpaces = max - team[i].getName().length();
			resultsTable[i+1][0] = team[i].getName() + new String(new char[numberOfSpaces]).replace('\0', ' ');
			resultsTable[0][i+1] = team[i].getName().substring(0,3);
		}

		String repeat = "---+";
		rowHeader = "+" + new String(new char[max]).replace('\0','-') + "+" + new String(new char[this.numberOfTeams]).replace("\0", repeat);
	}

	public void processResults()
	{
		for(int i = 0; i < this.numberOfTeams; i++)
		{
			for(int j = 0; j < this.numberOfGamesPlayed; j++)
			{
				if(team[i].getName().equals(matches[j].getHostTeam()))
				{
					for(int k = 0; k < this.numberOfTeams; k++)
					{
						if(matches[j].getGuestTeam().equals(team[k].getName()))
						{
							resultsTable[i+1][k+1] = matches[j].getMatchScore();

							team[i].gamePlayed();
							team[k].gamePlayed();

							String score = matches[j].getMatchScore();
							String splits[] = score.split(":");
							int scoreHome = Integer.parseInt(splits[0]);
							int scoreAway = Integer.parseInt(splits[1]);

							team[i].addGoalsFor(scoreHome);
							team[i].addGoalsAgainst(scoreAway);
							team[k].addGoalsFor(scoreAway);
							team[k].addGoalsAgainst(scoreHome);

							if(scoreHome > scoreAway)
							{
								team[i].gameWon();
								team[k].gameLost();
							}
							else if(scoreAway > scoreHome)
							{
								team[i].gameLost();
								team[k].gameWon();
							}
							else
							{
								team[i].gameTied();
								team[k].gameTied();
							}
						}
						else
						{
							resultsTable[i+1][k+1] = "   ";
						}
					}
				}
			}
		}

		for(int i = 0; i < this.numberOfTeams; i++)
		{
			resultsTable[i+1][i+1] = " X ";
			team[i].setPoints();
		}
	}

	public void printResults()
	{
		System.out.println("RESULTS:");
		System.out.println(this.rowHeader);

		for(int i = 0; i<=this.numberOfTeams; i++)
		{
			System.out.print("|");
			for(int j = 0; j <= this.numberOfTeams; j++)
			{
				System.out.print(resultsTable[i][j] + "|");
			}
			System.out.println("\n" + this.rowHeader);
		}

		System.out.println(" ");
	}

	public void printStandings()
	{
		System.out.println("STANDINGS");
		System.out.println("----------");

		for(int i=0; i < this.numberOfTeams; i++)
		{
			int numberOfSpaces = this.resultsTable[0][0].length() - team[i].getName().length();

			System.out.println(i+1+ ". " + (team[i].getName() + new String(new char[numberOfSpaces]).replace('\0',' ')) + " " + team[i].getGames() + " " + team[i].getWon() + " " + team[i].getTied() + " " + team[i].getLost() + " " + team[i].getGoalsFor() + ":" + team[i].getGoalsAgainst() + " " + team[i].getPoints());
		}
	}

	public void sort_Points(Team team[], int first, int last)
	{
		int i = first;
		int j = last;
		int temp;
		int middle = team[(first+last)/2].getPoints();

		while(i < j)
		{
			while(team[i].getPoints() > middle)
			{
				i++;
			}

			while(team[j].getPoints() < middle)
			{
				j--;
			}

			if( j >= i)
			{
				swap(team, i, j);
				i++;
				j--;
			}
		}

		if(first < j)
		{
			sort_Points(team, first, j);
		}

		if(i < last)
		{
			sort_Points(team, i, last);
		}
	}

	public void sort_Goals(Team team[], int first, int last)
	{
		int i = first;
		int j = last;
		int temp;
		int middle = team[(first+last)/2].getGoalsFor();

		while(i < j)
		{
			while(team[i].getGoalsFor() > middle)
			{
				i++;
			}

			while(team[j].getGoalsFor() < middle)
			{
				j--;
			}

			if( j >= i)
			{
				swap(team, i, j);
				i++;
				j--;
			}
		}

		if(first < j)
		{
			sort_Goals(team, first, j);
		}

		if(i < last)
		{
			sort_Goals(team, i, last);
		}
	}

	public void sort_Difference(Team team[], int first, int last)
	{
		int i = first;
		int j = last;
		int temp;
		int middle = team[(first+last)/2].getGoalsDifference();

		while(i < j)
		{
			while(team[i].getGoalsDifference() > middle)
			{
				i++;
			}

			while(team[j].getGoalsDifference() < middle)
			{
				j--;
			}

			if( j >= i)
			{
				swap(team, i, j);
				i++;
				j--;
			}
		}

		if(first < j)
		{
			sort_Difference(team, first, j);
		}

		if(i < last)
		{
			sort_Difference(team, i, last);
		}
	}

	public void sort_Won(Team team[], int first, int last)
	{
		int i = first;
		int j = last;
		int temp;
		int middle = team[(first+last)/2].getWon();

		while(i < j)
		{
			while(team[i].getWon() > middle)
			{
				i++;
			}

			while(team[j].getWon() < middle)
			{
				j--;
			}

			if( j >= i)
			{
				swap(team, i, j);
				i++;
				j--;
			}
		}

		if(first < j)
		{
			sort_Won(team, first, j);
		}

		if(i < last)
		{
			sort_Won(team, i, last);
		}
	}

	public void swap(Team team[], int dex1, int dex2)
	{
		Team temp = team[dex1];
		team[dex1] = team[dex2];
		team[dex2] = temp;
	}

	public void sortTeams()
	{
		Team originalCopy[] = new Team[this.numberOfTeams];
		System.arraycopy(this.team, 0, originalCopy, 0, this.numberOfTeams);

		sort_Points(this.team, 0, this.numberOfTeams-1);

		int place = this.numberOfTeams-1;
		for(int i=0; i<this.numberOfTeams-1; i++)
		{
			if(team[i].getPoints() == team[i+1].getPoints())
			{
				place--;
			}
		}

		if(place == this.numberOfTeams-1)
		{	
			return;
		}
		else
		{
			sort_Goals(this.team, place, this.numberOfTeams-1);
		}

		int newPlace = this.numberOfTeams-1;
		for(int i=place; i<this.numberOfTeams-1; i++)
		{
			if(team[i].getGoalsFor() == team[i+1].getGoalsFor())
			{
				newPlace--;	
			}
		}

		if(newPlace == this.numberOfTeams-1)
		{
			return;
		}
		else
		{
			sort_Difference(this.team, newPlace, this.numberOfTeams-1);
		}

		place = this.numberOfTeams-1;
		for(int i=newPlace; i<this.numberOfTeams-1; i++)
		{
			if(team[i].getGoalsDifference() == team[i+1].getGoalsDifference())
			{
				place--;
			}
		}

		if(place == this.numberOfTeams-1)
		{
			return;
		}
		else
		{
			sort_Won(this.team, place, this.numberOfTeams-1);
		}

		newPlace = this.numberOfTeams-1;
		for(int i = place ; i<this.numberOfTeams-1; i++)
		{
			if(team[i].getWon() == team[i+1].getWon())
			{
				newPlace--;
			}
		}

		if(newPlace == this.numberOfTeams-1)
		{
			return;
		}
		else
		{
			int i = newPlace;
			int j = this.numberOfTeams-1;

			while(i < j)
			{
				swap(team, i++, j--);
			}
		}
	}

	public static void main(String args[])
	{
		Soccer soccer = new Soccer();
		soccer.processResults();
		soccer.printResults();
		soccer.sortTeams();
		soccer.printStandings();
	}
}
