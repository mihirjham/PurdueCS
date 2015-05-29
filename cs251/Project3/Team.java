class Team
{
	String name;
	int games, won, tied, lost;
	int goalsFor, goalsAgainst;
	int points;

	public Team(String name)
	{
		this.name = name;
		this.games = 0;
		this.won = 0;
		this.tied = 0;
		this.lost = 0;
		this.goalsFor = 0;
		this.goalsAgainst = 0;
		this.points = 0;
	}

	public String getName()
	{
		return this.name;
	}

	public int getGames()
	{
		return this.games;
	}

	public int getWon()
	{
		return this.won;
	}

	public int getTied()
	{
		return this.tied;
	}

	public int getLost()
	{
		return this.lost;
	}

	public int getGoalsFor()
	{
		return this.goalsFor;
	}

	public int getGoalsAgainst()
	{
		return this.goalsAgainst;
	}

	public int getPoints()
	{
		return this.points;
	}

	public void gamePlayed()
	{
		this.games++;
	}

	public void gameWon()
	{
		this.won++;
	}

	public void gameLost()
	{
		this.lost++;
	}

	public void gameTied()
	{
		this.tied++;
	}
	
	public void addGoalsFor(int goals)
	{
		this.goalsFor = this.goalsFor + goals;
	}

	public void addGoalsAgainst(int goals)
	{
		this.goalsAgainst = this.goalsAgainst + goals;
	}
	
	public int getGoalsDifference()
	{
		return this.goalsFor - this.goalsAgainst;
	}

	public void setPoints()
	{
		this.points = (this.won*3) + (this.tied*1);
	}
}
