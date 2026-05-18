package p5adventure;

import student.adventure.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a location in the European Football Stadium Hunt.
 * Each Location can hold any number of Item objects.
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class Location extends Room {

    private List<Item> items;

    /**
     * Creates a Location with the given description and empty item list.
     *
     * @param description a short description of the location
     */
    public Location(String description) {
        super(description);
        this.items = new ArrayList<>();
    }

    /**
     * Returns the list of items currently in this location.
     *
     * @return list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Adds an item to this location.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes and returns the item with the given name if removable.
     * Returns null if not found or not removable.
     *
     * @param name the name of the item to remove
     * @return the removed Item, or null
     */
    public Item removeItem(String name) {
        if (name == null) {
            return null;
        }
        for (Item item : items) {
            if (item != null && name.equalsIgnoreCase(item.getName())) {
                if (item.isRemovable()) {
                    items.remove(item);
                    return item;
                }
                return null;
            }
        }
        return null;
    }

    /**
     * Returns the long description including exits and all items present.
     *
     * @return the full description string
     */
    @Override
    public String getLongDescription() {
        StringBuilder sb = new StringBuilder(super.getLongDescription());
        if (items.isEmpty()) {
            sb.append("\nItems here: none");
        } else {
            sb.append("\nItems here:");
            for (Item item : items) {
                sb.append("\n  - ").append(item.toString());
            }
        }
        return sb.toString();
    }
}
