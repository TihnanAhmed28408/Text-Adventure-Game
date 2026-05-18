package p5adventure;

import student.adventure.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the AdventureGame class.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class AdventurerGameTest {

    private AdventureGame game;

    /** initializes objects */
    @Before
    public void setUp() {
        game = new AdventureGame();
    }

    /** tests welcome message contains game title */
    @Test
    public void testWelcomeMessageContainsTitle() {
        assertTrue(game.welcomeMessage()
            .contains("Stadium Hunt"));
    }

    /** tests welcome message mentions the claim command */
    @Test
    public void testWelcomeMessageContainsClaim() {
        assertTrue(game.welcomeMessage().contains("claim"));
    }

    /** tests player starts at Wanda Metropolitano */
    @Test
    public void testPlayerStartsAtWanda() {
        assertTrue(game.player().getCurrentRoom()
            .getShortDescription().contains("Wanda Metropolitano"));
    }

    /** tests adventurer inventory is empty at game start */
    @Test
    public void testPlayerInventoryEmptyAtStart() {
        Adventurer adv = (Adventurer) game.player();
        assertTrue("Adventurer inventory must be empty at game start",
            adv.getInventory().isEmpty());
    }

    /** tests adventurer weight is zero at game start */
    @Test
    public void testPlayerWeightZeroAtStart() {
        Adventurer adv = (Adventurer) game.player();
        assertEquals(0, adv.getTotalWeight());
    }

    /** tests Wanda has a south exit to Allianz Arena */
    @Test
    public void testWandaConnectedSouth() {
        Room wanda = game.player().getCurrentRoom();
        Room allianz = wanda.getExit("south");
        assertNotNull("Wanda should have a south exit", allianz);
        assertTrue(allianz.getShortDescription()
            .contains("Allianz Arena"));
    }

    /** tests Allianz Arena connects back north to Wanda */
    @Test
    public void testAllianzConnectedBackNorth() {
        Room wanda   = game.player().getCurrentRoom();
        Room allianz = wanda.getExit("south");
        Room backToWanda = allianz.getExit("north");
        assertEquals(wanda, backToWanda);
    }

    /** tests UEFA HQ is reachable from Wanda */
    @Test
    public void testAllRoomsReachableFromWanda() {
        Room wanda   = game.player().getCurrentRoom();
        Room allianz = wanda.getExit("south");
        Room sanSiro = allianz.getExit("south");
        Room uefaHQ  = sanSiro.getExit("east");
        assertNotNull("San Siro should be reachable", sanSiro);
        assertNotNull("UEFA HQ should be reachable", uefaHQ);
        assertTrue(uefaHQ.getShortDescription()
            .contains("UEFA Headquarters"));
    }
}
