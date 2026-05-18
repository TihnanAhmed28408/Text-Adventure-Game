package p5adventure;

import student.adventure.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player in the European Football Stadium Hunt.
 * The Adventurer travels between stadiums collecting memorabilia.
 * Maximum carry weight is 30.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class Adventurer extends Player {

    private static final int MAX_WEIGHT = 30;

    private int totalWeight;
    private List<Item> inventory;
    private Room previousRoom;

    /**
     * New adventurer player created, initialized at start of game
     * with empty inventory and 0 weight.
     */
    public Adventurer() {
        super();
        this.totalWeight = 0;
        this.inventory = new ArrayList<>();
        this.previousRoom = null;
    }

    /** @return current weight of the total objects carried */
    public int getTotalWeight() { return totalWeight; }

    /** @param totalWeight is the new total weight */
    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    /** @return the list of items that is being carried */
    public List<Item> getInventory() { return inventory; }

    /** @param inventory the new inventory list */
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * @return the previous room visited, null if no previous room
     */
    public Room getPreviousRoom() { return previousRoom; }

    /** @param previousRoom set value as the previous room visited */
    public void setPreviousRoom(Room previousRoom) {
        this.previousRoom = previousRoom;
    }

    /** @return the maximum weight this adventurer can carry (30) */
    public int getMaxWeight() { return MAX_WEIGHT; }

    /**
     * Attempts to add item to adventurer's inventory.
     * Item must be non-null, removable, and within 30kg weight limit.
     *
     * @param item the item to add
     * @return true if added successfully, false otherwise
     */
    public boolean addItem(Item item) {
        if (item == null) {
            System.out.println("There is no item to pick up.");
            return false;
        }
        if (!item.isRemovable()) {
            System.out.println("You can't pick up the " + item.getName());
            return false;
        }
        if (totalWeight + item.getWeight() > MAX_WEIGHT) {
            System.out.println("You can't carry the " + item.getName()
                + " as it would exceed the 30kg limit.");
            return false;
        }
        inventory.add(item);
        totalWeight += item.getWeight();
        return true;
    }

    /**
     * Removes and returns the item with a given name from the inventory.
     * If item is not found, returns null.
     *
     * @param itemName the name of the item to remove
     * @return the removed Item, or null
     */
    public Item removeItem(String itemName) {
        if (itemName == null) {
            return null;
        }
        for (Item item : inventory) {
            if (itemName.equalsIgnoreCase(item.getName())) {
                inventory.remove(item);
                totalWeight -= item.getWeight();
                return item;
            }
        }
        return null;
    }

    /**
     * Checks if adventurer has won the game.
     * Wins when: at UEFA Headquarters with all 10 stadium collectibles.
     *
     * @return true if the win condition is met, false otherwise
     */
    public boolean isGameOver() {
        Room currentRoom = getCurrentRoom();
        if (currentRoom == null) {
            return false;
        }
        if (!currentRoom.getShortDescription().contains("UEFA Headquarters")) {
            return false;
        }
        return inventory.size() >= 10;
    }

    /**
     * Prints all items the adventurer is currently carrying.
     * If inventory is empty, prints a message saying so.
     */
    public void printInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are carrying nothing.");
            return;
        }
        System.out.println("You are carrying ("
            + totalWeight + "/" + MAX_WEIGHT + " kg):");
        for (Item item : inventory) {
            System.out.println("  - " + item.toString());
        }
    }
}
