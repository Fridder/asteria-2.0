package com.asteria.world.entity.player.skill.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.asteria.engine.task.Task;
import com.asteria.util.Utility;
import com.asteria.world.entity.Animation;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.skill.SkillData;
import com.asteria.world.entity.player.skill.Skills;
import com.asteria.world.entity.player.skill.event.SkillAnimationTask;
import com.asteria.world.entity.player.skill.event.SkillRepeatingAction;
import com.asteria.world.item.Item;
import com.asteria.world.map.Location;

/**
 * A {@link SkillRepeatingAction} extension and {@link SkillAnimationTask}
 * implementation that handles the entire <code>FISHING</code> skill.
 * 
 * @author lare96
 */
public class Fishing extends SkillRepeatingAction implements SkillAnimationTask {

    // Has support for pretty much everything you need: able to fish all fish
    // with correct experience and levels (obviously), rare caskets with items,
    // fishing multiple fish with a big net, and even has support for fishing
    // certain fish that only apply to some sort of conditions! You'll have to
    // spawn the fishing spots by yourself though.

    /** All of the items that can be obtained from a casket. */
    public static final Item[] CASKET_ITEMS = { new Item(1061), new Item(592),
            new Item(1059), new Item(995, 100000), new Item(4212),
            new Item(995, 50000), new Item(401), new Item(995, 150000),
            new Item(407) };

    @Override
    public void executeAction(Player player, Task task) {

        Tool tool = player.getFishingTool();

        // Cancel the task if we don't have the correct tools.
        if (!player.getInventory().contains(tool.getId())) {
            player.getPacketBuilder().sendMessage(
                "You do not have the correct tool to fish here!");
            task.cancel();
            return;
        }

        // Cancel if we don't have the required fishing bait.
        if (tool.getNeeded() > 0) {
            if (!player.getInventory().contains(tool.getNeeded())) {
                player.getPacketBuilder().sendMessage(
                    "You do not have enough bait for the fish.");
                task.cancel();
                return;
            }
        }

        // Cancel if we don't have enough inventory space.
        if (player.getInventory().getRemainingSlots() < 1) {
            player.getPacketBuilder().sendMessage(
                "You do not have any space left in your inventory.");
            task.cancel();
            return;
        }

        // Cancel if we aren't a high enough level. */
        if (!player.getSkills()[Skills.FISHING].reqLevel(tool.getLevel())) {
            player.getPacketBuilder().sendMessage(
                "You must have a fishing level of " + tool.getLevel() + " to fish with this.");
            task.cancel();
            return;
        }

        // Random stop for that 'old school' runescape feel.
        if (Utility.RANDOM.nextInt(50) == 0) {
            task.cancel();
            return;
        }

        // Execute the code for the fishing tool.
        tool.onFish(player);
    }

    @Override
    public int actionInterval(Player player) {

        // Obviously here we just get the fishing time from the designated
        // method.
        return getFishingTime(player);
    }

    @Override
    public boolean initialExecute(Player player) {

        // We return 'false' because we DON'T want the task to execute first.
        // This is because in fishing you do not immediately get a fish when you
        // start fishing.
        return false;
    }

    @Override
    public boolean executeIf(Player player) {

        Tool tool = player.getFishingTool();

        // Don't fish if movement is locked.
        if (player.getMovementQueue().isLockMovement()) {
            return false;
        }

        // Don't fish if we don't have the correct tool.
        if (!player.getInventory().contains(tool.getId())) {
            player.getPacketBuilder().sendMessage(
                "You do not have the correct tool to fish here!");
            return false;
        }

        // Don't fish if we don't have the correct bait.
        if (tool.getNeeded() > 0) {
            if (!player.getInventory().contains(tool.getNeeded())) {
                player.getPacketBuilder().sendMessage(
                    "You do not have enough bait for the fish.");
                return false;
            }
        }

        // Don't fish if we don't have enough inventory space.
        if (player.getInventory().getRemainingSlots() < 1) {
            player.getPacketBuilder().sendMessage(
                "You do not have any space left in your inventory.");
            return false;
        }

        // Don't fish if we aren't a high enough level to use the tool.
        if (!player.getSkills()[Skills.FISHING].reqLevel(tool.getLevel())) {
            player.getPacketBuilder().sendMessage(
                "You must have a fishing level of " + tool.getLevel() + " to fish with this.");
            return false;
        }

        // Passed all the checks, prepare the player for fishing.
        player.getPacketBuilder().sendMessage("You begin to fish...");
        player.animation(new Animation(tool.getAnimation()));
        player.getMovementQueue().reset();
        return true;
    }

