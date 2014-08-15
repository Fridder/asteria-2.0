package com.asteria.world.entity.player.content;

import com.asteria.util.Utility;
import com.asteria.world.entity.combat.special.CombatSpecial;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.skill.Skills;
import com.asteria.world.item.Item;
import com.asteria.world.item.ItemDefinition;

/**
 * Changes the interface in the first sidebar whenever a new weapon is equipped
 * or an existing item is unequipped.
 * 
 * @author lare96
 */
public final class AssignWeaponInterface {

    // TODO: Load all of this through json file, not going to touch this until
    // that's done.

    /** An array of all of the weapon interfaces. */
    private static WeaponInterface[] weaponInterface = new WeaponInterface[7956];

    /** Loads all of the weapon interfaces. */
    public static void loadWeaponInterfaces() {
        for (ItemDefinition def : ItemDefinition.getDefinitions()) {
            if (def == null || def.isNoted() || def.getEquipmentSlot() != Utility.EQUIPMENT_SLOT_WEAPON) {
                continue;
            }

            if (def.getItemName().startsWith("Staff") || def.getItemName()
                    .endsWith("staff") || def.getItemName().endsWith("wands")) {
                weaponInterface[def.getItemId()] = WeaponInterface.STAFF;
            } else if (def.getItemName().startsWith("Scythe")) {
                weaponInterface[def.getItemId()] = WeaponInterface.SCYTHE;
            } else if (def.getItemName().equals("Dharoks greataxe")) {
                weaponInterface[def.getItemId()] = WeaponInterface.BATTLEAXE;
            } else if (def.getItemName().equals("Torags hammers")) {
                weaponInterface[def.getItemId()] = WeaponInterface.WARHAMMER;
            } else if (def.getItemName().endsWith("warhammer") || def
                    .getItemName().endsWith("maul") || def.getItemName()
                    .equals("Tzhaar-ket-om")) {
                weaponInterface[def.getItemId()] = WeaponInterface.WARHAMMER;
            } else if (def.getItemName().endsWith("battleaxe")) {
                weaponInterface[def.getItemId()] = WeaponInterface.BATTLEAXE;
            } else if (def.getItemName().equals("Crossbow") || def
                    .getItemName().endsWith("crossbow")) {
                weaponInterface[def.getItemId()] = WeaponInterface.CROSSBOW;
            } else if (def.getItemName().endsWith("shortbow") || def
                    .getItemName().startsWith("Crystal bow") || def
                    .getItemName().endsWith("crystal bow")) {
                weaponInterface[def.getItemId()] = WeaponInterface.SHORTBOW;
            } else if (def.getItemName().endsWith("longbow")) {
                weaponInterface[def.getItemId()] = WeaponInterface.LONGBOW;
            } else if (def.getItemName().endsWith("dagger") || def
                    .getItemName().endsWith("dagger(p)") || def.getItemName()
                    .endsWith("dagger(p+)") || def.getItemName().endsWith(
                    "dagger(p++)")) {
                weaponInterface[def.getItemId()] = WeaponInterface.DAGGER;
            } else if (def.getItemName().endsWith("longsword")) {
                weaponInterface[def.getItemId()] = WeaponInterface.LONGSWORD;
            } else if (def.getItemName().endsWith(" sword") && !def
                    .getItemName().endsWith("2h sword")) {
                weaponInterface[def.getItemId()] = WeaponInterface.SWORD;
            } else if (def.getItemName().endsWith("scimitar")) {
                weaponInterface[def.getItemId()] = WeaponInterface.SCIMITAR;
            } else if (def.getItemName().endsWith("2h sword")) {
                weaponInterface[def.getItemId()] = WeaponInterface.TWO_HANDED_SWORD;
            } else if (def.getItemName().endsWith("mace")) {
                weaponInterface[def.getItemId()] = WeaponInterface.MACE;
            } else if (def.getItemName().endsWith("knife") || def.getItemName()
                    .endsWith("knife(p)") || def.getItemName().endsWith(
                    "knife(p+)") || def.getItemName().endsWith("knife(p++)") || def
                    .getItemName().equals("Toktz-xil-ul")) {
                weaponInterface[def.getItemId()] = WeaponInterface.KNIFE;
            } else if (def.getItemName().endsWith("spear")) {
                weaponInterface[def.getItemId()] = WeaponInterface.SPEAR;
            } else if (def.getItemName().endsWith("pickaxe")) {
                weaponInterface[def.getItemId()] = WeaponInterface.PICKAXE;
            } else if (def.getItemName().endsWith("claws")) {
                weaponInterface[def.getItemId()] = WeaponInterface.CLAWS;
            } else if (def.getItemName().endsWith("halberd")) {
                weaponInterface[def.getItemId()] = WeaponInterface.HALBERD;
            } else if (def.getItemName().endsWith("whip") || def.getItemName()
                    .endsWith("flail")) {
                weaponInterface[def.getItemId()] = WeaponInterface.WHIP;
            } else if (def.getItemName().endsWith("thrownaxe")) {
                weaponInterface[def.getItemId()] = WeaponInterface.THROWNAXE;
            } else if (def.getItemName().endsWith("javelin") || def
                    .getItemName().endsWith("javelin(p)") || def.getItemName()
                    .endsWith("javelin(p+)") || def.getItemName().endsWith(
                    "javelin(p++)")) {
                weaponInterface[def.getItemId()] = WeaponInterface.JAVELIN;
            } else if (def.getItemName().endsWith("dart") || def.getItemName()
                    .endsWith("dart(p)") || def.getItemName().endsWith(
                    "dart(p+)") || def.getItemName().endsWith("dart(p++)")) {
                weaponInterface[def.getItemId()] = WeaponInterface.DART;
            } else if (def.getItemName().endsWith("axe") && !def.getItemName()
                    .endsWith("pickaxe")) {
                weaponInterface[def.getItemId()] = WeaponInterface.BATTLEAXE;
            } else {
                weaponInterface[def.getItemId()] = WeaponInterface.UNARMED;
            }
        }
    }

