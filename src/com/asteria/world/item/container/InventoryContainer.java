package com.asteria.world.item.container;

import com.asteria.world.entity.player.Player;
import com.asteria.world.item.Item;
import com.asteria.world.item.ItemContainer;

/**
 * An {@link ItemContainer} implementation that will manage items in a player's
 * inventory.
 * 
 * @author lare96
 */
public class InventoryContainer extends ItemContainer {

    /** The player's inventory being managed. */
    private final Player player;

    /**
     * Create a new {@link InventoryContainer}.
     * 
     * @param player
     *            the player's inventory being managed.
     */
    public InventoryContainer(Player player) {
        super(Policy.NORMAL, 28);
        this.player = player;
    }

    /** Refreshes the contents of this container to the inventory. */
    public void refresh() {
        refresh(3214, player);
    }

    @Override
    public boolean add(Item item, int slot) {
        if (item == null) {
            return false;
        }

        if (!contains(item.getId()) && !item.getDefinition().isStackable()) {
            if (getFreeSlot() == -1) {

                player.getPacketBuilder().sendMessage(
                    "You don't have enough space in your inventory!");
                return false;
            }
        }

        boolean modified = super.add(item, slot);
        refresh();
        return modified;
    }

    @Override
    public boolean add(Item item) {
        return add(item, -1);
    }

    @Override
    public boolean remove(Item item, int slot) {
        boolean modified = super.remove(item, slot);
        refresh();
        return modified;
    }

    @Override
    public boolean remove(Item item) {
        return remove(item, -1);
    }
}