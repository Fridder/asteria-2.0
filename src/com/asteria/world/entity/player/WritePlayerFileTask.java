package com.asteria.world.entity.player;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.asteria.engine.GameEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * A task executed by the {@link GameEngine}'s sequential thread pool that will
 * save the player's character file.
 * 
 * @author lare96
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public class WritePlayerFileTask implements Runnable {

    /** A {@link Logger} for printing debugging info. */
    private static Logger logger = Logger.getLogger(WritePlayerFileTask.class.getSimpleName());

    /** The player who's file will be saved. */
    private final Player player;

    /**
     * Create a new {@link WritePlayerFileTask}.
     * 
     * @param player
     *            the player who's file will be saved.
     */
    public WritePlayerFileTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {

        // Put a concurrent lock on the player just in case they are
        // still being modified.
        synchronized (player) {

            // Create the path and file objects.
            Path path = Paths.get("./data/players/",
                player.getUsername() + ".json");
            File file = path.toFile();
            file.getParentFile().setWritable(true);

            // Attempt to make the player save directory if it doesn't
            // exist.
            if (!file.getParentFile().exists()) {
                try {
                    file.getParentFile().mkdirs();
                } catch (SecurityException e) {
                    logger.log(Level.SEVERE,
                        "Unable to create directory for player data!", e);
                }
            }

            try (FileWriter writer = new FileWriter(file)) {

                // Now add the properties to the json parser.
                Gson builder = new GsonBuilder().setPrettyPrinting().create();
                JsonObject object = new JsonObject();

                object.addProperty("username", player.getUsername().trim());
                object.addProperty("password", player.getPassword().trim());
                object.add("position", builder.toJsonTree(player.getPosition()));
                object.addProperty("staff-rights", player.getRights().name());
                object.addProperty("gender", new Integer(player.getGender()));
                object.add("appearance",
                    builder.toJsonTree(player.getAppearance()));
                object.add("colors", builder.toJsonTree(player.getColors()));
                object.addProperty("run-toggled", new Boolean(
                    player.getMovementQueue().isRunToggled()));
                object.addProperty("new-player", new Boolean(
                    player.isNewPlayer()));
                object.add("inventory",
                    builder.toJsonTree(player.getInventory().toArray()));
                object.add("bank",
                    builder.toJsonTree(player.getBank().toArray()));
                object.add("equipment",
                    builder.toJsonTree(player.getEquipment().toArray()));
                object.add("skills", builder.toJsonTree(player.getSkills()));
                object.add("friends",
                    builder.toJsonTree(player.getFriends().toArray()));
                object.add("ignores",
                    builder.toJsonTree(player.getIgnores().toArray()));
                object.addProperty("run-energy", new Integer(
                    player.getRunEnergy()));
                object.addProperty("spell-book", player.getSpellbook().name());
                object.addProperty("is-banned", new Boolean(player.isBanned()));
                object.addProperty("auto-retaliate", new Boolean(
                    player.isAutoRetaliate()));
                object.addProperty("fight-type", player.getFightType().name());
                object.addProperty("skull-timer", new Integer(
                    player.getSkullTimer()));
                object.addProperty("accept-aid", new Boolean(
                    player.isAcceptAid()));
                object.addProperty("poison-damage", new Integer(
                    player.getPoisonDamage()));
                object.addProperty("teleblock-timer", new Integer(
                    player.getTeleblockTimer()));
                object.addProperty("special-amount", new Integer(
                    player.getSpecialPercentage()));

                // And write the data to the character file!
                writer.write(builder.toJson(object));

                // And print an indication that we've saved it.
                logger.info(player + " game successfully saved!");
            } catch (Exception e) {

                // An error happened while saving.
                logger.log(Level.WARNING,
                    "An error has occured while saving a character file!", e);
            }
        }
    }
}
