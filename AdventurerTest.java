package p5adventure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the Adventurer class.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class AdventurerTest {

    private Adventurer adventurer;
    private Item lightItem;
    private Item heavyItem;
    private Item fixedItem;

    /** initializes test objects */
    @Before
    public void setUp() {
        adventurer = new Adventurer();
        lightItem  = new Item("badge", "An Arsenal badge.", 1);
        heavyItem  = new Item("boulder", "A giant boulder.", 35);
        fixedItem  = new Item("pitch", "The pitch.", 10, false);
    }

    /** tests inventory is empty at start */
    @Test
    public void testInitialInventoryEmpty() {
        assertTrue("Inventory should be empty at start",
            adventurer.getInventory().isEmpty());
    }

    /** tests total weight is zero at start */
    @Test
    public void testInitialWeightZero() {
        assertEquals(0, adventurer.getTotalWeight());
    }

    /** tests previous room is null at start */
    @Test
    public void testInitialPreviousRoomNull() {
        assertNull("Previous room should be null at start",
            adventurer.getPreviousRoom());
    }

    /** tests addItem returns true and updates inventory */
    @Test
    public void testAddItemSuccess() {
        boolean result = adventurer.addItem(lightItem);
        assertTrue("addItem should return true on success", result);
        assertEquals(1, adventurer.getInventory().size());
        assertEquals(1, adventurer.getTotalWeight());
    }

    /** tests addItem returns false when item would exceed max weight */
    @Test
    public void testAddItemOverWeight() {
        boolean result = adventurer.addItem(heavyItem);
        assertFalse("addItem should return false when over max weight", result);
        assertTrue("Inventory should still be empty",
            adventurer.getInventory().isEmpty());
        assertEquals(0, adventurer.getTotalWeight());
    }

    /** tests addItem returns false for non-removable items */
    @Test
    public void testAddItemNotRemovable() {
        boolean result = adventurer.addItem(fixedItem);
        assertFalse("addItem should return false for non-removable item",
            result);
        assertTrue("Inventory should still be empty",
            adventurer.getInventory().isEmpty());
    }

    /** tests addItem accumulates weight correctly */
    @Test
    public void testAddItemAccumulatesWeight() {
        Item item1 = new Item("scarf", "Scarf.", 5);
        Item item2 = new Item("badge", "Badge.", 3);
        adventurer.addItem(item1);
        adventurer.addItem(item2);
        assertEquals(8, adventurer.getTotalWeight());
    }

    /** tests removeItem returns the item and clears it from inventory */
    @Test
    public void testRemoveItemSuccess() {
        adventurer.addItem(lightItem);
        Item removed = adventurer.removeItem("badge");
        assertNotNull(removed);
        assertEquals("badge", removed.getName());
        assertTrue(adventurer.getInventory().isEmpty());
        assertEquals(0, adventurer.getTotalWeight());
    }

    /** tests removeItem returns null when item is not found */
    @Test
    public void testRemoveItemNotFound() {
        Item removed = adventurer.removeItem("nonexistent");
        assertNull("Should return null when item not in inventory", removed);
    }

    /** tests removeItem updates weight correctly */
    @Test
    public void testRemoveItemUpdatesWeight() {
        Item item1 = new Item("scarf", "Scarf.", 5);
        Item item2 = new Item("badge", "Badge.", 3);
        adventurer.addItem(item1);
        adventurer.addItem(item2);
        adventurer.removeItem("scarf");
        assertEquals(3, adventurer.getTotalWeight());
    }

    /** tests setter and getter for previous room */
    @Test
    public void testSetAndGetPreviousRoom() {
        Location loc = new Location("test room");
        adventurer.setPreviousRoom(loc);
        assertEquals(loc, adventurer.getPreviousRoom());
    }

    /** tests max weight returns 30 */
    @Test
    public void testMaxWeight() {
        assertEquals(30, adventurer.getMaxWeight());
    }

    /** tests addItem returns false for null item */
    @Test
    public void testAddItemNull() {
        assertFalse(adventurer.addItem(null));
    }

    /** tests removeItem returns null for null name */
    @Test
    public void testRemoveItemNullName() {
        assertNull(adventurer.removeItem(null));
    }

    /** tests printInventory when empty does not crash */
    @Test
    public void testPrintInventoryEmpty() {
        adventurer.printInventory();
        assertTrue(adventurer.getInventory().isEmpty());
    }

    /** tests printInventory when carrying an item does not crash */
    @Test
    public void testPrintInventoryWithItem() {
        adventurer.addItem(lightItem);
        adventurer.printInventory();
        assertEquals(1, adventurer.getInventory().size());
    }

    /** tests isGameOver returns false when not at UEFA HQ */
    @Test
    public void testIsGameOverNotAtHQ() {
        Location loc = new Location("at Wanda Metropolitano");
        adventurer.setCurrentRoom(loc);
        assertFalse(adventurer.isGameOver());
    }

    /** tests isGameOver returns false when at HQ but not enough items */
    @Test
    public void testIsGameOverAtHQNotEnoughItems() {
        Location hq = new Location(
            "at UEFA Headquarters in Nyon, Switzerland");
        adventurer.setCurrentRoom(hq);
        adventurer.addItem(new Item("scarf", "scarf", 1));
        assertFalse(adventurer.isGameOver());
    }

    /** tests isGameOver returns true when at HQ with 10 items */
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
}
