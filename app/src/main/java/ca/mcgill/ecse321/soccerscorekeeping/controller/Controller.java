package ca.mcgill.ecse321.soccerscorekeeping.controller;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;
import ca.mcgill.ecse321.soccerscorekeeping.persistence.XStreamPersistence;

public class Controller
{
	public Controller()
	{
		
	}
	
	public void createMatch(String name,Team team1, Team team2)
	{
		Match m1 = new Match(name,team1, team2);
		Manager m = Manager.getInstance();
		m.addMatche(m1);
		XStreamPersistence.saveToXMLwithXStream(m);
	}
	
	public Match createMatch(String team1, String team2)
	{
		Manager m = Manager.getInstance();
		Team t1 = new Team();
		Team t2 = new Team();
		
		for(Team team:m.getTeams())
		{
			if(team.getName().equals(team1))
			{
				t1=team;
			}
			else if(team.getName().equals(team2))
			{
				t2=team;
			}
		}
		
		
		Match m1 = new Match(t1.getName()+" v "+t2.getName(),t1, t2);
		m.addMatche(m1);
		XStreamPersistence.saveToXMLwithXStream(m);
		return m1;
	}
	
	public void createShot(Player player,Match match, boolean goal)
	{
		Shot s = new Shot(goal);
		player.addShot(s);
		match.addShot(s);
		
		if(goal==true)
		{
			if(player.getTeam().getName().equals(match.getTeam(0).getName()))
			{
				match.incrementGoals1();
			}
			else
			{
				match.incrementGoals2();
			}
			
		}
		
		Manager m = Manager.getInstance();
		XStreamPersistence.saveToXMLwithXStream(m);
	}
	
	//Assuming no two players have the same name.
	public String createShot(String player,Match match, boolean goal)
	{
		Manager m = Manager.getInstance();
		Shot s = new Shot(goal);
		
		Player p1 = new Player();
		for(Team t : m.getTeams())
		{
			for(Player p : t.getPlayers())
			{
				if(p.getName().equals(player))
				{
					p1=p;
				}
			}
		}
		
		if(p1.getName()==null)
		{
			return "Player does not exist";
		}
		
		match.addShot(s);
		p1.addShot(s);
		
		if(goal==true)
		{
			if(p1.getTeam().getName().equals(match.getTeam(0).getName()))
			{
				match.incrementGoals1();
			}
			else
			{
				match.incrementGoals2();
			}
			
		}
		
		XStreamPersistence.saveToXMLwithXStream(m);
		return null;
	}
	
	public void createInfraction(Player player, Match match, String type, boolean isPenalty)
	{
		Infraction i1 = new Infraction(type, isPenalty);
		match.addInfraction(i1);
		player.addInfraction(i1);
		Manager m = Manager.getInstance();
		XStreamPersistence.saveToXMLwithXStream(m);
	}
	
	public String createInfraction(String player, Match match, String type, boolean isPenalty)
	{	
		Manager m =Manager.getInstance();
		Infraction i1 = new Infraction(type, isPenalty);
		
		Player p1 = new Player();
		for(Team t : m.getTeams())
		{
			for(Player p : t.getPlayers())
			{
				if(p.getName().equals(player))
				{
					p1=p;
				}
			}
		}
		
		if(p1.getName()==null)
		{
			return "Player does not exist";
		}
		
		match.addInfraction(i1);
		p1.addInfraction(i1);
		
		XStreamPersistence.saveToXMLwithXStream(m);
		return null;
	}
	
	public void chooseWinner(Match match)
	{
		int a = match.getGoals1();
		int b = match.getGoals2();
		
		if(a<b)
		{
			match.getTeam(1).addWin();
		}
		else if(a>b)
		{
			match.getTeam(0).addWin();
		}
		else
		{
			match.getTeam(0).addDraw();
			match.getTeam(1).addDraw();
		}
		
		Manager m = Manager.getInstance();
		XStreamPersistence.saveToXMLwithXStream(m);
	}
	
	public static Team findTeam(String teamName)
	{
		Manager m = Manager.getInstance();
		Team team = new Team();
		for(Team t : m.getTeams())
		{
			if(t.getName().equals(teamName))
			{
				team=t;
				return team;
			}
		}
		
		return null;
	}
	
