package server.world.entity;

import java.util.BitSet;

/**
 * Manages update flags for all in-game entities.
 * 
 * @author lare96
 */
public class UpdateFlags {

    /** A bit set holding the values for the update flags. */
    private BitSet bits = new BitSet(Flag.values().length);

    /**
     * Flag an update flag.
     * 
     * @param flag
     *        the update flag you are flagging.
     */
    public void flag(Flag flag) {
        bits.set(flag.ordinal());
    }

    /**
     * Gets the value of an update flag.
     * 
     * @param flag
     *        the flag to get the value of.
     * @return the value of the flag.
     */
    public boolean get(Flag flag) {
        return bits.get(flag.ordinal());
    }

    /**
     * Gets if an update is required or not.
     * 
     * @return true if an update is required.
     */
    public boolean isUpdateRequired() {
        return !bits.isEmpty();
    }

    /**
     * Resets the update flags.
     */
    public void reset() {
        bits.clear();
    }

    /**
     * Enum which holds data for each update flag.
     * 
     * @author lare96
     */
    public enum Flag {

        /** Appearance update. */
        APPEARANCE,

        /** Chat update. */
        CHAT,

        /** Graphics update. */
        GRAPHICS,

        /** Animation update. */
        ANIMATION,

        /** Forced chat update. */
        FORCED_CHAT,

        /** Interacting entity update. */
        FACE_ENTITY,

        /** Face coordinate update. */
        FACE_COORDINATE,

        /** Hit update. */
        HIT,

        /** Hit 2 update. */
        HIT_2,

        /** Transform entity to another npc. */
        TRANSFORM
    }
}