    @Override
    public Animation animation(Player player) {

        // We get the fishing animation from the fishing tool instance.
        return new Animation(player.getFishingTool().getAnimation());
    }

    @Override
    public int animationDelay() {

        // The fishing animation delay, 4 ticks is about right.
        return 4;
    }

    @Override
    public Optional<SkillAnimationTask> animationTask() {

        // Return 'this' because we're implementing 'SkillAnimationTask'. If the
        // skill doesn't have an animation, return 'Optional.empty()'. DO
        // NOT RETURN 'null' OR IT WILL NOT WORK.
        return Optional.of(this);
    }

    @Override
    public SkillData skill() {

        // Obviously here we just return the skill data constant that
        // corresponds to this class.
        return SkillData.FISHING;
    }

    /**
     * Determines the fish that will be caught based on the fishing tool and the
     * rarity of the fish.
     * 
     * @param player
     *            the player that will catch the fish.
     * @return the fish that was caught, will never be <code>null</code>.
     */
    private static Fish determineFish(Player player) {

        // Construct a dummy list to hold data and dump values into it.
        ArrayList<Fish> list = new ArrayList<>();
        Arrays.stream(player.getFishingTool().getFish()).filter(
            f -> f.getLevel() <= player.getSkills()[Skills.FISHING].getLevel() && f.isCatchable(player)).forEach(
            list::add);

        // Shuffle the list before using it so its completely random, we also
        // calculate the percentage gamble from now.
        double d = Math.round(Utility.RANDOM.nextDouble() * 100.0) / 100.0;
        Collections.shuffle(list, Utility.RANDOM);

        // Do comparisons to see if we can determine a fish, if not then return
        // the default fish.
        Optional<Fish> found = list.stream().filter(f -> f.getChance() >= d).findFirst();
        return found.orElse(player.getFishingTool().defaultFish());
    }

    /**
     * Calculates the fishing time for the argued player based on their fishing
     * level and the current tool they have.
     * 
     * @param player
     *            the player the fishing time is being calculated for.
     * @return the calculated fishing time, in ticks.
     */
    private static int getFishingTime(Player player) {

        // Here we the fishing time in ticks based on level and the fishing
        // tool.
        int calc = (10 - (int) Math.floor(player.getSkills()[Skills.FISHING].getLevel() / 10));
        return player.getFishingTool().getSpeed() + calc;
    }

