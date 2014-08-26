package com.asteria.world.entity.player.content;

import java.util.HashMap;
import java.util.Map;

import com.asteria.util.JsonLoader;
import com.asteria.world.entity.combat.weapon.CombatSpecial;
import com.asteria.world.entity.combat.weapon.FightType;
import com.asteria.world.entity.player.Player;
import com.asteria.world.item.Item;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * A static utility class that displays holds and displays data for weapon
 * interfaces.
 * 
 * @author lare96
 */
public final class WeaponInterfaces {

    /** A map of items and their respective interfaces. */
    private static Map<Integer, WeaponInterface> interfaces = new HashMap<>(500);

    /**
     * All of the interfaces for weapons and the data needed to display these
     * interfaces properly.
     * 
     * @author lare96
     */
    public enum WeaponInterface {
        STAFF(328, 331, 6, new FightType[] { FightType.STAFF_BASH,
                FightType.STAFF_FOCUS, FightType.STAFF_POUND }),
        WARHAMMER(425, 428, 6, new FightType[] { FightType.WARHAMMER_BLOCK,
                FightType.WARHAMMER_POUND, FightType.WARHAMMER_PUMMEL }, 7474, 7486),
        SCYTHE(776, 779, 6, new FightType[] { FightType.SCYTHE_BLOCK,
                FightType.SCYTHE_CHOP, FightType.SCYTHE_JAB,
                FightType.SCYTHE_REAP }),
        BATTLEAXE(1698, 1701, 6, new FightType[] { FightType.BATTLEAXE_BLOCK,
                FightType.BATTLEAXE_CHOP, FightType.BATTLEAXE_HACK,
                FightType.BATTLEAXE_SMASH }, 7499, 7511),
        CROSSBOW(1749, 1752, 5, new FightType[] { FightType.CROSSBOW_RAPID,
                FightType.CROSSBOW_ACCURATE, FightType.CROSSBOW_LONGRANGE }, 7524, 7536),
        SHORTBOW(1764, 1767, 5, new FightType[] { FightType.SHORTBOW_RAPID,
                FightType.SHORTBOW_ACCURATE, FightType.SHORTBOW_LONGRANGE }, 7549, 7561),
        LONGBOW(1764, 1767, 6, new FightType[] { FightType.LONGBOW_RAPID,
                FightType.LONGBOW_ACCURATE, FightType.LONGBOW_LONGRANGE }, 7549, 7561),
        DAGGER(2276, 2279, 5, new FightType[] { FightType.DAGGER_BLOCK,
                FightType.DAGGER_LUNGE, FightType.DAGGER_SLASH,
                FightType.DAGGER_STAB }, 7574, 7586),
        SWORD(2276, 2279, 5, new FightType[] { FightType.SWORD_BLOCK,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_STAB }, 7574, 7586),
        SCIMITAR(2423, 2426, 5, new FightType[] { FightType.SCIMITAR_BLOCK,
                FightType.SCIMITAR_CHOP, FightType.SCIMITAR_LUNGE,
                FightType.SCIMITAR_SLASH }, 7599, 7611),
        LONGSWORD(2423, 2426, 6, new FightType[] { FightType.LONGSWORD_BLOCK,
                FightType.LONGSWORD_CHOP, FightType.LONGSWORD_LUNGE,
                FightType.LONGSWORD_SLASH }, 7599, 7611),
        MACE(3796, 3799, 4, new FightType[] { FightType.MACE_BLOCK,
                FightType.MACE_POUND, FightType.MACE_PUMMEL,
                FightType.MACE_SPIKE }, 7624, 7636),
        KNIFE(4446, 4449, 4, new FightType[] { FightType.KNIFE_RAPID,
                FightType.KNIFE_ACCURATE, FightType.KNIFE_LONGRANGE }, 7649, 7661),
        SPEAR(4679, 4682, 6, new FightType[] { FightType.SPEAR_BLOCK,
                FightType.SPEAR_LUNGE, FightType.SPEAR_POUND,
                FightType.SPEAR_SWIPE }, 7674, 7686),
        TWO_HANDED_SWORD(4705, 4708, 6, new FightType[] {
                FightType.TWOHANDEDSWORD_BLOCK, FightType.TWOHANDEDSWORD_CHOP,
                FightType.TWOHANDEDSWORD_SLASH, FightType.TWOHANDEDSWORD_SMASH }, 7699, 7711),
        PICKAXE(5570, 5573, 6, new FightType[] { FightType.PICKAXE_BLOCK,
                FightType.PICKAXE_IMPALE, FightType.PICKAXE_SMASH,
                FightType.PICKAXE_SPIKE }),
        CLAWS(7762, 7765, 4, new FightType[] { FightType.CLAWS_BLOCK,
                FightType.CLAWS_CHOP, FightType.CLAWS_LUNGE,
                FightType.CLAWS_SLASH }, 7800, 7812),
        HALBERD(8460, 8463, 6, new FightType[] { FightType.HALBERD_FEND,
                FightType.HALBERD_JAB, FightType.HALBERD_SWIPE }, 8493, 8505),
        UNARMED(5855, 5857, 6, new FightType[] { FightType.UNARMED_BLOCK,
                FightType.UNARMED_KICK, FightType.UNARMED_PUNCH }),
        WHIP(12290, 12293, 4, new FightType[] { FightType.WHIP_FLICK,
                FightType.WHIP_LASH, FightType.WHIP_DEFLECT }, 12323, 12335),
        THROWNAXE(4446, 4449, 6, new FightType[] { FightType.THROWNAXE_RAPID,
                FightType.THROWNAXE_ACCURATE, FightType.THROWNAXE_LONGRANGE }, 7649, 7661),
        DART(4446, 4449, 3, new FightType[] { FightType.DART_RAPID,
                FightType.DART_ACCURATE, FightType.DART_LONGRANGE }, 7649, 7661),
        JAVELIN(4446, 4449, 6, new FightType[] { FightType.JAVELIN_RAPID,
                FightType.JAVELIN_ACCURATE, FightType.JAVELIN_LONGRANGE }, 7649, 7661);

