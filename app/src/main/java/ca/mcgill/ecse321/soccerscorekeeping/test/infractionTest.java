package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;

public class infractionTest {

	@Test
	public void test() 
	{
		Infraction i = new Infraction("YELLOW",true);
		Infraction j = new Infraction("YELLOW",false);
		Infraction k = new Infraction("RED",true);
		Infraction l = new Infraction("RED",false);
		
		assertEquals(i.getType(),"YELLOW");
		assertEquals(j.getType(),"YELLOW");
		assertEquals(k.getType(),"RED");
		assertEquals(k.getType(),"RED");
		assertEquals(i.getIsPenaltyKick(),true);
		assertEquals(j.getIsPenaltyKick(),false);
		assertEquals(k.getIsPenaltyKick(),true);
		assertEquals(l.getIsPenaltyKick(),false);
	}

}
