package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class teamTest 
{

	@Test
	public void test()
	{
		Team t1 = new Team("Brazil",0);
		Player p1 = new Player("Ronaldo",t1);
		Player p2 = new Player("Kaka",t1);
		Shot s1 = new Shot(true);
		Shot s2 = new Shot(true);
		Shot s3 = new Shot(true);
		Infraction i1 = new Infraction("RED",true);
		Infraction i2 = new Infraction("YELLOW",true);
		p1.addShot(s1);
		p1.addInfraction(i1);
		p2.addShot(s2);
		p2.addShot(s3);
		p2.addInfraction(i2);
		
		
		t1.setPoints(20);
		t1.addPlayer(p1);
		t1.addPlayer(p2);
		
		assertEquals(t1.getPoints(),20);
		assertEquals(t1.getPlayers().size(),2);
		
		t1.addWin();
		assertEquals(t1.getPoints(),23);
		
		t1.addDraw();
		assertEquals(t1.getPoints(),24);
		
		assertEquals(t1.goalsScored(),3);
		assertEquals(t1.yellowCards(),1);
		assertEquals(t1.redCards(),1);
		assertEquals(t1.penaltyKicks(),2);
	}
	

}
