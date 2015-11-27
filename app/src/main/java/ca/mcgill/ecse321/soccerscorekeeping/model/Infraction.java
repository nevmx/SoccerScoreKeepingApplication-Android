/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.soccerscorekeeping.model;

// line 35 "../../../../../soccerscorekeeping.ump"
// line 71 "../../../../../soccerscorekeeping.ump"
public class Infraction
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Infraction Attributes
  private String type;
  private boolean isPenaltyKick;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Infraction(String aType, boolean aIsPenaltyKick)
  {
    type = aType;
    isPenaltyKick = aIsPenaltyKick;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(String aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPenaltyKick(boolean aIsPenaltyKick)
  {
    boolean wasSet = false;
    isPenaltyKick = aIsPenaltyKick;
    wasSet = true;
    return wasSet;
  }

  public String getType()
  {
    return type;
  }

  public boolean getIsPenaltyKick()
  {
    return isPenaltyKick;
  }

  public void delete()
  {}


  public String toString()
  {
	  String outputString = "";
    return super.toString() + "["+
            "type" + ":" + getType()+ "," +
            "isPenaltyKick" + ":" + getIsPenaltyKick()+ "]"
     + outputString;
  }
}