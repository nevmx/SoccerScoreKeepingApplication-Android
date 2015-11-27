package ca.mcgill.ecse321.soccerscorekeeping.admin;

import java.util.ArrayList;

import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;
import ca.mcgill.ecse321.soccerscorekeeping.persistence.XStreamPersistence;

public class managerTools 
{
	public managerTools()
	{
		
	}
	
	public String createTeam(String name)
	{
		if(name!=null && !name.equals(""))
		{
			Manager m = Manager.getInstance();
			
			for(Team team:m.getTeams())
			{
				if(team.getName().toLowerCase().equals(name.toLowerCase()))
				{
					return "The team already exists.";
				}
			}
			
			
			Team t1= new Team(name);
			m.addTeam(t1);
			XStreamPersistence.saveToXMLwithXStream(m);
			return null;
		}
		else
		{
			return "The team name cannot be empty.";
		}
		
	}
	
	public void createPlayer(String playerName, Team team)
	{
		Manager m = Manager.getInstance();
		Player p1 = new Player(playerName, team);
		int index = m.indexOfTeam(team);
		m.getTeam(index).addPlayer(p1);
		XStreamPersistence.saveToXMLwithXStream(m);
	}
	
	public String deleteTeam(String teamName) {
		
		if(teamName==null || teamName.equals(""))
		{
			return "The team name cannot be empty.";
		}
		
		Manager m = Manager.getInstance();
		
		Team team = new Team();
		
		for(Team t : m.getTeams())
		{
			if(t.getName().toLowerCase().equals(teamName.toLowerCase()))
			{
				team=t;
			}
		}
		
		if(team.getName()==null)
		{
			return "That team does not exist.";
		}
		
		m.removeTeam(team);
		XStreamPersistence.saveToXMLwithXStream(m);
		return null;
	}
	
	public String deletePlayer(String playerName, String teamName)
	{
		
		if(playerName==null || teamName==null || playerName.equals("") || teamName.equals(""))
		{
			return "The team/player name cannot be empty.";
		}
		
		Manager m = Manager.getInstance();
		
		Team temp = new Team();
		
		for(Team t : m.getTeams())
		{
			if(t.getName().toLowerCase().equals(teamName.toLowerCase()))
			{
				temp=t;
			}
		}
		
		if(temp.getName()==null)
		{
			return "That team does not exist.";
		}
		
		for(Player player:temp.getPlayers())
		{
			if(player.getName().toLowerCase().equals(playerName.toLowerCase()))
			{
				if(temp.removePlayer(player)) {
					XStreamPersistence.saveToXMLwithXStream(m);
					return null;
				}
				return "Error occured in deleting player";
			}
		}
		
		return "Player Does not exist.";
	}
	
	public String createPlayer(String playerName, String teamName)
	{
		if(playerName==null || teamName==null || playerName.equals("") || teamName.equals(""))
		{
			return "The team/player name cannot be empty.";
		}
		
		Manager m = Manager.getInstance();
		
		Team temp = new Team();
		
		for(Team t : m.getTeams())
		{
			if(t.getName().toLowerCase().equals(teamName.toLowerCase()))
			{
				temp=t;
			}
		}
		
		if(temp.getName()==null)
		{
			return "That team does not exist.";
		}
		
		for(Player player:temp.getPlayers())
		{
			if(player.getName().toLowerCase().equals(playerName.toLowerCase()))
			{
				return "The player already exists.";
			}
		}
		
		Player p1 = new Player(playerName,temp);
		
		int index = m.indexOfTeam(temp);
		m.getTeam(index).addPlayer(p1);
		XStreamPersistence.saveToXMLwithXStream(m);
		
		return null;
	}
}
