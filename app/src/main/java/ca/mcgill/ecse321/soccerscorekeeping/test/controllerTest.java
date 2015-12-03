
package ca.mcgill.ecse321.soccerscorekeeping.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse321.soccerscorekeeping.admin.managerTools;
import ca.mcgill.ecse321.soccerscorekeeping.controller.Controller;
import ca.mcgill.ecse321.soccerscorekeeping.model.Infraction;
import ca.mcgill.ecse321.soccerscorekeeping.model.Manager;
import ca.mcgill.ecse321.soccerscorekeeping.model.Match;
import ca.mcgill.ecse321.soccerscorekeeping.model.Player;
import ca.mcgill.ecse321.soccerscorekeeping.model.Shot;
import ca.mcgill.ecse321.soccerscorekeeping.model.Team;
import ca.mcgill.ecse321.soccerscorekeeping.persistence.XStreamPersistence;

public class controllerTest
{
    @Before
    public void setUp() throws Exception
    {
        Manager m = Manager.getInstance();
        XStreamPersistence.setFilename("src\\ca\\mcgill\\ecse321\\soccerscorekeeping\\test\\testController.xml");
        XStreamPersistence.setAlias("Team",Team.class);
        XStreamPersistence.setAlias("Player",Player.class);
        XStreamPersistence.setAlias("Shot",Shot.class);
        XStreamPersistence.setAlias("Infraction",Infraction.class);
        XStreamPersistence.setAlias("Manager",Manager.class);
        XStreamPersistence.setAlias("Match",Match.class);

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

        //Check manager is empty
        assertEquals(0,m.getTeams().size());
        assertEquals(0,m.getMatches().size());

        //Preprocessing before test is run
        managerTools mt = new managerTools();

        mt.createTeam("Pakistan");
        mt.createTeam("England");

        mt.createPlayer("Younis", m.getTeam(0));
        mt.createPlayer("Imran", m.getTeam(0));

        mt.createPlayer("Finn", m.getTeam(1));
        mt.createPlayer("Cook", m.getTeam(1));

        //Initialize instance of controller
        Controller c = new Controller();

        //Create a match
        c.createMatch("Pakistan","England");

        Match m1 = m.getMatche(0);

        assertEquals(m.getMatche(0).getTeam(0).getName(),"Pakistan");
        assertEquals(m.getMatche(0).getTeam(1).getName(),"England");

        //Create Dummy shots
        c.createShot("Younis", m1, true);
        c.createShot("Imran", m1, false);

        //Check if shots were enetered correctly
        assertEquals(m.getTeam(0).getPlayer(0).getShot(0).getIsGoal(),true);
        assertEquals(m.getTeam(0).getPlayer(1).getShot(0).getIsGoal(),false);

        //Create Dummy Shots
        c.createShot("Finn", m1, false);
        c.createShot("Cook", m1, false);

        //Check is hsots were entered correctly
        assertEquals(m.getTeam(1).getPlayer(0).getShot(0).getIsGoal(),false);
        assertEquals(m.getTeam(1).getPlayer(1).getShot(0).getIsGoal(),false);

        //Create Dummy Infractions
        c.createInfraction("Younis",m1,"RED",true);
        c.createInfraction("Cook",m1,"YELLOW",true);
        c.createInfraction("Finn",m1,"RED",false);
        c.createInfraction("Cook",m1,"YELLOW",false);

        //Check if infractions were entered correctly
        assertEquals(m.getTeam(0).getPlayer(0).getInfraction(0).getType(),"RED");
        assertEquals(m.getTeam(0).getPlayer(0).getInfraction(0).getIsPenaltyKick(),true);

        assertEquals(m.getTeam(1).getPlayer(1).getInfraction(0).getType(),"YELLOW");
        assertEquals(m.getTeam(1).getPlayer(1).getInfraction(0).getIsPenaltyKick(),true);

        assertEquals(m.getTeam(1).getPlayer(1).getInfraction(1).getType(),"YELLOW");
        assertEquals(m.getTeam(1).getPlayer(1).getInfraction(1).getIsPenaltyKick(),false);

        assertEquals(m.getTeam(1).getPlayer(0).getInfraction(0).getType(),"RED");
        assertEquals(m.getTeam(1).getPlayer(0).getInfraction(0).getIsPenaltyKick(),false);

        //Check if points were addded to manager
        c.chooseWinner(m1);
        assertEquals(m.getTeam(0).getPoints(),3);

    }

}
