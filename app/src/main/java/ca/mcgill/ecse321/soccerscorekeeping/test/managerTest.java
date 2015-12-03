package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;

public class managerTest {

	@Test
	public void test() 
	{
		Manager m = Manager.getInstance();
		
		//Add teams
		Team t1 = new Team("Brazil",0);
		Team t2 = new Team("England",0);
		
		//Add Players
		Player b1 = new Player("Ronaldo",t1);
		Player b2 = new Player("Kaka",t1);
		Player e1 = new Player("Rooney",t2);
		Player e2 = new Player("Crouch",t2);
		
		t1.addPlayer(b1);
		t1.addPlayer(b2);
		t2.addPlayer(e1);
		t2.addPlayer(e2);
		
		//Save to manager
		m.addTeam(t1);
		m.addTeam(t2);
		
		//Check
		assertEquals(m.getTeams().size(),2);
		assertEquals(m.getTeam(0).getPlayers().size()+m.getTeam(1).getPlayers().size(),4);
		
		//Create Match
		Match m1 = new Match("Brazil vs England",0,0,t1,t2);
		m1.incrementGoals1();
		m1.incrementGoals1();
		
		//Add match to manager
		m.addMatche(m1);
		
		//Check
		assertEquals(m.getMatches().size(),1);
		assertEquals(m.getMatche(0).getTeam(0).getName(),"Brazil");
		assertEquals(m.getMatche(0).getTeam(1).getName(),"England");
		assertEquals(m.getMatche(0).getGoals1(),2);
		assertEquals(m.getMatche(0).getGoals2(),0);
		
		//Delete
		m.delete();
		assertEquals(m.getTeams().size(),0);
		assertEquals(m.getMatches().size(),0);
		
	}

}
