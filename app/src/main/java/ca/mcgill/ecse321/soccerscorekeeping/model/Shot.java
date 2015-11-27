/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.soccerscorekeeping.model;

// line 41 "../../../../../soccerscorekeeping.ump"
// line 76 "../../../../../soccerscorekeeping.ump"
public class Shot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shot Attributes
  private boolean isGoal;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shot(boolean aIsGoal)
  {
    isGoal = aIsGoal;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsGoal(boolean aIsGoal)
  {
    boolean wasSet = false;
    isGoal = aIsGoal;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsGoal()
  {
    return isGoal;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "isGoal" + ":" + getIsGoal()+ "]"
     + outputString;
  }
}