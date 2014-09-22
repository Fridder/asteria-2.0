package com.asteria.world.entity.combat.weapon;

import com.asteria.world.entity.combat.CombatType;
import com.asteria.world.entity.player.skill.Skills;

/**
 * A collection of constants that each represent a different fighting style.
 * 
 * @author lare96
 */
public enum FightStyle {
    ACCURATE() {
        @Override
        public int[] skills(CombatType type) {
            return type == CombatType.RANGED ? new int[] { Skills.RANGED }
                : new int[] { Skills.ATTACK };
        }
    },
    AGGRESSIVE() {
        @Override
        public int[] skills(CombatType type) {
            return type == CombatType.RANGED ? new int[] { Skills.RANGED }
                : new int[] { Skills.STRENGTH };
        }
    },
    DEFENSIVE() {
        @Override
        public int[] skills(CombatType type) {
            return type == CombatType.RANGED ? new int[] { Skills.RANGED,
                    Skills.DEFENCE } : new int[] { Skills.DEFENCE };
        }
    },
    CONTROLLED() {
        @Override
        public int[] skills(CombatType type) {
            return new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE };
        }
    };

    /**
     * Determines the skills trained by this fighting style based on the
     * {@link CombatType}.
     * 
     * @param type
     *            the combat type to determine the skills trained with.
     * @return the skills trained by this fighting style.
     */
    public abstract int[] skills(CombatType type);
}