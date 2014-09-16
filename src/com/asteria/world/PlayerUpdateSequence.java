package com.asteria.world;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

import com.asteria.engine.net.Session;
import com.asteria.world.entity.npc.NpcAggression;
import com.asteria.world.entity.npc.NpcUpdating;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.PlayerUpdating;

/**
 * A {@link WorldUpdateSequence} implementation for {@link Player}s that
 * provides code for each of the updating stages.
 * 
 * @author lare96
 */
public class PlayerUpdateSequence implements WorldUpdateSequence<Player> {

    /** Used to block the game thread until updating is completed. */
    private final Phaser synchronizer;

    /** The thread pool that will update players in parallel. */
    private final ExecutorService updateExecutor;

    /**
     * Create a new {@link PlayerUpdateSequence}.
     * 
     * @param synchronizer
     *            used to block the game thread until updating is completed.
     * @param updateExecutor
     *            the thread pool that will update players in parallel.
     */
    public PlayerUpdateSequence(Phaser synchronizer,
        ExecutorService updateExecutor) {
        this.synchronizer = synchronizer;
        this.updateExecutor = updateExecutor;
    }

    @Override
    public void executePreUpdate(Player t) {
        Session session = t.getSession();

        try {
            if (session.getTimeout().elapsed() > 5000) {
                session.disconnect();
                return;
            }

            NpcAggression.target(t);
            t.getMovementQueue().execute();
        } catch (Exception e) {
            e.printStackTrace();
            session.disconnect();
        }
    }

    @Override
    public void executeUpdate(Player t) {
        updateExecutor.execute(() -> {
            try {
                synchronized (t) {
                    PlayerUpdating.update(t);
                    NpcUpdating.update(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
                t.getSession().disconnect();
            } finally {
                synchronizer.arriveAndDeregister();
            }
        });
    }

    @Override
    public void executePostUpdate(Player t) {
        try {
            t.reset();
            t.setCachedUpdateBlock(null);
            t.getSession().resetPacketCount();
        } catch (Exception e) {
            e.printStackTrace();
            t.getSession().disconnect();
        }
    }
}