    /**
     * An enumeration managing all tools that can be used to fish.
     * 
     * @author lare96
     */
    public enum Tool {
        NET(303, 1, -1, 3, 621, Fish.SHRIMP, Fish.ANCHOVY) {
            @Override
            public Fish defaultFish() {
                return Fish.SHRIMP;
            }
        },
        BIG_NET(305, 16, -1, 3, 620, Fish.MACKEREL, Fish.OYSTER, Fish.COD, Fish.BASS, Fish.CASKET) {
            @Override
            public void onFish(Player player) {

                // We need this 'onFish' implementation so we can catch multiple
                // fish with a a big net.
                int amount = Utility.inclusiveRandom(1, 4);
                int freeSlots = player.getInventory().getRemainingSlots();

                if (!(freeSlots >= amount))
                    amount = freeSlots;

                for (int i = 0; i < amount; i++) {
                    Fish f = determineFish(player);

                    player.getPacketBuilder().sendMessage(
                        "You catch " + Utility.appendIndefiniteArticle(f.getName()) + ".");
                    player.getInventory().add(new Item(f.getId()));
                    Skills.experience(player, f.getExperience(), Skills.FISHING);
                }
            }

            @Override
            public Fish defaultFish() {
                return Fish.MACKEREL;
            }
        },
        FISHING_ROD(307, 5, 313, 2, 622, Fish.SARDINE, Fish.HERRING, Fish.PIKE, Fish.SLIMY_EEL, Fish.CAVE_EEL, Fish.LAVA_EEL) {
            @Override
            public Fish defaultFish() {
                return Fish.SARDINE;
            }
        },
        FLY_FISHING_ROD(309, 20, 314, 1, 622, Fish.TROUT, Fish.SALMON) {
            @Override
            public Fish defaultFish() {
                return Fish.TROUT;
            }
        },
        HARPOON(311, 35, -1, 5, 618, Fish.TUNA, Fish.SWORDFISH) {
            @Override
            public Fish defaultFish() {
                return Fish.TUNA;
            }
        },
        SHARK_HARPOON(311, 76, -1, 6, 618, Fish.SHARK) {
            @Override
            public Fish defaultFish() {
                return Fish.SHARK;
            }
        },
        LOBSTER_POT(301, 40, -1, 4, 619, Fish.LOBSTER) {
            @Override
            public Fish defaultFish() {
                return Fish.LOBSTER;
            }
        };

        /** The item id of the tool. */
        private int id;

        /** The level you need to be to use this tool. */
        private int level;

        /** The id of an item needed to use this tool. */
        private int needed;

        /** The speed of this tool. */
        private int speed;

        /** The animation performed when using this tool. */
        private int animation;

        /** All of the fish you can catch with this tool. */
        private Fish[] fish;

        /**
         * Creates a new {@link Tool}.
         * 
         * @param id
         *            the item id of the tool.
         * @param level
         *            the level you need to be to use this tool.
         * @param needed
         *            the id of an item needed to use this tool.
         * @param speed
         *            the speed of this tool.
         * @param animation
         *            the animation performed when using this tool.
         * @param fish
         *            the fish you can catch with this tool.
         */
        private Tool(int id, int level, int needed, int speed, int animation,
            Fish... fish) {
            this.id = id;
            this.level = level;
            this.needed = needed;
            this.speed = speed;
            this.animation = animation;
            this.fish = fish;
        }

        /**
         * A dynamic method that determines what happens when any fish is caught
         * with this tool.
         * 
         * @param player
         *            the player that caught the fish.
         * @param fish
         *            the fish that was caught.
         */
        public void onFish(Player player) {

            // The normal fishing implementation, determine a fish then catch
            // it.
            Fish f = determineFish(player);
            player.getPacketBuilder().sendMessage(
                "You catch " + Utility.appendIndefiniteArticle(f.getName()) + ".");
            player.getInventory().add(new Item(f.getId()));
            Skills.experience(player, f.getExperience(), Skills.FISHING);

            if (needed > 0)
                player.getInventory().remove(new Item(needed));
        }

        /**
         * The default fish that will be chosen if a fish has still not been
         * determined after calculations have completed.
         * 
         * @return the default fish implementation.
         */
        public abstract Fish defaultFish();

        /**
         * Gets the item id of this tool.
         * 
         * @return the item id.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the level you need to be to use this tool.
         * 
         * @return the level needed.
         */
        public int getLevel() {
            return level;
        }

        /**
         * Gets the id of an item needed to use this tool.
         * 
         * @return the item needed.
         */
        public int getNeeded() {
            return needed;
        }

        /**
         * Gets the speed of this tool.
         * 
         * @return the speed.
         */
        public int getSpeed() {
            return speed;
        }