    /**
     * All of the interfaces for weapons and the data needed to display these
     * interfaces properly.
     * 
     * @author lare96
     */
    public enum WeaponInterface {
        STAFF(328, 331, 6, new FightType[] { FightType.STAFF_BASH,
                FightType.STAFF_FOCUS, FightType.STAFF_POUND }, -1, -1,
                new SpecialWeapon[] {}),
        WARHAMMER(425, 428, 6, new FightType[] { FightType.WARHAMMER_BLOCK,
                FightType.WARHAMMER_POUND, FightType.WARHAMMER_PUMMEL }, 7474,
                7486, new SpecialWeapon[] { new SpecialWeapon(4153,
                        CombatSpecial.GRANITE_MAUL) }),
        SCYTHE(776, 779, 6, new FightType[] { FightType.SCYTHE_BLOCK,
                FightType.SCYTHE_CHOP, FightType.SCYTHE_JAB,
                FightType.SCYTHE_REAP }, -1, -1, new SpecialWeapon[] {}),
        BATTLEAXE(1698, 1701, 6, new FightType[] { FightType.BATTLEAXE_BLOCK,
                FightType.BATTLEAXE_CHOP, FightType.BATTLEAXE_HACK,
                FightType.BATTLEAXE_SMASH }, 7499, 7511,
                new SpecialWeapon[] { new SpecialWeapon(1377,
                        CombatSpecial.DRAGON_BATTLEAXE) }),
        CROSSBOW(1749, 1752, 5, new FightType[] { FightType.CROSSBOW_RAPID,
                FightType.CROSSBOW_ACCURATE, FightType.CROSSBOW_LONGRANGE },
                7524, 7536, new SpecialWeapon[] {}),
        SHORTBOW(1764, 1767, 5, new FightType[] { FightType.SHORTBOW_RAPID,
                FightType.SHORTBOW_ACCURATE, FightType.SHORTBOW_LONGRANGE },
                7549, 7561, new SpecialWeapon[] { new SpecialWeapon(861,
                        CombatSpecial.MAGIC_SHORTBOW) }),
        LONGBOW(1764, 1767, 6, new FightType[] { FightType.LONGBOW_RAPID,
                FightType.LONGBOW_ACCURATE, FightType.LONGBOW_LONGRANGE },
                7549, 7561, new SpecialWeapon[] { new SpecialWeapon(859,
                        CombatSpecial.MAGIC_LONGBOW) }),
        DAGGER(2276, 2279, 5, new FightType[] { FightType.DAGGER_BLOCK,
                FightType.DAGGER_LUNGE, FightType.DAGGER_SLASH,
                FightType.DAGGER_STAB }, 7574, 7586, new SpecialWeapon[] {
                new SpecialWeapon(1215, CombatSpecial.DRAGON_DAGGER),
                new SpecialWeapon(1231, CombatSpecial.DRAGON_DAGGER),
                new SpecialWeapon(5680, CombatSpecial.DRAGON_DAGGER),
                new SpecialWeapon(5698, CombatSpecial.DRAGON_DAGGER) }),
        SWORD(2276, 2279, 5, new FightType[] { FightType.SWORD_BLOCK,
                FightType.SWORD_LUNGE, FightType.SWORD_SLASH,
                FightType.SWORD_STAB }, 7574, 7586, new SpecialWeapon[] {}),
        SCIMITAR(2423, 2426, 5, new FightType[] { FightType.SCIMITAR_BLOCK,
                FightType.SCIMITAR_CHOP, FightType.SCIMITAR_LUNGE,
                FightType.SCIMITAR_SLASH }, 7599, 7611,
                new SpecialWeapon[] { new SpecialWeapon(4587,
                        CombatSpecial.DRAGON_SCIMITAR) }),
        LONGSWORD(2423, 2426, 6, new FightType[] { FightType.LONGSWORD_BLOCK,
                FightType.LONGSWORD_CHOP, FightType.LONGSWORD_LUNGE,
                FightType.LONGSWORD_SLASH }, 7599, 7611,
                new SpecialWeapon[] { new SpecialWeapon(1305,
                        CombatSpecial.DRAGON_LONGSWORD) }),
        MACE(3796, 3799, 4, new FightType[] { FightType.MACE_BLOCK,
                FightType.MACE_POUND, FightType.MACE_PUMMEL,
                FightType.MACE_SPIKE }, 7624, 7636,
                new SpecialWeapon[] { new SpecialWeapon(1434,
                        CombatSpecial.DRAGON_MACE), }),
        KNIFE(4446, 4449, 4, new FightType[] { FightType.KNIFE_RAPID,
                FightType.KNIFE_ACCURATE, FightType.KNIFE_LONGRANGE }, 7649,
                7661, new SpecialWeapon[] {}),
        SPEAR(4679, 4682, 6, new FightType[] { FightType.SPEAR_BLOCK,
                FightType.SPEAR_LUNGE, FightType.SPEAR_POUND,
                FightType.SPEAR_SWIPE }, 7674, 7686, new SpecialWeapon[] {
                new SpecialWeapon(1249, CombatSpecial.DRAGON_SPEAR),
                new SpecialWeapon(1263, CombatSpecial.DRAGON_SPEAR),
                new SpecialWeapon(5716, CombatSpecial.DRAGON_SPEAR),
                new SpecialWeapon(5730, CombatSpecial.DRAGON_SPEAR) }),
        TWO_HANDED_SWORD(4705, 4708, 6,
                new FightType[] { FightType.TWOHANDEDSWORD_BLOCK,
                        FightType.TWOHANDEDSWORD_CHOP,
                        FightType.TWOHANDEDSWORD_SLASH,
                        FightType.TWOHANDEDSWORD_SMASH }, 7699, 7711,
                new SpecialWeapon[] { new SpecialWeapon(7158,
                        CombatSpecial.DRAGON_2H_SWORD) }),
        PICKAXE(5570, 5573, 6, new FightType[] { FightType.PICKAXE_BLOCK,
                FightType.PICKAXE_IMPALE, FightType.PICKAXE_SMASH,
                FightType.PICKAXE_SPIKE }, -1, -1, new SpecialWeapon[] {}),
        CLAWS(7762, 7765, 4, new FightType[] { FightType.CLAWS_BLOCK,
                FightType.CLAWS_CHOP, FightType.CLAWS_LUNGE,
                FightType.CLAWS_SLASH }, 7800, 7812, new SpecialWeapon[] {}),
        HALBERD(8460, 8463, 6, new FightType[] { FightType.HALBERD_FEND,
                FightType.HALBERD_JAB, FightType.HALBERD_SWIPE }, 8493, 8505,
                new SpecialWeapon[] { new SpecialWeapon(3204,
                        CombatSpecial.DRAGON_HALBERD) }),
        UNARMED(5855, 5857, 6, new FightType[] { FightType.UNARMED_BLOCK,
                FightType.UNARMED_KICK, FightType.UNARMED_PUNCH }, -1, -1,
                new SpecialWeapon[] {}),
        WHIP(12290, 12293, 4, new FightType[] { FightType.WHIP_FLICK,
                FightType.WHIP_LASH, FightType.WHIP_DEFLECT }, 12323, 12335,
                new SpecialWeapon[] { new SpecialWeapon(4151,
                        CombatSpecial.ABYSSAL_WHIP) }),
        THROWNAXE(4446, 4449, 6, new FightType[] { FightType.THROWNAXE_RAPID,
                FightType.THROWNAXE_ACCURATE, FightType.THROWNAXE_LONGRANGE },
                7649, 7661, new SpecialWeapon[] {}),
        DART(4446, 4449, 3, new FightType[] { FightType.DART_RAPID,
                FightType.DART_ACCURATE, FightType.DART_LONGRANGE }, 7649,
                7661, new SpecialWeapon[] {}),
        JAVELIN(4446, 4449, 6, new FightType[] { FightType.JAVELIN_RAPID,
                FightType.JAVELIN_ACCURATE, FightType.JAVELIN_LONGRANGE },
                7649, 7661, new SpecialWeapon[] {});

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

