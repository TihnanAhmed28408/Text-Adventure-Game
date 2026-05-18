package p5adventure;

import student.adventure.*;

/**
 * Command words: inventory, i
 * Lists all items the adventurer is currently carrying.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class InventoryCommand extends Command {

    /**
     * @param player the current player
     * @return false always
     */
    @Override
    public boolean execute(Player player) {
        Adventurer adventurer = (Adventurer) player;
        adventurer.printInventory();
        return false;
    }
}
