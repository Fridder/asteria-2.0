package com.asteria.world.entity.player;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import com.asteria.util.Utility;
import com.asteria.world.entity.combat.weapon.FightType;
import com.asteria.world.entity.player.content.Spellbook;
import com.asteria.world.entity.player.skill.Skill;
import com.asteria.world.entity.player.skill.Skills;
import com.asteria.world.item.Item;
import com.asteria.world.map.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A result-bearing task executed on the main game thread that will load the
 * player's character file.
 * 
 * @author lare96
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public class ReadPlayerFileTask implements Callable<Integer> {

    /** A {@link Logger} for printing debugging info. */
    private static Logger logger = Logger.getLogger(ReadPlayerFileTask.class.getSimpleName());

    /** The player who's file will be written to. */
    private final Player player;

    /**
     * Create a new {@link ReadPlayerFileTask}.
     * 
     * @param player
     *            the player who's file will be written to.
     */
    public ReadPlayerFileTask(Player player) {
        this.player = player;
    }

    @Override
    public Integer call() {

        // Create the path and file objects.
        Path path = Paths.get("data/players", player.getUsername() + ".json");
        File file = path.toFile();

        // If the file doesn't exist, we're logging in for the first
        // time and can skip all of this.
        if (!file.exists()) {
            Skills.create(player);
            logger.info(player + " is logging in for the first time!");
            return Utility.LOGIN_RESPONSE_OK;
        }

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().create();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);

            if (reader.has("username")) {
                player.setUsername(reader.get("username").getAsString());
            }
            if (reader.has("password")) {
                String password = reader.get("password").getAsString();
                if (!player.getPassword().equals(password)) {
                    return Utility.LOGIN_RESPONSE_INVALID_CREDENTIALS;
                }

                player.setPassword(password);
            }
            if (reader.has("position")) {
                player.getPosition().setAs(
                    builder.fromJson(reader.get("position"), Position.class));
            }
            if (reader.has("staff-rights")) {
                player.setRights(PlayerRights.valueOf(reader.get("staff-rights").getAsString()));
            }
            if (reader.has("gender")) {
                player.setGender(reader.get("gender").getAsInt());
            }
            if (reader.has("appearance")) {
                player.setAppearance(builder.fromJson(
                    reader.get("appearance").getAsJsonArray(), int[].class));
            }
            if (reader.has("colors")) {
                player.setColors(builder.fromJson(
                    reader.get("colors").getAsJsonArray(), int[].class));
            }
            if (reader.has("run-toggled")) {
                player.getMovementQueue().setRunToggled(
                    reader.get("run-toggled").getAsBoolean());
            }
            if (reader.has("new-player")) {
                player.setNewPlayer(reader.get("new-player").getAsBoolean());
            }
            if (reader.has("inventory")) {
                player.getInventory()

                .setItems(
                    builder.fromJson(reader.get("inventory").getAsJsonArray(),
                        Item[].class));

            }
            if (reader.has("bank")) {
                player.getBank()

                .setItems(
                    builder.fromJson(reader.get("bank").getAsJsonArray(),
                        Item[].class));
            }
            if (reader.has("equipment")) {
                player.getEquipment()

                .setItems(
                    builder.fromJson(reader.get("equipment").getAsJsonArray(),
                        Item[].class));
            }
            if (reader.has("skills")) {
                player.setSkills(builder.fromJson(
                    reader.get("skills").getAsJsonArray(), Skill[].class));
            }
            if (reader.has("friends")) {
                long[] friends = builder.fromJson(
                    reader.get("friends").getAsJsonArray(), long[].class);

                for (long l : friends) {
                    player.getFriends().add(l);
                }
            }
            if (reader.has("ignores")) {
                long[] ignores = builder.fromJson(
                    reader.get("ignores").getAsJsonArray(), long[].class);

                for (long l : ignores) {
                    player.getIgnores().add(l);
                }
            }
            if (reader.has("run-energy")) {
                player.setRunEnergy(reader.get("run-energy").getAsInt());
            }
            if (reader.has("spell-book")) {
                player.setSpellbook(Spellbook.valueOf(reader.get("spell-book").getAsString()));
            }
            if (reader.has("is-banned")) {
                boolean banned = reader.get("is-banned").getAsBoolean();

                if (banned) {
                    return Utility.LOGIN_RESPONSE_ACCOUNT_DISABLED;
                }
                player.setBanned(banned);
            }
            if (reader.has("auto-retaliate")) {
                player.setAutoRetaliate(reader.get("auto-retaliate").getAsBoolean());
            }
            if (reader.has("fight-type")) {
                player.setFightType(FightType.valueOf(reader.get("fight-type").getAsString()));
            }
            if (reader.has("skull-timer")) {
                player.setSkullTimer(reader.get("skull-timer").getAsInt());
            }
            if (reader.has("accept-aid")) {
                player.setAcceptAid(reader.get("accept-aid").getAsBoolean());
            }
            if (reader.has("poison-damage")) {
                player.setPoisonDamage(reader.get("poison-damage").getAsInt());
            }
            if (reader.has("teleblock-timer")) {
                player.setTeleblockTimer(reader.get("teleblock-timer").getAsInt());
            }
            if (reader.has("special-amount")) {
                player.setSpecialPercentage(reader.get("special-amount").getAsInt());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utility.LOGIN_RESPONSE_COULD_NOT_COMPLETE_LOGIN;
        }
        return Utility.LOGIN_RESPONSE_OK;
    }
}