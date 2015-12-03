package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;

public class shotTest 
{

	@Test
	public void test() 
	{
		Shot s1 = new Shot(true);
		Shot s2 = new Shot(false);
		
		assertEquals(s1.getIsGoal(),true);
		assertEquals(s2.getIsGoal(),false);
	}

}
