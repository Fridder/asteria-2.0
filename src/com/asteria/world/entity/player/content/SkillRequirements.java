package com.asteria.world.entity.player.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.asteria.util.JsonLoader;
import com.asteria.util.Utility;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.skill.SkillData;
import com.asteria.world.item.Item;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * A static utility class that contains methods that ensures a player has high
 * enough skill levels to equip certain items.
 * 
 * @author lare96
 */
public final class SkillRequirements {

    /** A map of the items and their respective skill requirements. */
    private static Map<Integer, SkillRequirement[]> requirements = new HashMap<>(
        450);

    /**
     * Determines if the argued player has high enough skill levels to equip the
     * argued item.
     * 
     * @param player
     *            the player who's skill levels will be checked.
     * @param item
     *            the item the player will be checked for.
     * @return <code>true</code> if the player can equip the item,
     *         <code>false</code> otherwise.
     */
    public static boolean check(Player player, Item item) {
        if (Objects.nonNull(item)) {
            SkillRequirement[] req = requirements.get(item.getId());

            if (req == null) {
                return true;
            }

            for (SkillRequirement s : req) {
                if (player.getSkills()[s.getSkillId()].getLevelForExperience() < s.getRequiredLevel()) {
                    player.getPacketBuilder().sendMessage(
                        "You need a " + Utility.capitalize(SkillData.getSkill(
                            s.getSkillId()).name().toLowerCase()) + " level of " + s.getRequiredLevel() + " to wear this item.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prepares the dynamic json loader for loading skill requirements.
     * 
     * @return the dynamic json loader.
     * @throws Exception
     *             if any errors occur while preparing for load.
     */
    public static JsonLoader parseRequirements() throws Exception {
        return new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {
                int id = reader.get("item-id").getAsInt();
                SkillRequirement[] req = builder.fromJson(
                    reader.get("requirement"), SkillRequirement[].class);
                requirements.put(id, req);
            }

            @Override
            public String filePath() {
                return "./data/json/equipment/skill_requirements.json";
            }
        };
    }

    /**
     * A skill requirement that is used to prevent players from equipping items
     * that are too high of a level for them.
     * 
     * @author lare96
     */
    private static class SkillRequirement {

        /** The level required to equip the item. */
        private int requiredLevel;

        /** The skill that corresponds to the level. */
        private int skillId;

        /**
         * Create a new {@link SkillRequirement}.
         * 
         * @param requiredLevel
         *            the level required to equip the item.
         * @param skillId
         *            the skill that corresponds to the level.
         */
        @SuppressWarnings("unused")
        public SkillRequirement(int requiredLevel, int skillId) {
            this.requiredLevel = requiredLevel;
            this.skillId = skillId;
        }

        /**
         * Gets the level required to equip the item.
         * 
         * @return the level required to equip the item.
         */
        public int getRequiredLevel() {
            return requiredLevel;
        }

        /**
         * Gets the skill that corresponds to the level.
         * 
         * @return the skill that corresponds to the level.
         */
        public int getSkillId() {
            return skillId;
        }
    }

    private SkillRequirements() {}
}
