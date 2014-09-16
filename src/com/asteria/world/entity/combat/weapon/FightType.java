package com.asteria.world.entity.combat.weapon;

import com.asteria.util.Utility;
import com.asteria.world.entity.player.Player;
import com.asteria.world.entity.player.skill.Skills;

/**
 * A collection of constants that each represent a different fighting type.
 * 
 * @author lare96
 */
public enum FightType {

    STAFF_BASH(406, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
    STAFF_POUND(406, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    STAFF_FOCUS(406, new int[] { Skills.DEFENCE }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
    WARHAMMER_POUND(401, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
    WARHAMMER_PUMMEL(401, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    WARHAMMER_BLOCK(401, new int[] { Skills.DEFENCE }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
    SCYTHE_REAP(408, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    SCYTHE_CHOP(451, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
    SCYTHE_JAB(412, new int[] { Skills.STRENGTH }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    SCYTHE_BLOCK(408, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    BATTLEAXE_CHOP(1833, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    BATTLEAXE_HACK(1833, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    BATTLEAXE_SMASH(401, new int[] { Skills.STRENGTH }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    BATTLEAXE_BLOCK(1833, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    CROSSBOW_ACCURATE(427, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    CROSSBOW_RAPID(427, new int[] { Skills.RANGED }, 43, 1, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    CROSSBOW_LONGRANGE(427, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
    SHORTBOW_ACCURATE(426, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    SHORTBOW_RAPID(426, new int[] { Skills.RANGED }, 43, 1, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    SHORTBOW_LONGRANGE(426, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
    LONGBOW_ACCURATE(426, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    LONGBOW_RAPID(426, new int[] { Skills.RANGED }, 43, 1, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    LONGBOW_LONGRANGE(426, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
    DAGGER_STAB(400, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_STAB, FightStyle.ACCURATE),
    DAGGER_LUNGE(400, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
    DAGGER_SLASH(451, new int[] { Skills.STRENGTH }, 43, 2, Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
    DAGGER_BLOCK(400, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
    SWORD_STAB(412, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_STAB, FightStyle.ACCURATE),
    SWORD_LUNGE(412, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
    SWORD_SLASH(451, new int[] { Skills.STRENGTH }, 43, 2, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    SWORD_BLOCK(412, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
    SCIMITAR_CHOP(451, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    SCIMITAR_SLASH(451, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    SCIMITAR_LUNGE(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
            Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB, FightStyle.CONTROLLED),
    SCIMITAR_BLOCK(451, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    LONGSWORD_CHOP(451, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    LONGSWORD_SLASH(451, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    LONGSWORD_LUNGE(412, new int[] { Skills.ATTACK, Skills.STRENGTH,
            Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB, FightStyle.CONTROLLED),
    LONGSWORD_BLOCK(451, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    MACE_POUND(1833, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
    MACE_PUMMEL(401, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    MACE_SPIKE(412, new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB, FightStyle.CONTROLLED),
    MACE_BLOCK(401, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
    KNIFE_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    KNIFE_RAPID(806, new int[] { Skills.RANGED }, 43, 1, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    KNIFE_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
    SPEAR_LUNGE(2080, new int[] { Skills.ATTACK, Skills.STRENGTH,
            Skills.DEFENCE }, 43, 0, Utility.ATTACK_STAB, FightStyle.CONTROLLED),
    SPEAR_SWIPE(2081, new int[] { Skills.ATTACK, Skills.STRENGTH,
            Skills.DEFENCE }, 43, 1, Utility.ATTACK_SLASH, FightStyle.CONTROLLED),
    SPEAR_POUND(2082, new int[] { Skills.ATTACK, Skills.STRENGTH,
            Skills.DEFENCE }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.CONTROLLED),
    SPEAR_BLOCK(2080, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
    TWOHANDEDSWORD_CHOP(407, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    TWOHANDEDSWORD_SLASH(407, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    TWOHANDEDSWORD_SMASH(406, new int[] { Skills.STRENGTH }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    TWOHANDEDSWORD_BLOCK(407, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    PICKAXE_SPIKE(412, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_STAB, FightStyle.ACCURATE),
    PICKAXE_IMPALE(412, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_STAB, FightStyle.AGGRESSIVE),
    PICKAXE_SMASH(401, new int[] { Skills.STRENGTH }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    PICKAXE_BLOCK(412, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
    CLAWS_CHOP(451, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    CLAWS_SLASH(451, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    CLAWS_LUNGE(412, new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB, FightStyle.CONTROLLED),
    CLAWS_BLOCK(451, new int[] { Skills.DEFENCE }, 43, 3, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    HALBERD_JAB(412, new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, 43, 0, Utility.ATTACK_STAB, FightStyle.CONTROLLED),
    HALBERD_SWIPE(440, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_SLASH, FightStyle.AGGRESSIVE),
    HALBERD_FEND(412, new int[] { Skills.DEFENCE }, 43, 2, Utility.ATTACK_STAB, FightStyle.DEFENSIVE),
    UNARMED_PUNCH(422, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_CRUSH, FightStyle.ACCURATE),
    UNARMED_KICK(423, new int[] { Skills.STRENGTH }, 43, 1, Utility.ATTACK_CRUSH, FightStyle.AGGRESSIVE),
    UNARMED_BLOCK(422, new int[] { Skills.DEFENCE }, 43, 2, Utility.ATTACK_CRUSH, FightStyle.DEFENSIVE),
    WHIP_FLICK(1658, new int[] { Skills.ATTACK }, 43, 0, Utility.ATTACK_SLASH, FightStyle.ACCURATE),
    WHIP_LASH(1658, new int[] { Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE }, 43, 1, Utility.ATTACK_SLASH, FightStyle.CONTROLLED),
    WHIP_DEFLECT(1658, new int[] { Skills.DEFENCE }, 43, 2, Utility.ATTACK_SLASH, FightStyle.DEFENSIVE),
    THROWNAXE_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    THROWNAXE_RAPID(806, new int[] { Skills.RANGED }, 43, 1, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    THROWNAXE_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
    DART_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    DART_RAPID(806, new int[] { Skills.RANGED }, 43, 1, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    DART_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 2, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE),
    JAVELIN_ACCURATE(806, new int[] { Skills.RANGED }, 43, 0, Utility.ATTACK_RANGE, FightStyle.ACCURATE),
    JAVELIN_RAPID(806, new int[] { Skills.RANGED }, 43, 2, Utility.ATTACK_RANGE, FightStyle.AGGRESSIVE),
    JAVELIN_LONGRANGE(806, new int[] { Skills.RANGED, Skills.DEFENCE }, 43, 3, Utility.ATTACK_RANGE, FightStyle.DEFENSIVE);

    /** The animation this fight type holds. */
    private int animation;

    /** The train type this fight type holds. */
    private int[] trainType;

    /** The parent config id. */
    private int parentId;

    /** The child config id. */
    private int childId;

    /** The bonus type. */
    private int bonusType;

    /** The fighting style. */
    private FightStyle style;

    /**
     * Create a new {@link FightType}.
     * 
     * @param animation
     *            the animation this fight type holds.
     * @param trainType
     *            the train type this fight type holds.
     * @param parentId
     *            the parent config id.
     * @param childId
     *            the child config id.
     * @param bonusType
     *            the bonus type.
     * @param fightStyle
     *            the fighting style.
     */
    private FightType(int animation, int[] trainType, int parentId,
        int childId, int bonusType, FightStyle style) {
        this.animation = animation;
        this.trainType = trainType;
        this.parentId = parentId;
        this.childId = childId;
        this.bonusType = bonusType;
        this.style = style;
    }

    /**
     * Gets the animation this fight type holds.
     * 
     * @return the animation.
     */
    public int getAnimation() {
        return animation;
    }

    /**
     * Gets the train type this fight type holds.
     * 
     * @return the train type.
     */
    public int[] getTrainType() {
        return trainType;
    }

    /**
     * Gets the parent config id.
     * 
     * @return the parent id.
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * Gets the child config id.
     * 
     * @return the child id.
     */
    public int getChildId() {
        return childId;
    }

    /**
     * Gets the bonus type.
     * 
     * @return the bonus type.
     */
    public int getBonusType() {
        return bonusType;
    }

    /**
     * Gets the fighting style.
     * 
     * @return the fighting style.
     */
    public FightStyle getStyle() {
        return style;
    }

    /**
     * Determines the corresponding bonus for this fight type.
     * 
     * @return the corresponding bonus for this fight type.
     */
    public int getCorrespondingBonus() {
        switch (bonusType) {
        case Utility.ATTACK_CRUSH:
            return Utility.DEFENCE_CRUSH;
        case Utility.ATTACK_MAGIC:
            return Utility.DEFENCE_MAGIC;
        case Utility.ATTACK_RANGE:
            return Utility.DEFENCE_RANGE;
        case Utility.ATTACK_SLASH:
            return Utility.DEFENCE_SLASH;
        case Utility.ATTACK_STAB:
            return Utility.DEFENCE_STAB;
        default:
            return Utility.DEFENCE_CRUSH;
        }
    }

    /**
     * Changes the fight type when a weapon is equipped or unequipped.
     * 
     * @param player
     *            the player changing their weapon.
     */
    public static void assign(Player player) {

        for (FightType fightType : player.getWeapon().getFightType()) {
            if (fightType.getTrainType() == player.getFightType().getTrainType()) {
                player.setFightType(fightType);
                player.getPacketBuilder().sendConfig(
                    player.getFightType().getParentId(),
                    player.getFightType().getChildId());
                return;
            }
        }

        player.setFightType(player.getWeapon().getFightType()[0]);
        player.getPacketBuilder().sendConfig(
            player.getFightType().getParentId(),
            player.getFightType().getChildId());
    }
}