/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.soccerscorekeeping.model;
import java.util.*;

// line 20 "../../../../../soccerscorekeeping.ump"
// line 58 "../../../../../soccerscorekeeping.ump"
public class Team
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Team Attributes
  private String name;
  private int points;

  //Team Associations
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Team(String aName, int aPoints)
  {
    name = aName;
    points = aPoints;
    players = new ArrayList<Player>();
  }
  
  public Team(String aName)
  {
	  name = aName;
	  points=0;
	  players = new ArrayList<Player>();
  }
  
  public Team()
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

  public boolean setPoints(int aPoints)
  {
    boolean wasSet = false;
    points = aPoints;
    wasSet = true;
    return wasSet;
  }
  
  public void addWin()
  {
	  points+=3;
  }
  
  public void addDraw()
  {
	  points+=1;
  }

  public String getName()
  {
    return name;
  }

  public int getPoints()
  {
    return points;
  }

  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }

  public static int minimumNumberOfPlayers()
  {
    return 0;
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (players.contains(aPlayer)) { return false; }
    players.add(aPlayer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (players.contains(aPlayer))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  
  public int goalsScored()
  {
	  int i=0;
	  for(Player p : players)
	  {
		  i+=p.goalsScored();
	  }
	  return i;
  }
  
  public int shotsTaken()
  {
	  int i=0;
	  for(Player p : players)
	  {
		  i+=p.getShots().size();
	  }
	  return i;
  }
  
  public int totalInfractions()
  {
	  int i=0;
	  for(Player p : players)
	  {
		  i+=p.getInfractions().size();
	  }
	  return i;
  }
  
  public int yellowCards()
  {
	  int i=0;
	  for(Player p : players)
	  {
		  i+=p.yellowCards();
	  }
	  return i;
  }
  
  public int redCards()
  {
	  int i=0;
	  for(Player p : players)
	  {
		  i+=p.redCards();
	  }
	  return i;
  }
  
  public int penaltyKicks()
  {
	  int i=0;
	  for(Player p : players)
	  {
		  i+=p.penaltyKicks();
	  }
	  return i;
  }

  public void delete()
  {
    players.clear();
  }


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "points" + ":" + getPoints()+ "]"
     + outputString;
  }
}