        /**
         * Gets the animation performed when using this tool.
         * 
         * @return the animation.
         */
        public int getAnimation() {
            return animation;
        }

        /**
         * Gets the fish you can catch with this tool.
         * 
         * @return the fish available.
         */
        public Fish[] getFish() {
            return fish;
        }

    }

    /**
     * An enumeration managing all of the fish that can be caught while fishing.
     * 
     * @author lare96
     */
    private enum Fish {
        SHRIMP(317, 1, 0.85, 10),
        SARDINE(327, 5, 0.8, 20),
        HERRING(345, 10, 0.85, 30),
        ANCHOVY(321, 15, 0.45, 40),
        MACKEREL(353, 16, 0.9, 20),
        CASKET(405, 16, 0.01, 100),
        OYSTER(407, 16, 0.05, 80),
        TROUT(335, 20, 0.95, 50),
        COD(341, 23, 0.9, 45),
        PIKE(349, 25, 0.9, 60),
        SLIMY_EEL(3379, 28, 0.05, 65) {
            @Override
            public boolean isCatchable(Player player) {

                // We can only catch this fish if we are fishing from the
                // wilderness.
                return player.getPosition().inLocation(Location.WILDERNESS,
                    true);
            }
        },
        SALMON(331, 30, 0.75, 70),
        TUNA(359, 35, 0.95, 80),
        CAVE_EEL(5001, 38, 0.07, 80) {
            @Override
            public boolean isCatchable(Player player) {

                // We can only catch this fish if we are fishing from the
                // wilderness.
                return player.getPosition().inLocation(Location.WILDERNESS,
                    true);
            }
        },
        LOBSTER(377, 40, 0.85, 90),
        BASS(363, 46, 0.5, 100),
        SWORDFISH(371, 50, 0.75, 100),
        LAVA_EEL(2148, 53, 0.85, 60) {
            @Override
            public boolean isCatchable(Player player) {

                // We can only catch this fish if we are fishing from the
                // wilderness.
                return player.getPosition().inLocation(Location.WILDERNESS,
                    true);
            }
        },
        SHARK(383, 76, 0.7, 110);

        /** The item id of the fish. */
        private int id;

        /** The level needed to be able to catch the fish. */
        private int level;

        /** The chance of catching this fish (when grouped with other fishes). */
        private double chance;

        /** The experience gained from catching this fish. */
        private int experience;

        /**
         * Creates a new {@link Fish}.
         * 
         * @param id
         *            the item id of the fish.
         * @param level
         *            the level needed to be able to catch the fish.
         * @param chance
         *            the chance of catching this fish (when grouped with other
         *            fishes).
         * @param experience
         *            the experience gained from catching this fish.
         */
        private Fish(int id, int level, double chance, int experience) {
            this.id = id;
            this.level = level;
            this.chance = chance;
            this.experience = experience;
        }

        /**
         * A dynamic method that determines if the argued player can catch this
         * fish.
         * 
         * @param player
         *            the player that is trying to catch this fish.
         * 
         * @return <code>true</code> if this fish can be caught,
         *         <code>false</code> otherwise.
         */
        public boolean isCatchable(Player player) {
            return true;
        }

        /**
         * Gets the item id of the fish.
         * 
         * @return the item id.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the level needed to be able to catch the fish.
         * 
         * @return the level.
         */
        public int getLevel() {
            return level;
        }

        /**
         * Gets the chance of catching this fish (when grouped with other
         * fishes).
         * 
         * @return the chance.
         */
        public double getChance() {
            return chance;
        }

        /**
         * Gets the experience gained from catching this fish.
         * 
         * @return the experience.
         */
        public int getExperience() {
            return experience;
        }

        /**
         * Gets a presentable formatted version of this constant's name.
         * 
         * @return the formatted version of this contant's name.
         */
        public String getName() {
            return Utility.capitalize(name().toLowerCase().replaceAll("_", " "));
        }
    }
}
