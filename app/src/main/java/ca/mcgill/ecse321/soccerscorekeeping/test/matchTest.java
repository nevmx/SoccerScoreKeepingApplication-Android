package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class matchTest {

	@Test
	public void test() 
	{
		Team t1 = new Team("Pakistan");
		Team t2 = new Team("England");
		
		Match m = new Match("Pakistan v England", 0, 0, t1,t2);
		
		//Check if match eas created correctly
		assertEquals(m.getName(),"Pakistan v England");
		assertEquals(m.getGoals1(),0);
		assertEquals(m.getGoals2(),0);
		
		//Check increment goals methods
		m.incrementGoals1();
		m.incrementGoals2();
		assertEquals(m.getGoals1(),1);
		assertEquals(m.getGoals2(),1);
		
		//Create and add Shots
		Shot s1 = new Shot(true);
		Shot s2 = new Shot(false);
		m.addShot(s1);m.addShot(s2);
		assertEquals(m.getShot(0).getIsGoal(),true);
		assertEquals(m.getShot(1).getIsGoal(),false);
		assertEquals(m.getShots().size(),2);
		
		//Create and add infractions
		Infraction i1 = new Infraction("RED",true);
		Infraction i2 = new Infraction("YELLOW",false);
		m.addInfraction(i1);m.addInfraction(i2);
		assertEquals(m.getInfractions().size(),2);
		
		//Delete all match data
		m.delete();
		assertEquals(m.getTeams().size(),0);
		assertEquals(m.getShots().size(),0);
		assertEquals(m.getInfractions().size(),0);
		
	}

}
