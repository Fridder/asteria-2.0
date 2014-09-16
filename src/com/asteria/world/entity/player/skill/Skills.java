package com.asteria.world.entity.player.skill;

import java.util.HashMap;
import java.util.Map;

import com.asteria.util.Utility;
import com.asteria.world.entity.Graphic;
import com.asteria.world.entity.UpdateFlags.Flag;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.skill.event.SkillEvent;
import com.asteria.world.entity.player.skill.impl.Fishing;

/**
 * A class that holds various utility methods for managing a player's
 * {@link Skill}s.
 * 
 * @author lare96
 */
public final class Skills {

    /** A set of all of the skill events. */
    public static final Map<String, SkillEvent> SKILL_EVENTS = new HashMap<>();

    /** The indexes of the skills in the player's skill array. */
    public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2,
        HITPOINTS = 3, RANGED = 4, PRAYER = 5, MAGIC = 6, COOKING = 7,
        WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11,
        CRAFTING = 12, SMITHING = 13, MINING = 14, HERBLORE = 15, AGILITY = 16,
        THIEVING = 17, SLAYER = 18, FARMING = 19, RUNECRAFTING = 20;

    /**
     * The experience multiplier. All experience will be multiplied by this
     * number before being added to the player's skill.
     */
    public static final int EXP_RATE_MULTIPLIER = 1;

    /**
     * Loads all skills in the specified directory.
     * 
     * @throws Exception
     *             if any errors occur while loading skills.
     */
    public static void loadSkills() throws Exception {
        SKILL_EVENTS.put("Fishing", new Fishing());
    }

    /**
     * Adds the argued amount of experience to a certain {@link Skill} for an
     * {@link Player}. All experience added using this method is multiplied
     * using the <code>EXP_RATE_MULTIPLIER</code>.
     * 
     * @param player
     *            the player being granted the experience.
     * @param amount
     *            the amount of experience being given.
     * @param skill
     *            the skill this experience is being given to.
     */
    public static void experience(Player player, int amount, int skill) {

        // Get the level and experience before adding the experience.
        int oldLevel = player.getSkills()[skill].getLevelForExperience();
        int experience = player.getSkills()[skill].getExperience();

        // Multiply the argued experience and add it.
        amount *= Skills.EXP_RATE_MULTIPLIER;
        player.getSkills()[skill].setExperience(experience + amount);

        // Check if we are able to level up and do so if needed.
        if (!(oldLevel >= 99)) {
            int newLevel = player.getSkills()[skill].calculateLevelForExperience();

            if (oldLevel < newLevel) {
                if (skill != 3) {
                    player.getSkills()[skill].setLevel(newLevel, true);
                } else {
                    int old = player.getSkills()[skill].getLevel();

                    player.getSkills()[skill].setLevel(old + 1, true);
                }
                levelUp(player, SkillData.getSkill(skill), skill);
                player.graphic(new Graphic(199));
                player.getFlags().flag(Flag.APPEARANCE);
            }
        }

        // Refresh the skill once we're done.
        Skills.refresh(player, skill);
    }

    /**
     * Sent when a the player reaches a new skill level.
     * 
     * @param player
     *            the player leveling up.
     * @param skill
     *            the skill being advanced a level.
     * @param skillId
     *            the identification for the skill.
     */
    private static void levelUp(Player player, SkillData skill, int skillId) {

        // Send the player an indication that they have leveled up.
        player.getPacketBuilder().sendString(
            "@dre@Congratulations, you've just advanced " + Utility.appendIndefiniteArticle(skill.name().toLowerCase().replaceAll(
                "_", " ")) + " level!", skill.getFirstLine());
        player.getPacketBuilder().sendString(
            "Your " + skill.name().toLowerCase().replaceAll("_", " ") + " level is now " + player.getSkills()[skillId].getLevelForExperience() + ".",
            skill.getSecondLine());
        player.getPacketBuilder().sendMessage(
            "Congratulations, you've just advanced " + Utility.appendIndefiniteArticle(skill.name().toLowerCase().replaceAll(
                "_", " ")) + " level!");
        player.getPacketBuilder().sendChatInterface(skill.getChatbox());
    }

    /**
     * Refreshes the argued {@link Skill} for the argued {@link Player}.
     * 
     * @param player
     *            the player refreshing the skill.
     * @param skill
     *            the skill being refreshed.
     */
    public static void refresh(Player player, int skill) {

        // Get the instance of the skill.
        Skill s = player.getSkills()[skill];

        // If the skill doesn't exist, we create it.
        if (s == null) {
            s = new Skill();

            if (skill == Skills.HITPOINTS) {
                s.setLevel(10, true);
                s.setExperience(1300);
            }

            player.getSkills()[skill] = s;
        }

        // Send the skill data to the client.
        player.getPacketBuilder().sendSkill(skill, s.getLevel(),
            s.getExperience());
    }

    /**
     * Refreshes all of the argued {@link Player}'s skills.
     * 
     * @param player
     *            the player to refresh all skills for.
     */
    public static void refreshAll(Player player) {

        // Refresh all of the skills.
        for (int i = 0; i < player.getSkills().length; i++) {
            refresh(player, i);
        }
    }

    /**
     * Creates a completely new array of {@link Skill}s for the argued
     * {@link Player}.
     * 
     * @param player
     *            the player to create a new array of skills for.
     */
    public static void create(Player player) {

        // Loop through the array of skills.
        for (int i = 0; i < player.getSkills().length; i++) {

            // Create a new skill instance.
            player.getSkills()[i] = new Skill();

            // If the skill is hitpoints, set the level to 10.
            if (i == Skills.HITPOINTS) {
                player.getSkills()[i].setLevel(10, true);
                player.getSkills()[i].setRealLevel(10);
                player.getSkills()[i].setExperience(1300);
            }
        }
    }

    /**
     * Restores a certain skill back to its original level.
     * 
     * @param player
     *            the player to restore the skill for.
     * @param skill
     *            the skill to restore.
     */
    public static void restore(Player player, int skill) {

        // Restore it back to its original level.
        player.getSkills()[skill].setLevel(
            player.getSkills()[skill].getLevelForExperience(), true);

        // Refresh the skill after.
        refresh(player, skill);
    }

    /**
     * Restores all skills back to their original levels.
     * 
     * @param player
     *            the player to restore the skill for.
     */
    public static void restoreAll(Player player) {

        // Restore all of the skills.
        for (int i = 0; i < player.getSkills().length; i++) {
            restore(player, i);
        }
    }

    /**
     * Fires all skill events if needed.
     * 
     * @param player
     *            the player to fire skill events for.
     */
    public static final void fireSkillEvents(Player player) {

        // Iterate through the registered skills and fire events.
        for (SkillEvent skill : SKILL_EVENTS.values()) {
            if (skill.skill().getIndex() == -1)
                continue;

            if (player.getSkillEvent()[skill.skill().getIndex()]) {
                skill.stopSkill(player);
                player.getSkillEvent()[skill.skill().getIndex()] = false;
            }
        }
    }

    private Skills() {}
}
