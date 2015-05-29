class Match
{
	String hostTeam;
	String guestTeam;
	String matchScore;

	public Match(String match)
	{
		String splits[] = match.split(" ");
		this.hostTeam = splits[0];
		this.guestTeam = splits[2];
		this.matchScore = splits[3];
	}
	
	public String getHostTeam()
	{
		return this.hostTeam;
	}

	public String getGuestTeam()
	{
		return this.guestTeam;
	}

	public String getMatchScore()
	{
		return this.matchScore;
	}
}