        /** The interface that will be displayed on the sidebar. */
        private int interfaceId;

        /** The line that the name of the item will be printed to. */
        private int nameLineId;

        /** The attack speed of weapons using this interface. */
        private int speed;

        /** The fight types that correspond with this interface. */
        private FightType[] fightType;

        /** The id of the special bar for this interface. */
        private int specialBar;

        /** The id of the special meter for this interface. */
        private int specialMeter;

        /**
         * Creates a new weapon interface.
         * 
         * @param interfaceId
         *            the interface that will be displayed on the sidebar.
         * @param nameLineId
         *            the line that the name of the item will be printed to.
         * @param speed
         *            the attack speed of weapons using this interface.
         * @param fightType
         *            the fight types that correspond with this interface.
         * @param specialBar
         *            the id of the special bar for this interface.
         * @param specialMeter
         *            the id of the special meter for this interface.
         */
        private WeaponInterface(int interfaceId, int nameLineId, int speed,
            FightType[] fightType, int specialBar, int specialMeter) {
            this.interfaceId = interfaceId;
            this.nameLineId = nameLineId;
            this.speed = speed;
            this.fightType = fightType;
            this.specialBar = specialBar;
            this.specialMeter = specialMeter;
        }

        /**
         * Creates a new weapon interface.
         * 
         * @param interfaceId
         *            the interface that will be displayed on the sidebar.
         * @param nameLineId
         *            the line that the name of the item will be printed to.
         * @param speed
         *            the attack speed of weapons using this interface.
         * @param fightType
         *            the fight types that correspond with this interface.
         */
        private WeaponInterface(int interfaceId, int nameLineId, int speed,
            FightType[] fightType) {
            this(interfaceId, nameLineId, speed, fightType, -1, -1);
        }

        /**
         * Gets the interface that will be displayed on the sidebar.
         * 
         * @return the interface id.
         */
        public int getInterfaceId() {
            return interfaceId;
        }

        /**
         * Gets the line that the name of the item will be printed to.
         * 
         * @return the name line id.
         */
        public int getNameLineId() {
            return nameLineId;
        }

        /**
         * Gets the attack speed of weapons using this interface.
         * 
         * @return the attack speed of weapons using this interface.
         */
        public int getSpeed() {
            return speed;
        }

        /**
         * Gets the fight types that correspond with this interface.
         * 
         * @return the fight types that correspond with this interface.
         */
        public FightType[] getFightType() {
            return fightType;
        }

        /**
         * Gets the id of the special bar for this interface.
         * 
         * @return the id of the special bar for this interface.
         */
        public int getSpecialBar() {
            return specialBar;
        }

        /**
         * Gets the id of the special meter for this interface.
         * 
         * @return the id of the special meter for this interface.
         */
        public int getSpecialMeter() {
            return specialMeter;
        }
    }

    /**
     * Assigns an interface to the combat sidebar based on the argued weapon.
     * 
     * @param player
     *            the player that the interface will be assigned for.
     * @param item
     *            the item that the interface will be chosen for.
     */
    public static void assign(Player player, Item item) {
        if (item == null) {
            reset(player);
            return;
        }

        WeaponInterface weapon = interfaces.get(item.getId());
        if (weapon == WeaponInterface.UNARMED) {
            player.getPacketBuilder().sendSidebarInterface(0,
                weapon.getInterfaceId());
            player.getPacketBuilder().sendString("Unarmed",
                weapon.getNameLineId());
            player.setWeapon(WeaponInterface.UNARMED);
            return;
        } else if (weapon == WeaponInterface.CROSSBOW) {
            player.getPacketBuilder().sendString("Weapon: ",
                weapon.getNameLineId() - 1);
        } else if (weapon == WeaponInterface.WHIP) {
            player.getPacketBuilder().sendString("Weapon: ",
                weapon.getNameLineId() - 1);
        }

        player.getPacketBuilder().sendItemOnInterface(
            weapon.getInterfaceId() + 1, 200, item.getId());
        player.getPacketBuilder().sendSidebarInterface(0,
            weapon.getInterfaceId());
        player.getPacketBuilder().sendString(
            item.getDefinition().getItemName(), weapon.getNameLineId());
        player.setWeapon(weapon);
        CombatSpecial.assign(player);
        CombatSpecial.updateSpecialAmount(player);
    }

    /**
     * Resets the current interface back to "UNARMED".
     * 
     * @param player
     *            the player to reset the interface for.
     */
    private static void reset(Player player) {
        player.getPacketBuilder().sendSidebarInterface(0,
            WeaponInterface.UNARMED.getInterfaceId());
        player.getPacketBuilder().sendString("Unarmed",
            WeaponInterface.UNARMED.getNameLineId());
        player.setWeapon(WeaponInterface.UNARMED);
        CombatSpecial.assign(player);
    }

    /**
     * Prepares the dynamic json loader for loading weapon interfaces.
     * 
     * @return the dynamic json loader.
     * @throws Exception
     *             if any errors occur while preparing for load.
     */
    public static JsonLoader parseInterfaces() throws Exception {
        return new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {
                int id = reader.get("item-id").getAsInt();
                WeaponInterface animation = builder.fromJson(reader
                    .get("interface"), WeaponInterface.class);
                interfaces.put(id, animation);
            }

            @Override
            public String filePath() {
                return "./data/json/equipment/weapon_interfaces.json";
            }
        };
    }
}