        /** The items using this interface that requrie a special bar. */
        private SpecialWeapon[] specialAttackItems;

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
         * @param specialAttackItems
         *            the items using this interface that requrie a special bar.
         */
        private WeaponInterface(int interfaceId, int nameLineId, int speed,
                FightType[] fightType, int specialBar, int specialMeter,
                SpecialWeapon[] specialAttackItems) {
            this.interfaceId = interfaceId;
            this.nameLineId = nameLineId;
            this.speed = speed;
            this.fightType = fightType;
            this.specialBar = specialBar;
            this.specialMeter = specialMeter;
            this.specialAttackItems = specialAttackItems;
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

        /**
         * Gets the items using this interface that require a special bar.
         * 
         * @return the items using this interface that require a special bar.
         */
        public SpecialWeapon[] getSpecialAttackItems() {
            return specialAttackItems;
        }
    }

    /**
     * The different train types.
     * 
     * @author lare96
     */
    public enum TrainType {
        ATTACK,
        STRENGTH,
        DEFENCE,
        RANGED,
        MAGIC
    }

    public enum FightStyle {
        ACCURATE,
        AGGRESSIVE,
        DEFENSIVE,
        CONTROLLED
    }

    /**
     * The different fight types on the weapon interfaces.
     * 
     * @author lare96
     */
    public enum FightType {
        STAFF_BASH(406, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
        STAFF_POUND(406, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        STAFF_FOCUS(406, new int[] { Skills.DEFENCE }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
        WARHAMMER_POUND(401, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
        WARHAMMER_PUMMEL(401, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        WARHAMMER_BLOCK(401, new int[] { Skills.DEFENCE }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
        SCYTHE_REAP(408, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        SCYTHE_CHOP(451, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
        SCYTHE_JAB(412, new int[] { Skills.STRENGTH }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        SCYTHE_BLOCK(408, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        BATTLEAXE_CHOP(1833, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        BATTLEAXE_HACK(1833, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        BATTLEAXE_SMASH(401, new int[] { Skills.STRENGTH }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        BATTLEAXE_BLOCK(1833, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        CROSSBOW_ACCURATE(427, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        CROSSBOW_RAPID(427, new int[] { Skills.RANGED }, 43, 1,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        CROSSBOW_LONGRANGE(427, new int[] { Skills.RANGED, Skills.DEFENCE },
                43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
        SHORTBOW_ACCURATE(426, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        SHORTBOW_RAPID(426, new int[] { Skills.RANGED }, 43, 1,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        SHORTBOW_LONGRANGE(426, new int[] { Skills.RANGED, Skills.DEFENCE },
                43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
        LONGBOW_ACCURATE(426, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        LONGBOW_RAPID(426, new int[] { Skills.RANGED }, 43, 1,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        LONGBOW_LONGRANGE(426, new int[] { Skills.RANGED, Skills.DEFENCE }, 43,
                2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
        DAGGER_STAB(400, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_STAB, FightStyle.ACCURATE),
        DAGGER_LUNGE(400, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
        DAGGER_SLASH(451, new int[] { Skills.STRENGTH }, 43, 2,
                Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
        DAGGER_BLOCK(400, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
        SWORD_STAB(412, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_STAB, FightStyle.ACCURATE),
        SWORD_LUNGE(412, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
        SWORD_SLASH(451, new int[] { Skills.STRENGTH }, 43, 2,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        SWORD_BLOCK(412, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
        SCIMITAR_CHOP(451, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        SCIMITAR_SLASH(451, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        SCIMITAR_LUNGE(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB,
                FightStyle.CONTROLLED),
        SCIMITAR_BLOCK(451, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        LONGSWORD_CHOP(451, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        LONGSWORD_SLASH(451, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        LONGSWORD_LUNGE(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB,
                FightStyle.CONTROLLED),
        LONGSWORD_BLOCK(451, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        MACE_POUND(1833, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
        MACE_PUMMEL(401, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        MACE_SPIKE(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB,
                FightStyle.CONTROLLED),
        MACE_BLOCK(401, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
        KNIFE_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        KNIFE_RAPID(806, new int[] { Skills.RANGED }, 43, 1,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        KNIFE_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43,
                2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
        SPEAR_LUNGE(2080, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 0, Utility.ATTACK_STAB,
                FightStyle.CONTROLLED),
        SPEAR_SWIPE(2081, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 1, Utility.ATTACK_SLASH,
                FightStyle.CONTROLLED),
        SPEAR_POUND(2082, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 2, Utility.ATTACK_CRUSH,
                FightStyle.CONTROLLED),
        SPEAR_BLOCK(2080, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
        TWOHANDEDSWORD_CHOP(407, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        TWOHANDEDSWORD_SLASH(407, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        TWOHANDEDSWORD_SMASH(406, new int[] { Skills.STRENGTH }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        TWOHANDEDSWORD_BLOCK(407, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        PICKAXE_SPIKE(412, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_STAB, FightStyle.ACCURATE),
        PICKAXE_IMPALE(412, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
        PICKAXE_SMASH(401, new int[] { Skills.STRENGTH }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        PICKAXE_BLOCK(412, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
        CLAWS_CHOP(451, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        CLAWS_SLASH(451, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        CLAWS_LUNGE(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB,
                FightStyle.CONTROLLED),
        CLAWS_BLOCK(451, new int[] { Skills.DEFENCE }, 43, 3,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        HALBERD_JAB(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 0, Utility.ATTACK_STAB,
                FightStyle.CONTROLLED),
        HALBERD_SWIPE(440, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
        HALBERD_FEND(412, new int[] { Skills.DEFENCE }, 43, 2,
                Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
        UNARMED_PUNCH(422, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
        UNARMED_KICK(423, new int[] { Skills.STRENGTH }, 43, 1,
                Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
        UNARMED_BLOCK(422, new int[] { Skills.DEFENCE }, 43, 2,
                Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
        WHIP_FLICK(1658, new int[] { Skills.ATTACK }, 43, 0,
                Utility.ATTACK_SLASH, FightStyle.ACCURATE),
        WHIP_LASH(1658, new int[] { Skills.ATTACK, Skills.STRENGTH,
                Skills.DEFENCE }, 43, 1, Utility.ATTACK_SLASH,
                FightStyle.CONTROLLED),
        WHIP_DEFLECT(1658, new int[] { Skills.DEFENCE }, 43, 2,
                Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
        THROWNAXE_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        THROWNAXE_RAPID(806, new int[] { Skills.RANGED }, 43, 1,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        THROWNAXE_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE },
                43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
        DART_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        DART_RAPID(806, new int[] { Skills.RANGED }, 43, 1,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        DART_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2,
                Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
        JAVELIN_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0,
                Utility.ATTACK_RANGE, FightStyle.ACCURATE),
        JAVELIN_RAPID(806, new int[] { Skills.RANGED }, 43, 2,
                Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
        JAVELIN_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43,
                3, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE);

        /** The animation this fight type holds. */
        private int animation;

        /** The train type this fight type holds. */
        private int[] trainType;

        /** The parent config id. */
        private int parentId;

        /** The child config id. */
        private int childId;

        /** The bonus type. */
        private int bonusType;

        /** The fighting style. */
        private FightStyle style;

        /**
         * Create a new {@link FightType}.
         * 
         * @param animation
         *            the animation this fight type holds.
         * @param trainType
         *            the train type this fight type holds.
         * @param parentId
         *            the parent config id.
         * @param childId
         *            the child config id.
         * @param bonusType
         *            the bonus type.
         * @param fightStyle
         *            the fighting style.
         */
        private FightType(int animation, int[] trainType, int parentId,
                int childId, int bonusType, FightStyle style) {
            this.animation = animation;
            this.trainType = trainType;
            this.parentId = parentId;
            this.childId = childId;
            this.bonusType = bonusType;
            this.style = style;
        }

        /**
         * Gets the animation this fight type holds.
         * 
         * @return the animation.
         */
        public int getAnimation() {
            return animation;
        }

        /**
         * Gets the train type this fight type holds.
         * 
         * @return the train type.
         */
        public int[] getTrainType() {
            return trainType;
        }

        /**
         * Gets the parent config id.
         * 
         * @return the parent id.
         */
        public int getParentId() {
            return parentId;
        }

        /**
         * Gets the child config id.
         * 
         * @return the child id.
         */
        public int getChildId() {
            return childId;
        }

        /**
         * Gets the bonus type.
         * 
         * @return the bonus type.
         */
        public int getBonusType() {
            return bonusType;
        }

        /**
         * Gets the fighting style.
         * 
         * @return the fighting style.
         */
        public FightStyle getStyle() {
            return style;
        }

        public int getCorrespondingBonus() {
            switch (bonusType) {
            case Utility.ATTACK_CRUSH:
                return Utility.DEFENCE_CRUSH;
            case Utility.ATTACK_MAGIC:
                return Utility.DEFENCE_MAGIC;
            case Utility.ATTACK_RANGE:
                return Utility.DEFENCE_RANGE;
            case Utility.ATTACK_SLASH:
                return Utility.DEFENCE_SLASH;
            case Utility.ATTACK_STAB:
                return Utility.DEFENCE_STAB;
            default:
                return Utility.DEFENCE_CRUSH;
            }
        }
    }

    /**
     * Assigns special bars to the attack style interface if needed.
     * 
     * @param player
     *            the player to assign the special bar for.
     */
    public static void assignSpecialBar(Player player) {
        if (player.getWeapon().getSpecialBar() == -1) {
            player.setCombatSpecial(null);
            return;
        }

        for (SpecialWeapon weapon : player.getWeapon().getSpecialAttackItems()) {
            if (player.getEquipment().getItemId(Utility.EQUIPMENT_SLOT_WEAPON) == weapon
                    .getItemId()) {
                player.getPacketBuilder().sendHideInterfaceLayer(
                        player.getWeapon().getSpecialBar(), false);
                player.setCombatSpecial(weapon.getCombatSpecial());
                return;
            }
        }
        player.getPacketBuilder().sendHideInterfaceLayer(
                player.getWeapon().getSpecialBar(), true);
        player.setCombatSpecial(null);
    }

    /**
     * Assigns the correct interface for the player based on the item.
     * 
     * @param player
     *            the player to assign the interface for.
     * @param item
     *            the item to base the interface on.
     */
    public static void assignInterface(Player player, Item item) {
        if (item == null) {
            reset(player);
            return;
        }

        WeaponInterface weapon = weaponInterface[item.getId()];
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
                "" + item.getDefinition().getItemName() + "",
                weapon.getNameLineId());
        player.setWeapon(weapon);
        assignSpecialBar(player);
        CombatSpecial.updateSpecialAmount(player);
    }

    /**
     * Resets the sidebar when an item is unequipped from the weapon slot.
     * 
     * @param player
     *            the player to reset the sidebar for.
     */
    private static void reset(Player player) {

        /** Reset the sidebar back to "unarmed". */
        player.getPacketBuilder().sendSidebarInterface(0,
                WeaponInterface.UNARMED.getInterfaceId());
        player.getPacketBuilder().sendString("Unarmed",
                WeaponInterface.UNARMED.getNameLineId());
        player.setWeapon(WeaponInterface.UNARMED);
        assignSpecialBar(player);
    }

    /**
     * Changes the fight type when a weapon is equipped or unequipped.
     * 
     * @param player
     *            the player changing their weapon.
     */
    public static void changeFightType(Player player) {

        for (FightType fightType : player.getWeapon().getFightType()) {
            if (fightType.getTrainType() == player.getFightType()
                    .getTrainType()) {
                player.setFightType(fightType);
                player.getPacketBuilder().sendConfig(
                        player.getFightType().getParentId(),
                        player.getFightType().getChildId());
                return;
            }
        }

        player.setFightType(player.getWeapon().getFightType()[0]);
        player.getPacketBuilder().sendConfig(
                player.getFightType().getParentId(),
                player.getFightType().getChildId());
    }

    /**
     * A single weapon that has a special attack.
     * 
     * @author lare96
     */
    private static class SpecialWeapon {

        /** The weapon that has the special attack. */
        private int itemId;

        /** The actual special attack. */
        private CombatSpecial combatSpecial;

        /**
         * Create a new {@link SpecialWeapon}.
         * 
         * @param itemId
         *            the weapon that has the special attack.
         * @param combatSpecial
         *            the actual special attack.
         */
        public SpecialWeapon(int itemId, CombatSpecial combatSpecial) {
            this.itemId = itemId;
            this.combatSpecial = combatSpecial;
        }

        /**
         * Gets the weapon that has the special attack.
         * 
         * @return the weapon that has the special attack.
         */
        public int getItemId() {
            return itemId;
        }

        /**
         * Gets the actual special attack.
         * 
         * @return the actual special attack.
         */
        public CombatSpecial getCombatSpecial() {
            return combatSpecial;
        }
    }

    private AssignWeaponInterface() {}
}
