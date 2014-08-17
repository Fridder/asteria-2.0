package com.asteria.world.entity;

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
     * Holds all of the update flag constants. Please note that the order of
     * these constants <b>should never</b> be changed!
     * 
     * @author lare96
     */
    public enum Flag {
        APPEARANCE,
        CHAT,
        GRAPHICS,
        ANIMATION,
        FORCED_CHAT,
        FACE_ENTITY,
        FACE_COORDINATE,
        HIT,
        HIT_2,
        TRANSFORM
    }

    /**
     * Flags the argued update flag. This method will do nothing if the argued
     * flag already has a value of <code>true</code>.
     * 
     * @param flag
     *            the update flag that will be flagged.
     */
    public void flag(Flag flag) {
        bits.set(flag.ordinal());
    }

    /**
     * Flips the value of the argued update flag.
     * 
     * @param flag
     *            the update flag that will be flipped.
     */
    public void flip(Flag flag) {
        bits.flip(flag.ordinal());
    }

    /**
     * Gets the value of an update flag.
     * 
     * @param flag
     *            the update flag to get the value of.
     * @return the value of the flag.
     */
    public boolean get(Flag flag) {
        return bits.get(flag.ordinal());
    }

    /**
     * Determines if an update is required. This is done by checking if the
     * backing {@link #bits} is not empty.
     * 
     * @return true if an update is required, meaning the backing bit set is not
     *         empty.
     */
    public boolean isUpdateRequired() {
        return !bits.isEmpty();
    }

    /**
     * Resets the update flags, essentially reverting all flags back to a state
     * of <code>false</code>.
     */
    public void reset() {
        bits.clear();
    }
}
