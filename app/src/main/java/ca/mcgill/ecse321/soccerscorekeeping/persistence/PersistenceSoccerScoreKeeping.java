package ca.mcgill.ecse321.soccerscorekeeping.persistence;

import java.util.Iterator;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class PersistenceSoccerScoreKeeping 
{
	public static void initializeXStream()
	{
		XStreamPersistence.setFilename("soccerscores.xml");
		XStreamPersistence.setAlias("Infraction",Infraction.class);
		XStreamPersistence.setAlias("Manager",Manager.class);
		XStreamPersistence.setAlias("Match",Match.class);
		XStreamPersistence.setAlias("Player",Player.class);
		XStreamPersistence.setAlias("Shot",Shot.class);
		XStreamPersistence.setAlias("Team",Team.class);
	}
	
	public static void loadSoccerScores()
	{
		Manager m = Manager.getInstance();
		PersistenceSoccerScoreKeeping.initializeXStream();
		
		Manager m2 = (Manager)XStreamPersistence.loadFromXMLwithXStream();
		if(m2!=null)
		{
			Iterator<Team> i = m2.getTeams().iterator();
			while(i.hasNext())
			{
				Team team = i.next();
				m.addTeam(team);
			}
			
			Iterator<Match> j = m2.getMatches().iterator();
			while(j.hasNext())
			{
				Match match = j.next();
				m.addMatche(match);
			}
		}
	}
	
}
