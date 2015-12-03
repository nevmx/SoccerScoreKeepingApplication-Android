package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class playerTest 
{

	@Test
	public void test() 
	{
		
		Team t1 = new Team("Brazil",0);
		Player p1 = new Player("Ronaldo",t1);
		
		assertEquals(p1.getName(),"Ronaldo");
		
		//Create Dummy shots and infractions
		Shot s1 = new Shot(true);
		Shot s2 = new Shot(false);
		
		Infraction i1 = new Infraction("RED",true);
		Infraction i2 = new Infraction("YELLOW",false);
		
		//Add shots and infractions
		p1.addShot(s1);
		p1.addShot(s2);
		p1.addInfraction(i1);
		p1.addInfraction(i2);
		
		assertEquals(p1.getInfractions().size(),2);
		assertEquals(p1.getShots().size(),2);
		
		//Check all methods that will be used later
		assertEquals(p1.yellowCards(),1);
		assertEquals(p1.redCards(),1);
		assertEquals(p1.goalsScored(),1);
		assertEquals(p1.penaltyKicks(),1);
		
		//Delete all player data
		p1.delete();
		assertEquals(p1.penaltyKicks(),0);
		assertEquals(p1.yellowCards(),0);
		assertEquals(p1.redCards(),0);
		assertEquals(p1.goalsScored(),0);
		assertEquals(p1.getShots().size(),0);
		assertEquals(p1.getInfractions().size(),0);
	}

}
