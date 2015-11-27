/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.soccerscorekeeping.model;
import java.util.*;

// line 10 "../../../../../soccerscorekeeping.ump"
// line 52 "../../../../../soccerscorekeeping.ump"
public class Match
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Match Attributes
  private String name;
  private int goals1;
  private int goals2;

  //Match Associations
  private List<Team> teams;
  private List<Shot> shots;
  private List<Infraction> infractions;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Match(String aName, int aGoals1, int aGoals2, Team... allTeams)
  {
    name = aName;
    goals1 = aGoals1;
    goals2 = aGoals2;
    teams = new ArrayList<Team>();
    boolean didAddTeams = setTeams(allTeams);
    if (!didAddTeams)
    {
      throw new RuntimeException("Unable to create Match, must have 2 teams");
    }
    shots = new ArrayList<Shot>();
    infractions = new ArrayList<Infraction>();
  }
  
  public Match(String aName,Team... allTeams)
  {
    name = aName;
    goals1 = 0;
    goals2 = 0;
    teams = new ArrayList<Team>();
    boolean didAddTeams = setTeams(allTeams);
    if (!didAddTeams)
    {
      throw new RuntimeException("Unable to create Match, must have 2 teams");
    }
    shots = new ArrayList<Shot>();
    infractions = new ArrayList<Infraction>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setGoals1(int aGoals1)
  {
    boolean wasSet = false;
    goals1 = aGoals1;
    wasSet = true;
    return wasSet;
  }
  
  public void incrementGoals1()
  {
	  goals1++;
  }
  
  public void incrementGoals2()
  {
	  goals2++;
  }

  public boolean setGoals2(int aGoals2)
  {
    boolean wasSet = false;
    goals2 = aGoals2;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getGoals1()
  {
    return goals1;
  }

  public int getGoals2()
  {
    return goals2;
  }

  public Team getTeam(int index)
  {
    Team aTeam = teams.get(index);
    return aTeam;
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

  public Shot getShot(int index)
  {
    Shot aShot = shots.get(index);
    return aShot;
  }

  public List<Shot> getShots()
  {
    List<Shot> newShots = Collections.unmodifiableList(shots);
    return newShots;
  }

  public int numberOfShots()
  {
    int number = shots.size();
    return number;
  }

  public boolean hasShots()
  {
    boolean has = shots.size() > 0;
    return has;
  }

  public int indexOfShot(Shot aShot)
  {
    int index = shots.indexOf(aShot);
    return index;
  }

  public Infraction getInfraction(int index)
  {
    Infraction aInfraction = infractions.get(index);
    return aInfraction;
  }

  public List<Infraction> getInfractions()
  {
    List<Infraction> newInfractions = Collections.unmodifiableList(infractions);
    return newInfractions;
  }

  public int numberOfInfractions()
  {
    int number = infractions.size();
    return number;
  }

  public boolean hasInfractions()
  {
    boolean has = infractions.size() > 0;
    return has;
  }

  public int indexOfInfraction(Infraction aInfraction)
  {
    int index = infractions.indexOf(aInfraction);
    return index;
  }

  public static int requiredNumberOfTeams()
  {
    return 2;
  }

  public static int minimumNumberOfTeams()
  {
    return 2;
  }

  public static int maximumNumberOfTeams()
  {
    return 2;
  }

  public boolean setTeams(Team... newTeams)
  {
    boolean wasSet = false;
    ArrayList<Team> verifiedTeams = new ArrayList<Team>();
    for (Team aTeam : newTeams)
    {
      if (verifiedTeams.contains(aTeam))
      {
        continue;
      }
      verifiedTeams.add(aTeam);
    }

    if (verifiedTeams.size() != newTeams.length || verifiedTeams.size() != requiredNumberOfTeams())
    {
      return wasSet;
    }

    teams.clear();
    teams.addAll(verifiedTeams);
    wasSet = true;
    return wasSet;
  }

  public static int minimumNumberOfShots()
  {
    return 0;
  }

  public boolean addShot(Shot aShot)
  {
    boolean wasAdded = false;
    if (shots.contains(aShot)) { return false; }
    shots.add(aShot);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShot(Shot aShot)
  {
    boolean wasRemoved = false;
    if (shots.contains(aShot))
    {
      shots.remove(aShot);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addShotAt(Shot aShot, int index)
  {  
    boolean wasAdded = false;
    if(addShot(aShot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShots()) { index = numberOfShots() - 1; }
      shots.remove(aShot);
      shots.add(index, aShot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShotAt(Shot aShot, int index)
  {
    boolean wasAdded = false;
    if(shots.contains(aShot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShots()) { index = numberOfShots() - 1; }
      shots.remove(aShot);
      shots.add(index, aShot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShotAt(aShot, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfInfractions()
  {
    return 0;
  }

  public boolean addInfraction(Infraction aInfraction)
  {
    boolean wasAdded = false;
    if (infractions.contains(aInfraction)) { return false; }
    infractions.add(aInfraction);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInfraction(Infraction aInfraction)
  {
    boolean wasRemoved = false;
    if (infractions.contains(aInfraction))
    {
      infractions.remove(aInfraction);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addInfractionAt(Infraction aInfraction, int index)
  {  
    boolean wasAdded = false;
    if(addInfraction(aInfraction))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInfractions()) { index = numberOfInfractions() - 1; }
      infractions.remove(aInfraction);
      infractions.add(index, aInfraction);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInfractionAt(Infraction aInfraction, int index)
  {
    boolean wasAdded = false;
    if(infractions.contains(aInfraction))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInfractions()) { index = numberOfInfractions() - 1; }
      infractions.remove(aInfraction);
      infractions.add(index, aInfraction);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInfractionAt(aInfraction, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    teams.clear();
    shots.clear();
    infractions.clear();
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "goals1" + ":" + getGoals1()+ "," +
            "goals2" + ":" + getGoals2()+ "]"
     + outputString;
  }
}