	public static Player findPlayer(Team team,String playerName)
	{
		Player player = new Player();
		for(Player p : team.getPlayers())
		{
			if(p.getName().equals(playerName))
			{
				player=p;
				return player;
			}
		}
		return null;
	}
	
	public Player[] topPlayers(int top, String sortChoice)
	{
		Manager m = Manager.getInstance();
		int allPlayers=0;
		for(Team t : m.getTeams())
		{
			allPlayers+=t.getPlayers().size();
		}
		
		Player[] players;
		
		if(allPlayers<top)
		{
			players=new Player[allPlayers];
		}
		else
		{
			players=new Player[top];
		}
		
		Player[] allPlayersList = new Player[allPlayers];
		
		int i=0;
		for(Team t : m.getTeams())
		{
			for(Player p : t.getPlayers())
			{
				allPlayersList[i]=p;
				i++;
			}
		}
		
		if(sortChoice.equals("Goals"))
		{
			sortByGoals(allPlayersList);
		}
		else
		{
			sortByInfractions(allPlayersList);
		}
		
		for(int j=0;j<players.length;j++)
		{
			players[j]=allPlayersList[j];
		}
		
		return players;
	}
	
	public void sortByGoals(Player[] input)
	{
		boolean swapped=true;
		while(swapped==true)
		{
			swapped=false;
			for(int i=1;i<input.length;i++)
			{
				if(input[i-1].goalsScored()<input[i].goalsScored())
				{
					swap(input,i-1,i);
					swapped=true;
				}
			}
		}
	}
	
	public void sortByInfractions(Player[] input)
	{
		boolean swapped=true;
		while(swapped==true)
		{
			swapped=false;
			for(int i=1;i<input.length;i++)
			{
				if(input[i-1].getInfractions().size()<input[i].getInfractions().size())
				{
					swap(input,i-1,i);
					swapped=true;
				}
			}
		}
	}
	
	public Team[] topTeams(int top, String sortChoice)
	{
		Manager m = Manager.getInstance();
		int allTeams=m.getTeams().size();
		
		Team[] teams;
		
		if(allTeams<top)
		{
			teams=new Team[allTeams];
		}
		else
		{
			teams=new Team[top];
		}
		
		Team[] allTeamsList = new Team[allTeams];
		int i=0;
		for(Team t : m.getTeams())
		{
			allTeamsList[i]=t;
			i++;
		}
		
		if(sortChoice.equals("Goals"))
		{
			sortTeamsByGoals(allTeamsList);
		}
		else if(sortChoice.equals("Infractions"))
		{
			sortTeamsByInfractions(allTeamsList);
		}
		else
		{
			sortTeamsByPoints(allTeamsList);
		}
		
		for(int j=0;j<teams.length;j++)
		{
			teams[j]=allTeamsList[j];
		}
		
		return teams;
	}
	
	public void sortTeamsByGoals(Team[] input)
	{
		boolean swapped=true;
		while(swapped==true)
		{
			swapped=false;
			for(int i=1;i<input.length;i++)
			{
				if(input[i-1].goalsScored()<input[i].goalsScored())
				{
					swap(input,i-1,i);
					swapped=true;
				}
			}
		}
	}
	
	public void sortTeamsByInfractions(Team[] input)
	{
		boolean swapped=true;
		while(swapped==true)
		{
			swapped=false;
			for(int i=1;i<input.length;i++)
			{
				if(input[i-1].totalInfractions()<input[i].totalInfractions())
				{
					swap(input,i-1,i);
					swapped=true;
				}
			}
		}
	}
	
	public void sortTeamsByPoints(Team[] input)
	{
		boolean swapped=true;
		while(swapped==true)
		{
			swapped=false;
			for(int i=1;i<input.length;i++)
			{
				if(input[i-1].getPoints()<input[i].getPoints())
				{
					swap(input,i-1,i);
					swapped=true;
				}
			}
		}
	}
	
	public void swap(Team[] a, int arg1, int arg2)
	{
		Team temp = new Team();
		temp = a[arg1];
		a[arg1]=a[arg2];
		a[arg2]=temp;
	}
	
	public void swap(Player[] a, int arg1, int arg2)
	{
		Player temp=new Player();
		temp = a[arg1];
		a[arg1]=a[arg2];
		a[arg2]=temp;
	}
}
