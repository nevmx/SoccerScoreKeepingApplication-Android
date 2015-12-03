package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;
import ca.mcgill.ecse321.soccerscorekeeping.persistence.XStreamPersistence;

public class persistenceTest 
{

	@Before
	public void setUp() throws Exception 
	{
		Manager m = Manager.getInstance();
		
		//Create Team
		Team t1 = new Team("Pakistan");
		
		//Create Players
		Player p1 = new Player("Younis",t1);
		Player p2 = new Player("Imran",t1);
		
		//Add players to team
		t1.addPlayer(p1);
		t1.addPlayer(p2);
		
		//Add Shots
		p1.addShot(new Shot(true));
		p2.addShot(new Shot(false));
		
		//Add Infractions
		p1.addInfraction(new Infraction("Yellow",true));
		p2.addInfraction(new Infraction("Red",false));
		
		//Manage stuff
		m.addTeam(t1);
	}

	@After
	public void tearDown() throws Exception
	{
		Manager m = Manager.getInstance();
		m.delete();
	}

	@Test
	public void test() 
	{
		Manager m = Manager.getInstance();
		XStreamPersistence.setFilename("src\\ca\\mcgill\\ecse321\\soccerscorekeeping\\persistence\\test.xml");
		XStreamPersistence.setAlias("Team",Team.class);
		XStreamPersistence.setAlias("Player",Player.class);
		XStreamPersistence.setAlias("Shot",Shot.class);
		XStreamPersistence.setAlias("Infraction",Infraction.class);
		XStreamPersistence.setAlias("Manager",Manager.class);
		if(!XStreamPersistence.saveToXMLwithXStream(m))
		{
			fail("Could not save to file");
		}
		
		m.delete();
		assertEquals(0,m.getTeams().size());
		
		m=(Manager)XStreamPersistence.loadFromXMLwithXStream();
		if(m==null)
		{
			fail("Could not load file");
		}
		
		assertEquals(1,m.getTeams().size());
		assertEquals("Pakistan",m.getTeam(0).getName());
		assertEquals(2,m.getTeam(0).getPlayers().size());
		assertEquals("Younis",m.getTeam(0).getPlayer(0).getName());
		assertEquals("Imran",m.getTeam(0).getPlayer(1).getName());
		assertEquals(1,m.getTeam(0).getPlayer(0).getShots().size());
		assertEquals(1,m.getTeam(0).getPlayer(1).getShots().size());
		assertEquals(1,m.getTeam(0).getPlayer(0).getInfractions().size());
		assertEquals(1,m.getTeam(0).getPlayer(1).getInfractions().size());
		assertEquals("Yellow",m.getTeam(0).getPlayer(0).getInfraction(0).getType());
		assertEquals("Red",m.getTeam(0).getPlayer(1).getInfraction(0).getType());
	}

}