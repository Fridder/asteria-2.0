package com.asteria.world.entity.player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.asteria.util.Utility;
import com.asteria.world.entity.Animation;
import com.asteria.world.entity.EntityDeath;
import com.asteria.world.entity.UpdateFlags.Flag;
import com.asteria.world.entity.combat.prayer.CombatPrayer;
import com.asteria.world.entity.player.content.WeaponInterfaces;
import com.asteria.world.entity.player.minigame.Minigame;
import com.asteria.world.entity.player.minigame.Minigames;
import com.asteria.world.entity.player.skill.Skills;
import com.asteria.world.item.Item;
import com.asteria.world.item.ground.GroundItem;
import com.asteria.world.item.ground.GroundItem.StaticGroundItem;
import com.asteria.world.item.ground.GroundItemManager;
import com.asteria.world.map.Position;

/**
 * Handles the death process for all {@link Player}s.
 * 
 * @author lare96
 */
public class PlayerDeath extends EntityDeath<Player> {

    /**
     * Messages chosen a random to be sent to a player that has killed another
     * player. <code>-victim-</code> is replaced with the player's name that was
     * killed. <code>-killer-</code> is replaced with the killer's name.
     */
    public static final String[] DEATH_MESSAGES = {
            "You have just killed -victim-!",
            "You have completely slaughtered -victim-!",
            "Wow -killer-, I bet -victim- will think twice before messing with you again!" };

    /**
     * The items that will be kept on death regardless of if the player is
     * skulled or not.
     */
    public static final int[] KEEP_ON_DEATH = { 6570 };

    /**
     * Create a new {@link PlayerDeath}.
     * 
     * @param player
     *            the player that needs to be taken through the death stages.
     */
    public PlayerDeath(Player player) {
        super(player);
    }

    @Override
    public void preDeath(Player entity) {

        // Start death animation, fire events, and reset stuff.
        entity.animation(new Animation(0x900));
        Skills.fireSkillEvents(entity);
        entity.getTradeSession().reset(false);
    }

    @Override
    public void death(Player entity) {

        // Get the killer and minigame instances.
        Optional<Player> killer = entity.getCombatBuilder().getKiller(true);
        Optional<Minigame> optional = Minigames.get(entity);

        // Send the killer a message.
        killer.ifPresent(k -> k.getPacketBuilder().sendMessage(Utility.randomElement(DEATH_MESSAGES).replaceAll("-victim-", entity.getCapitalizedUsername()).replaceAll("-killer-", k.getCapitalizedUsername())));

        // We are in a minigame, so fire minigames events instead.
        if (optional.isPresent()) {
            optional.get().fireOnDeath(entity);

            if (!optional.get().canKeepItems()) {
                if (entity.getRights().lessThan(PlayerRights.ADMINISTRATOR)) {
                    dropDeathItems(entity, killer);
                }
            }

            killer.ifPresent(k -> optional.get().fireOnKill(k, entity));
            entity.move(optional.get().getDeathPosition(entity));
            return;
        }

        // We are not in a minigame, so do normal death.
        // if (entity.getRights().lessThan(PlayerRights.ADMINISTRATOR)) {
        dropDeathItems(entity, killer);
        entity.move(new Position(3093, 3244));
        // }
    }

    @Override
    public void postDeath(Player entity) {

        // Completely reset the player, then flag for the appearance block.
        entity.getCombatBuilder().reset();
        entity.getTolerance().reset();
        entity.setSpecialPercentage(100);
        entity.getPacketBuilder().sendConfig(301, 0);
        entity.setSpecialActivated(false);
        entity.setSkullTimer(0);
        entity.setSkullIcon(-1);
        entity.setTeleblockTimer(0);
        entity.animation(new Animation(65535));
        WeaponInterfaces.assign(entity, entity.getEquipment().get(Utility.EQUIPMENT_SLOT_WEAPON));
        entity.getPacketBuilder().sendMessage(entity.getRights().lessThan(PlayerRights.ADMINISTRATOR) ? "Oh dear, you're dead!"
            : "You are part of administration and therefore unaffected by death.");
        entity.getPacketBuilder().sendWalkable(65535);
        CombatPrayer.deactivateAll(entity);
        Skills.restoreAll(entity);
        entity.getFlags().flag(Flag.APPEARANCE);
    }

    /**
     * Drops the victim's items once they have died.
     * 
     * @param entity
     *            the player who was killed.
     * @param killer
     *            the player who killed the victim.
     */
    public void dropDeathItems(Player entity, Optional<Player> killer) {

        // Add the player's kept items to a cached list.
        List<Item> keep = new LinkedList<>();
        Arrays.stream(KEEP_ON_DEATH).filter(id -> entity.getEquipment().unequipItem(new Item(id), false) || entity.getInventory().remove(new Item(id))).forEach(id -> keep.add(new Item(id)));

        // Add the player's inventory and equipment to a cached list.
        List<Item> items = new LinkedList<>();
        Collections.addAll(items, entity.getEquipment().toSafeArray());
        Collections.addAll(items, entity.getInventory().toSafeArray());

        // Remove all of the player's inventory and equipment.
        entity.getEquipment().clear();
        entity.getInventory().clear();
        entity.getEquipment().refresh();
        entity.getInventory().refresh();
        entity.getFlags().flag(Flag.APPEARANCE);

        // Determine how many items will be kept.
        int amount = entity.getSkullTimer() > 0 ? 0 : 3;
        if (CombatPrayer.isActivated(entity, CombatPrayer.PROTECT_ITEM)) {
            amount++;
        }

        // Discard all null values in the player's item list and then
        // sort the elements from greatest to least value. Then, keep the top
        // "amount" items from the item list and drop the rest.
        if (amount > 0) {
            items.sort(Collections.reverseOrder((one, two) -> Integer.compare(one.getDefinition().getGeneralStorePrice(), two.getDefinition().getGeneralStorePrice())));

            for (Iterator<Item> it = items.iterator(); it.hasNext();) {
                Item next = it.next();

                if (amount == 0) {
                    break;
                }
                entity.getInventory().add(new Item(next.getId()));

                if (next.getDefinition().isStackable() && next.getAmount() > 1) {
                    next.decrementAmountBy(1);
                } else {
                    it.remove();
                }
                amount--;
            }
        }

        items.stream().forEach(item -> GroundItemManager.register(!killer.isPresent() ? new StaticGroundItem(item, entity.getPosition())
            : new GroundItem(item, entity.getPosition(), killer.get())));
        entity.getInventory().addAll(keep);
    }
}
