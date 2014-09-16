package com.asteria.world;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import com.asteria.engine.BlockingThreadPool;
import com.asteria.engine.GameEngine;
import com.asteria.engine.net.Session.Stage;
import com.asteria.world.entity.EntityContainer;
import com.asteria.world.entity.npc.Npc;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.WritePlayerFileTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Updates all in-game entities, and also contains utility methods to manage
 * various aspects of the world.
 * 
 * @author lare96
 */
public final class World {

    /** All of the registered players. */
    private static EntityContainer<Player> players = new EntityContainer<>(1000);

    /** All of the registered NPCs. */
    private static EntityContainer<Npc> npcs = new EntityContainer<>(1500);

    /** Used to block the game thread until updating has completed. */
    private static Phaser synchronizer = new Phaser(1);

    /** A thread pool that will update players in parallel. */
    private static ExecutorService updateExecutor = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors(),
        new ThreadFactoryBuilder().setNameFormat("UpdateThread").setPriority(
            Thread.MAX_PRIORITY).build());

    /**
     * The default constructor, will throw an
     * {@link UnsupportedOperationException} if instantiated.
     */
    private World() {
        throw new UnsupportedOperationException(
            "This class cannot be instantiated!");
    }

    /**
     * The method that executes code for all in game entities every <tt>600</tt>
     * ms. Updating is parallelized using the {@link #updateExecutor} to execute
     * the code concurrently and the {@link #synchronizer} to block the game
     * thread until it's finished.
     */
    public static void tick() {
        try {

            // First we construct the update sequences.
            WorldUpdateSequence<Player> playerUpdate = new PlayerUpdateSequence(
                synchronizer, updateExecutor);
            WorldUpdateSequence<Npc> npcUpdate = new NpcUpdateSequence();

            // Then we execute pre-updating code.
            players.forEach(playerUpdate::executePreUpdate);
            npcs.forEach(npcUpdate::executePreUpdate);

            // Then we execute parallelized updating code.
            synchronizer.bulkRegister(players.size());
            players.forEach(playerUpdate::executeUpdate);
            synchronizer.arriveAndAwaitAdvance();

            // Then we execute post-updating code.
            players.forEach(playerUpdate::executePostUpdate);
            npcs.forEach(npcUpdate::executePostUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            savePlayers();
        }
    }

    /**
     * Returns an instance of a {@link Player} object for the specified username
     * hash.
     * 
     * @param hash
     *            The username hash.
     * @return The <code>Player</code> object representing the player or
     *         {@code null} if no such player exists.
     */
    public static Optional<Player> getPlayerByHash(long hash) {
        return players.search(p -> p != null && p.getUsernameHash() == hash);
    }

    /**
     * Returns an instance of a {@link Player} object for the specified
     * username.
     * 
     * @param username
     *            The username.
     * @return The <code>Player</code> object representing the player or
     *         {@code null} if no such player exists.
     */
    public static Optional<Player> getPlayerByName(String username) {
        return players.search(p -> p != null && p.getUsername().equals(username));
    }

    /**
     * Sends a message to all online {@link Player}s.
     * 
     * @param message
     *            the message to send that will be sent to everyone online.
     */
    public void sendMessage(String message) {
        players.forEach(p -> p.getPacketBuilder().sendMessage(message));
    }

    /** Saves the game for all players that are currently online. */
    public static void savePlayers() {
        players.forEach(World::savePlayer);
    }

    /** Performs a series of operations that shut the entire server down. */
    public static void shutdown() {
        try {

            // First save all players, we block the calling thread until all
            // players are saved.
            BlockingThreadPool pool = new BlockingThreadPool();
            players.forEach(p -> pool.append(new WritePlayerFileTask(p)));
            pool.awaitCompletion();

            // Terminate any thread pools.
            updateExecutor.shutdown();
            GameEngine.getServiceExecutor().shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Exit regardless if there was an error or not during shutdown.
            System.exit(1);
        }
    }

    /**
     * Saves the game for a single player.
     * 
     * @param player
     *            the player to save the game for.
     */
    public static void savePlayer(Player player) {

        // Don't save if we aren't logged in.
        if (player.getSession().getStage() != Stage.LOGGED_IN) {
            return;
        }

        // Push the save task to the sequential pool.
        GameEngine.getServiceExecutor().execute(new WritePlayerFileTask(player));
    }

    /**
     * Gets the container of players.
     * 
     * @return the container of players.
     */
    public static EntityContainer<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the container of npcs.
     * 
     * @return the container of npcs.
     */
    public static EntityContainer<Npc> getNpcs() {
        return npcs;
    }
}
