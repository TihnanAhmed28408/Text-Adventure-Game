package p5adventure;

import student.adventure.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Additional coverage tests targeting uncovered branches from WebCAT.
 * Covers: Adventurer, DirectionCommand, DropCommand, ExamineCommand,
 * InventoryCommand, ScoreCommand, TakeCommand.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class CoverageTest {

    private Adventurer adventurer;
    private Location room;
    private Item scarf;

    /** sets up common test objects */
    @Before
    public void setUp() {
        adventurer = new Adventurer();
        room = new Location("at Wanda Metropolitano");
        scarf = new Item("scarf", "A red and white scarf.", 2);
        room.addItem(scarf);
        adventurer.setCurrentRoom(room);
    }

    // ── Adventurer: uncovered branches ───────────────────────────────────────

    /** tests printInventory lists items when carrying items */
    @Test
    public void testInventoryStringWithItems() {
        adventurer.addItem(scarf);
        adventurer.printInventory();
        assertEquals(1, adventurer.getInventory().size());
        assertEquals(2, adventurer.getTotalWeight());
    }

    /** tests isGameOver false when no room set */
    @Test
    public void testIsGameOverNullRoom() {
        Adventurer fresh = new Adventurer();
        assertFalse(fresh.isGameOver());
    }

    /** tests isGameOver false when not at UEFA HQ */
    @Test
    public void testIsGameOverWrongRoom() {
        adventurer.setCurrentRoom(room);
        assertFalse(adventurer.isGameOver());
    }

    /** tests isGameOver false at UEFA HQ with fewer than 10 items */
    @Test
    public void testIsGameOverAtHQTooFewItems() {
        Location hq = new Location(
            "at UEFA Headquarters in Nyon, Switzerland");
        adventurer.setCurrentRoom(hq);
        adventurer.addItem(new Item("scarf", "scarf", 1));
        assertFalse(adventurer.isGameOver());
    }

    /** tests isGameOver true at UEFA HQ with exactly 10 items */
    @Test
    public void testIsGameOverWinCondition() {
        Location hq = new Location(
            "at UEFA Headquarters in Nyon, Switzerland");
        adventurer.setCurrentRoom(hq);
        for (int i = 0; i < 10; i++) {
            adventurer.addItem(new Item("item" + i, "desc", 1));
        }
        assertTrue(adventurer.isGameOver());
    }

    /** tests addItem returns false for null item */
    @Test
    public void testAddItemNullReturnsFalse() {
        assertFalse(adventurer.addItem(null));
        assertTrue(adventurer.getInventory().isEmpty());
    }

    /** tests removeItem returns null for null itemName */
    @Test
    public void testRemoveItemNullName() {
        assertNull(adventurer.removeItem(null));
    }

    // ── DirectionCommand: uncovered branches ──────────────────────────────────

    /** tests direction command moves player and updates previous room */
    @Test
    public void testDirectionCommandSouth() {
        Location south = new Location("south room");
        room.setExit("south", south);
        DirectionCommand cmd = new DirectionCommand("south");
        cmd.execute(adventurer);
        assertEquals(south, adventurer.getCurrentRoom());
        assertEquals(room, adventurer.getPreviousRoom());
    }

    /** tests abbreviation s maps to south */
    @Test
    public void testDirectionCommandAbbreviationS() {
        Location south = new Location("south room");
        room.setExit("south", south);
        DirectionCommand cmd = new DirectionCommand("s");
        cmd.execute(adventurer);
        assertEquals(south, adventurer.getCurrentRoom());
    }

    /** tests abbreviation e maps to east */
    @Test
    public void testDirectionCommandAbbreviationE() {
        Location east = new Location("east room");
        room.setExit("east", east);
        DirectionCommand cmd = new DirectionCommand("e");
        cmd.execute(adventurer);
        assertEquals(east, adventurer.getCurrentRoom());
    }

    /** tests abbreviation w maps to west */
    @Test
    public void testDirectionCommandAbbreviationW() {
        Location west = new Location("west room");
        room.setExit("west", west);
        DirectionCommand cmd = new DirectionCommand("w");
        cmd.execute(adventurer);
        assertEquals(west, adventurer.getCurrentRoom());
    }

    /** tests abbreviation u maps to up */
    @Test
    public void testDirectionCommandAbbreviationU() {
        Location up = new Location("up room");
        room.setExit("up", up);
        DirectionCommand cmd = new DirectionCommand("u");
        cmd.execute(adventurer);
        assertEquals(up, adventurer.getCurrentRoom());
    }

    /** tests abbreviation d maps to down */
    @Test
    public void testDirectionCommandAbbreviationD() {
        Location down = new Location("down room");
        room.setExit("down", down);
        DirectionCommand cmd = new DirectionCommand("d");
        cmd.execute(adventurer);
        assertEquals(down, adventurer.getCurrentRoom());
    }

    /** tests direction command prints message when no exit */
    @Test
    public void testDirectionCommandNoExitReturnsFalse() {
        DirectionCommand cmd = new DirectionCommand("east");
        assertFalse(cmd.execute(adventurer));
        assertEquals(room, adventurer.getCurrentRoom());
    }

    // ── DropCommand: uncovered branches ───────────────────────────────────────

    /** tests drop with no second word returns false */
    @Test
    public void testDropCommandNoSecondWord() {
        DropCommand cmd = new DropCommand();
        assertFalse(cmd.execute(adventurer));
    }

    /** tests drop puts item back in a Location correctly */
    @Test
    public void testDropCommandIntoLocation() {
        adventurer.addItem(scarf);
        room.getItems().clear();
        DropCommand cmd = new DropCommand();
        cmd.setSecondWord("scarf");
        assertFalse(cmd.execute(adventurer));
        assertEquals(1, room.getItems().size());
        assertTrue(adventurer.getInventory().isEmpty());
    }

    // ── ExamineCommand: uncovered branches ────────────────────────────────────

    /** tests examine when item is not found anywhere */
    @Test
    public void testExamineCommandItemNotFound() {
        ExamineCommand cmd = new ExamineCommand();
        cmd.setSecondWord("trophy");
        assertFalse(cmd.execute(adventurer));
    }

    /** tests examine finds item in inventory when not in room */
    @Test
    public void testExamineCommandFindsInInventory() {
        adventurer.addItem(scarf);
        room.getItems().clear();
        ExamineCommand cmd = new ExamineCommand();
        cmd.setSecondWord("scarf");
        assertFalse(cmd.execute(adventurer));
    }

    /** tests examine with no second word */
    @Test
    public void testExamineCommandNullSecondWord() {
        ExamineCommand cmd = new ExamineCommand();
        assertFalse(cmd.execute(adventurer));
    }

    // ── InventoryCommand: uncovered branches ──────────────────────────────────

    /** tests inventory command prints nothing when empty */
    @Test
    public void testInventoryCommandEmpty() {
        InventoryCommand cmd = new InventoryCommand();
        assertFalse(cmd.execute(adventurer));
        assertTrue(adventurer.getInventory().isEmpty());
    }

    /** tests inventory command prints items when carrying something */
    @Test
    public void testInventoryCommandNonEmpty() {
        adventurer.addItem(scarf);
        InventoryCommand cmd = new InventoryCommand();
        assertFalse(cmd.execute(adventurer));
        assertEquals(1, adventurer.getInventory().size());
    }

    // ── ScoreCommand: uncovered branches ─────────────────────────────────────

    /** tests score command when inventory is empty (collected == 0) */
    @Test
    public void testScoreCommandZeroItems() {
        ScoreCommand cmd = new ScoreCommand();
        assertFalse(cmd.execute(adventurer));
        assertEquals(0, adventurer.getInventory().size());
    }

    /** tests score command when partially filled (0 < collected < 10) */
    @Test
    public void testScoreCommandPartialItems() {
        for (int i = 0; i < 5; i++) {
            adventurer.addItem(new Item("item" + i, "desc", 1));
        }
        ScoreCommand cmd = new ScoreCommand();
        assertFalse(cmd.execute(adventurer));
        assertEquals(5, adventurer.getInventory().size());
    }

    /** tests score command when all 10 items collected */
    @Test
    public void testScoreCommandFullInventory() {
        for (int i = 0; i < 10; i++) {
            adventurer.addItem(new Item("item" + i, "desc", 1));
        }
        ScoreCommand cmd = new ScoreCommand();
        assertFalse(cmd.execute(adventurer));
        assertEquals(10, adventurer.getInventory().size());
    }

    // ── TakeCommand: uncovered branches ──────────────────────────────────────

    /** tests take command when item is fixed in place (not removable) */
    @Test
    public void testTakeCommandFixedItemInPlace() {
        Item fixed = new Item("pitch", "The pitch.", 50, false);
        room.addItem(fixed);
        TakeCommand cmd = new TakeCommand();
        cmd.setSecondWord("pitch");
        assertFalse(cmd.execute(adventurer));
        assertTrue(adventurer.getInventory().isEmpty());
    }

    /** tests take command when item is not in the room at all */
    @Test
    public void testTakeCommandItemMissing() {
        TakeCommand cmd = new TakeCommand();
        cmd.setSecondWord("trophy");
        assertFalse(cmd.execute(adventurer));
        assertTrue(adventurer.getInventory().isEmpty());
    }

    /** tests take command with no second word */
    @Test
    public void testTakeCommandNullSecondWord() {
        TakeCommand cmd = new TakeCommand();
        assertFalse(cmd.execute(adventurer));
    }

    /** tests take command rejects item if it would exceed weight limit */
    @Test
    public void testTakeCommandOverWeightPutsItemBack() {
        Item heavy = new Item("boulder", "A heavy rock.", 29);
        room.addItem(heavy);
        adventurer.addItem(new Item("lead", "Lead bar.", 5));
        TakeCommand cmd = new TakeCommand();
        cmd.setSecondWord("boulder");
        assertFalse(cmd.execute(adventurer));
        // boulder should be back in the room
        boolean inRoom = room.getItems().stream()
            .anyMatch(i -> i.getName().equals("boulder"));
        assertTrue(inRoom);
    }

    // ── Location: uncovered branches ──────────────────────────────────────────

    /** tests removeItem returns null for null name */
    @Test
    public void testLocationRemoveNullName() {
        assertNull(room.removeItem(null));
    }

    /** tests removeItem returns null when item not removable */
    @Test
    public void testLocationRemoveNotRemovable() {
        Item fixed = new Item("pitch", "The pitch.", 50, false);
        room.addItem(fixed);
        assertNull(room.removeItem("pitch"));
        assertEquals(2, room.getItems().size());
    }

    // ── ClaimCommand: uncovered branches ─────────────────────────────────────

    /** tests claim at UEFA HQ with exactly 10 items returns true */
    @Test
    public void testClaimCommandWin() {
        Location hq = new Location(
            "at UEFA Headquarters in Nyon, Switzerland");
        adventurer.setCurrentRoom(hq);
        for (int i = 0; i < 10; i++) {
            adventurer.addItem(new Item("item" + i, "desc", 1));
        }
        ClaimCommand cmd = new ClaimCommand();
        assertTrue(cmd.execute(adventurer));
    }

    /** tests claim outside UEFA HQ returns false */
    @Test
    public void testClaimCommandWrongLocation() {
        ClaimCommand cmd = new ClaimCommand();
        assertFalse(cmd.execute(adventurer));
    }

    /** tests claim at UEFA HQ with fewer than 10 items returns false */
    @Test
    public void testClaimCommandNotEnoughItems() {
        Location hq = new Location(
            "at UEFA Headquarters in Nyon, Switzerland");
        adventurer.setCurrentRoom(hq);
        adventurer.addItem(new Item("scarf", "scarf", 1));
        ClaimCommand cmd = new ClaimCommand();
        assertFalse(cmd.execute(adventurer));
    }
}
