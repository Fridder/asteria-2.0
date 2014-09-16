package com.asteria.world.entity.player.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.asteria.util.JsonLoader;
import com.asteria.world.entity.player.Player;
import com.asteria.world.item.Item;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * A static utility class that contains methods for changing the appearance
 * animation for a player whenever a new weapon is equipped or an existing item
 * is unequipped.
 * 
 * @author lare96
 */
public final class WeaponAnimations {

    /** A map of items and their respective animations. */
    private static Map<Integer, WeaponAnimation> animations = new HashMap<>(100);

    /**
     * Executes an animation for the argued player based on the animation of the
     * argued item.
     * 
     * @param player
     *            the player to animate.
     * @param item
     *            the item to get the animations for.
     */
    public static void assign(Player player, Item item) {
        if (Objects.nonNull(item)) {
            player.getUpdateAnimation().reset();
            WeaponAnimation animation = animations.get(item.getId());

            if (Objects.nonNull(animation)) {
                player.getUpdateAnimation().runningAnimation = animation.runningAnimation;
                player.getUpdateAnimation().walkingAnimation = animation.walkingAnimation;
                player.getUpdateAnimation().standingAnimation = animation.standingAnimation;
            }
        }
    }

    /**
     * Prepares the dynamic json loader for loading weapon animations.
     * 
     * @return the dynamic json loader.
     * @throws Exception
     *             if any errors occur while preparing for load.
     */
    public static JsonLoader parseAnimations() throws Exception {
        return new JsonLoader() {
            @Override
            public void load(JsonObject reader, Gson builder) {
                int id = reader.get("item-id").getAsInt();
                WeaponAnimation animation = builder.fromJson(
                    reader.get("animation"), WeaponAnimation.class);
                animations.put(id, animation);
            }

            @Override
            public String filePath() {
                return "./data/json/equipment/weapon_animations.json";
            }
        };
    }

    /**
     * A container class containing the three weapon animation types.
     * 
     * @author lare96
     */
    public static class WeaponAnimation {

        /** The standing animation for this player. */
        private int standingAnimation = -1;

        /** The walking animation for this player. */
        private int walkingAnimation = -1;

        /** The running animation for this player. */
        private int runningAnimation = -1;

        /**
         * Create a new {@link WeaponAnimation}.
         * 
         * @param standingAnimation
         *            the standing animation for this player.
         * @param walkingAnimation
         *            the walking animation for this player.
         * @param runningAnimation
         *            the running animation for this player.
         */
        public WeaponAnimation(int standingAnimation, int walkingAnimation,
            int runningAnimation) {
            this.standingAnimation = standingAnimation;
            this.walkingAnimation = walkingAnimation;
            this.runningAnimation = runningAnimation;
        }

        /**
         * Create a new {@link WeaponAnimation}.
         */
        public WeaponAnimation() {

        }

        @Override
        public WeaponAnimation clone() {
            WeaponAnimation wep = new WeaponAnimation();
            wep.standingAnimation = standingAnimation;
            wep.walkingAnimation = walkingAnimation;
            wep.runningAnimation = runningAnimation;
            return wep;
        }

        @Override
        public String toString() {
            return "WEAPON ANIMATION[standing= " + standingAnimation + ", walking= " + walkingAnimation + ", running= " + runningAnimation + "]";
        }

        /**
         * Resets the animation indexes.
         */
        public void reset() {
            standingAnimation = -1;
            walkingAnimation = -1;
            runningAnimation = -1;
        }

        /**
         * Gets the standing animation for this player.
         * 
         * @return the standing animation.
         */
        public int getStandingAnimation() {
            return standingAnimation;
        }

        /**
         * Gets the walking animation for this player.
         * 
         * @return the walking animation.
         */
        public int getWalkingAnimation() {
            return walkingAnimation;
        }

        /**
         * Gets the running animation for this player.
         * 
         * @return the running animation.
         */
        public int getRunningAnimation() {
            return runningAnimation;
        }

        /**
         * Sets the standing animation for this player.
         * 
         * @param standingAnimation
         *            the new standing animation to set.
         */
        public void setStandingAnimation(int standingAnimation) {
            this.standingAnimation = standingAnimation;
        }

        /**
         * Sets the walking animation for this player.
         * 
         * @param walkingAnimation
         *            the new walking animation to set.
         */
        public void setWalkingAnimation(int walkingAnimation) {
            this.walkingAnimation = walkingAnimation;
        }

        /**
         * Sets the running animation for this player.
         * 
         * @param runningAnimation
         *            the new running animation to set.
         */
        public void setRunningAnimation(int runningAnimation) {
            this.runningAnimation = runningAnimation;
        }
    }

    private WeaponAnimations() {}
}
