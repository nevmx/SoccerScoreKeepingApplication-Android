package ca.mcgill.ecse321.soccerscorekeeping.persistence;

import android.content.Context;

import java.io.File;
import java.util.Iterator;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

/*
 * This class has been modified from the original "Java Swing" app because the methods used to read
 * and write XML files are different for the Android internal storage. See loadSoccerScores method.
 * - Max Neverov 11/29/2015
 */

public class PersistenceSoccerScoreKeeping
{
	public static void initializeXStream(File filesDir)
	{
		XStreamPersistence.setFilename("soccerscores.xml");
		XStreamPersistence.setAlias("Infraction", Infraction.class);
		XStreamPersistence.setAlias("Manager",Manager.class);
		XStreamPersistence.setAlias("Team",Team.class);
		XStreamPersistence.setAlias("Match",Match.class);
		XStreamPersistence.setAlias("Player",Player.class);
		XStreamPersistence.setAlias("Shot",Shot.class);


		XStreamPersistence.setFilesDir(filesDir);
	}

	/*
	 * This method has been modified to work on Android machines. You need to get the files directory
	 * from the context of the application. This method is called in the MainActivity class.
	 */
	public static void loadSoccerScores(Context context)
	{
		// This is what I inserted
		File filesDir = context.getFilesDir();

		Manager m = Manager.getInstance();
		PersistenceSoccerScoreKeeping.initializeXStream(filesDir);
		
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
