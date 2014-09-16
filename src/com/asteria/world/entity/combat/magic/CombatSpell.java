package com.asteria.world.entity.combat.magic;

import java.util.Optional;

import com.asteria.engine.task.Task;
import com.asteria.engine.task.TaskManager;
import com.asteria.world.entity.Animation;
import com.asteria.world.entity.Entity;
import com.asteria.world.entity.Entity.EntityType;
import com.asteria.world.entity.Graphic;
import com.asteria.world.entity.Projectile;
import com.asteria.world.entity.Spell;
import com.asteria.world.entity.npc.Npc;

/**
 * A {@link Spell} implementation used for combat related spells.
 * 
 * @author lare96
 */
public abstract class CombatSpell extends Spell {

    @Override
    public void startCast(Entity cast, Entity castOn) {

        // First play the animation.
        if (cast.type() == EntityType.PLAYER) {
            castAnimation().ifPresent(cast::animation);
        } else {
            Npc npc = (Npc) cast;
            npc.animation(new Animation(
                npc.getDefinition().getAttackAnimation()));
        }

        // Then send the starting graphic.
        startGraphic().ifPresent(cast::graphic);

        // Finally send the projectile after two ticks.
        castProjectile(cast, castOn).ifPresent(g -> {
            TaskManager.submit(new Task(2, false) {
                @Override
                public void execute() {
                    g.sendProjectile();
                    this.cancel();
                }
            });
        });
    }

    /**
     * The fixed ID of the spell implementation as recognized by the protocol.
     * 
     * @return the ID of the spell, or <tt>-1</tt> if there is no ID for this
     *         spell.
     */
    public abstract int spellId();

    /**
     * The maximum hit an {@link Entity} can deal with this spell.
     * 
     * @return the maximum hit able to be dealt with this spell implementation.
     */
    public abstract int maximumHit();

    /**
     * The animation played when the spell is cast.
     * 
     * @return the animation played when the spell is cast.
     */
    public abstract Optional<Animation> castAnimation();

    /**
     * The starting graphic played when the spell is cast.
     * 
     * @return the starting graphic played when the spell is cast.
     */
    public abstract Optional<Graphic> startGraphic();

    /**
     * The projectile played when this spell is cast.
     * 
     * @param cast
     *            the entity casting the spell.
     * @param castOn
     *            the entity targeted by the spell.
     * 
     * @return the projectile played when this spell is cast.
     */
    public abstract Optional<Projectile> castProjectile(Entity cast,
        Entity castOn);

    /**
     * The ending graphic played when the spell hits the victim.
     * 
     * @return the ending graphic played when the spell hits the victim.
     */
    public abstract Optional<Graphic> endGraphic();

    /**
     * Fired when the spell hits the victim.
     * 
     * @param cast
     *            the entity casting the spell.
     * @param castOn
     *            the entity targeted by the spell.
     * @param accurate
     *            if the spell was accurate.
     * @param damage
     *            the amount of damage inflicted by this spell.
     */
    public abstract void finishCast(Entity cast, Entity castOn,
        boolean accurate, int damage);
}
