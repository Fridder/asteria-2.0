package com.asteria.world.entity.npc;

import java.util.Optional;

import com.asteria.engine.task.Task;
import com.asteria.engine.task.TaskManager;
import com.asteria.world.World;
import com.asteria.world.entity.Animation;
import com.asteria.world.entity.EntityDeath;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.minigame.Minigames;
import com.asteria.world.item.Item;
import com.asteria.world.item.ground.GroundItem;
import com.asteria.world.item.ground.GroundItem.StaticGroundItem;
import com.asteria.world.item.ground.GroundItemManager;
import com.asteria.world.map.Position;

/**
 * Handles the death process for all {@link Npc}s.
 * 
 * @author lare96
 */
public class NpcDeath extends EntityDeath<Npc> {

    /**
     * Create a new {@link NpcDeath}.
     * 
     * @param npc
     *            the npc that needs to be taken through the death stages.
     */
    public NpcDeath(Npc npc) {
        super(npc);
    }

    @Override
    public void preDeath(Npc entity) {

        // Here we do the death animation.
        entity.animation(new Animation(
            entity.getDefinition().getDeathAnimation()));
    }

    @Override
    public void death(Npc entity) {

        // We find who killed this npc, and get the drop table.
        Optional<Player> killer = entity.getCombatBuilder().getKiller(true);
        NpcDropTable table = NpcDropTable.getDrops().get(entity.getNpcId());

        // If here is no table we skip this stage.
        if (table != null) {

            // Calculate the items that will be dropped.
            Item[] dropItems = table.calculateDrops(killer.orElse(null));

            // Then we drop all of those items.
            for (Item drop : dropItems) {
                if (drop == null) {
                    continue;
                }

                GroundItemManager.register(!killer.isPresent() ? new StaticGroundItem(
                    drop, entity.getPosition())
                    : new GroundItem(drop, entity.getPosition(), killer.get()));
            }

            // Fire any minigame events.
            killer.ifPresent(k -> Minigames.get(k).ifPresent(
                m -> m.fireOnKill(k, entity)));
        }

        // Then finally we unregister the npc.
        entity.getPosition().setAs(new Position(1, 1));
        World.getNpcs().remove(entity);
    }

    @Override
    public void postDeath(Npc entity) {

        // And spawn it back if needed!
        if (entity.isRespawn()) {
            TaskManager.submit(new Task(entity.getRespawnTime(), false) {
                @Override
                public void execute() {
                    Npc npc = new Npc(entity.getNpcId(),
                        entity.getOriginalPosition());
                    npc.setRespawn(true);
                    World.getNpcs().add(npc);
                    this.cancel();
                }
            });
        }
    }
}
