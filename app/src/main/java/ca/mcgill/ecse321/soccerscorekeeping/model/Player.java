/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.soccerscorekeeping.model;
import java.util.*;

// line 27 "../../../../../soccerscorekeeping.ump"
// line 64 "../../../../../soccerscorekeeping.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;

  //Player Associations
  private List<Infraction> infractions;
  private List<Shot> shots;
  private Team team;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Team aTeam)
  {
    name = aName;
    infractions = new ArrayList<Infraction>();
    shots = new ArrayList<Shot>();
    if (!setTeam(aTeam))
    {
      throw new RuntimeException("Unable to create Player due to aTeam");
    }
  }
  
  public Player()
  {
	  
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

  public String getName()
  {
    return name;
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

  public Team getTeam()
  {
    return team;
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

  public boolean setTeam(Team aNewTeam)
  {
    boolean wasSet = false;
    if (aNewTeam != null)
    {
      team = aNewTeam;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    infractions.clear();
    shots.clear();
    team = null;
  }

  //Returns the total number of goals score by a player
  public int goalsScored()
  {
	  int i=0;
	  for(Shot s:shots)
	  {
		  if(s.getIsGoal()==true)
		  {
			  i++;
		  }
	  }
	  return i;
  }
  
  public int yellowCards()
  {
	  int i =0;
	  for(Infraction j : infractions)
	  {
		  if(j.getType().toLowerCase().equals("yellow"))
		  {
			  i++;
		  }
	  }
	  return i;
  }
  
  public int redCards()
  {
	  int i = yellowCards();
	  return infractions.size()-i;
  }
  
  public int penaltyKicks()
  {
	  int i =0;
	  for(Infraction j : infractions)
	  {
		  if(j.getIsPenaltyKick()==true)
		  {
			  i++;
		  }
	  }
	  return i;
  }

  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "team = "+(getTeam()!=null?Integer.toHexString(System.identityHashCode(getTeam())):"null")
     + outputString;
  }
}