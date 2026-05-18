package p5adventure;

import student.adventure.*;

/**
 * Command word: take
 * Allows the adventurer to pick up an item from the current location.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class TakeCommand extends Command {

    /**
     * Picks up the item named by the second word from the current room.
     * Removes it from the room and adds it to the adventurer's inventory.
     *
     * @param player the current player
     * @return false always
     */
    @Override
    public boolean execute(Player player) {
        if (!(player instanceof Adventurer)) {
            System.out.println("Error: unknown player type.");
            return false;
        }
        Adventurer adventurer = (Adventurer) player;
        String itemName = getSecondWord();

        if (itemName == null) {
            System.out.println("Take what? (e.g. 'take scarf')");
            return false;
        }

        Room currentRoom = adventurer.getCurrentRoom();
        if (!(currentRoom instanceof Location)) {
            System.out.println("You can't pick up items here.");
            return false;
        }
        Location current = (Location) currentRoom;
        Item item = current.removeItem(itemName);

        if (item == null) {
            boolean exists = current.getItems().stream()
                .anyMatch(i -> i.getName() != null
                    && i.getName().equalsIgnoreCase(itemName));
            if (exists) {
                System.out.println(
                    "You can't take the " + itemName
                    + " - it's fixed in place.");
            } else {
                System.out.println(
                    "There is no '" + itemName + "' here.");
            }
            return false;
        }

        boolean added = adventurer.addItem(item);
        if (added) {
            System.out.println("You pick up the " + item.getName()
                + ". " + item.getDescription());
        } else {
            current.addItem(item);
        }
        return false;
    }
}
