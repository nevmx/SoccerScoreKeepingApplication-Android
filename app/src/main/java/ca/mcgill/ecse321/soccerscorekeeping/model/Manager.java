/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.soccerscorekeeping.model;
import java.util.*;

import ca.mcgill.ecse321.soccerscorekeeping.persistence.XMLSequence;

// line 3 "../../../../../soccerscorekeeping.ump"
// line 46 "../../../../../soccerscorekeeping.ump"

@XMLSequence({
        "teams",
        "matches"
})
public class Manager
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Manager theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private List<Team> teams;
  private List<Match> matches;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private Manager()
  {
    teams = new ArrayList<Team>();
    matches = new ArrayList<Match>();
  }

  public static Manager getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new Manager();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Team getTeam(int index)
  {
    Team aTeam = teams.get(index);
    return aTeam;
  }
  
  public Team getTeam(String teamName) 
  {
		Team temp = new Team();
			
		for(Team t : teams)
		{
			if(t.getName().toLowerCase().equals(teamName.toLowerCase()))
			{
				temp=t;
			}
	  	}
		if(temp.getName()==null)
		{
			return null;
		} 
		return temp;
		  
  }

  public List<Team> getTeams()
  {
    List<Team> newTeams = Collections.unmodifiableList(teams);
    return newTeams;
  }

  public int numberOfTeams()
  {
    int number = teams.size();
    return number;
  }

  public boolean hasTeams()
  {
    boolean has = teams.size() > 0;
    return has;
  }

  public int indexOfTeam(Team aTeam)
  {
    int index = teams.indexOf(aTeam);
    return index;
  }

  public Match getMatche(int index)
  {
    Match aMatche = matches.get(index);
    return aMatche;
  }

  public List<Match> getMatches()
  {
    List<Match> newMatches = Collections.unmodifiableList(matches);
    return newMatches;
  }

  public int numberOfMatches()
  {
    int number = matches.size();
    return number;
  }

  public boolean hasMatches()
  {
    boolean has = matches.size() > 0;
    return has;
  }

  public int indexOfMatche(Match aMatche)
  {
    int index = matches.indexOf(aMatche);
    return index;
  }

  public static int minimumNumberOfTeams()
  {
    return 0;
  }

  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    teams.add(aTeam);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    if (teams.contains(aTeam))
    {
      teams.remove(aTeam);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addTeamAt(Team aTeam, int index)
  {  
    boolean wasAdded = false;
    if(addTeam(aTeam))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeams()) { index = numberOfTeams() - 1; }
      teams.remove(aTeam);
      teams.add(index, aTeam);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTeamAt(Team aTeam, int index)
  {
    boolean wasAdded = false;
    if(teams.contains(aTeam))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeams()) { index = numberOfTeams() - 1; }
      teams.remove(aTeam);
      teams.add(index, aTeam);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTeamAt(aTeam, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfMatches()
  {
    return 0;
  }

  public boolean addMatche(Match aMatche)
  {
    boolean wasAdded = false;
    if (matches.contains(aMatche)) { return false; }
    matches.add(aMatche);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMatche(Match aMatche)
  {
    boolean wasRemoved = false;
    if (matches.contains(aMatche))
    {
      matches.remove(aMatche);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addMatcheAt(Match aMatche, int index)
  {  
    boolean wasAdded = false;
    if(addMatche(aMatche))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatches()) { index = numberOfMatches() - 1; }
      matches.remove(aMatche);
      matches.add(index, aMatche);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMatcheAt(Match aMatche, int index)
  {
    boolean wasAdded = false;
    if(matches.contains(aMatche))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatches()) { index = numberOfMatches() - 1; }
      matches.remove(aMatche);
      matches.add(index, aMatche);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMatcheAt(aMatche, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    teams.clear();
    matches.clear();
  }

}