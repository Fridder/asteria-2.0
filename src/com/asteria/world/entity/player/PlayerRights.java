package com.asteria.world.entity.player;

/**
 * All of the different positions in the social pyramid.
 * 
 * @author lare96
 */
public enum PlayerRights {

    /** A regular player, the default rank. */
    PLAYER(0, 0),

    /** A donator, a regular player who has donated money. */
    DONATOR(0, 0),

    /** A veteran, a player who has been here since the start. */
    VETERAN(0, 0),

    /** A moderator, a player who has been appointed moderating power. */
    MODERATOR(1, 1),

    /** An administrator, a player who has been appointed administrative power. */
    ADMINISTRATOR(2, 2),

    /** A developer, has total control over the entire server. */
    DEVELOPER(2, 3);

    /**
     * The value of this rank as seen by the protocol. The only ranks the
     * protocol sees are player (0), moderator (1), and administrator (2).
     */
    private int protocolValue;

    /** The value of this rank as seen by the server. */
    private int value;

    /**
     * Create a new {@link PlayerRights}.
     * 
     * @param protocolValue
     *            the value of this rank as seen by the protocol.
     * @param value
     *            the value of this rank as seen by the server.
     */
    private PlayerRights(int protocolValue, int value) {
        this.protocolValue = protocolValue;
        this.value = value;
    }

    /**
     * Determines if this right is greater than the argued right. Please note
     * that this method <b>does not</b> compare the {@link Object}s themselves,
     * but instead compares the value behind them as specified by
     * <code>value</code> in the <code>PlayerRights</code> enum.
     * 
     * @param other
     *            the argued right to compare.
     * @return true if this right is greater.
     */
    public boolean greaterThan(PlayerRights other) {
        return value > other.value;
    }

    /**
     * Determines if this right is lesser than the argued right. Please note
     * that this method <b>does not</b> compare the {@link Object}s themselves,
     * but instead compares the value behind them as specified by
     * <code>value</code> in the <code>PlayerRights</code> enum.
     * 
     * @param other
     *            the argued right to compare.
     * @return true if this right is lesser.
     */
    public boolean lessThan(PlayerRights other) {
        return value < other.value;
    }

    /**
     * Determines if this right is equal in power to the argued right. Please
     * note that this method <b>does not</b> compare the {@link Object}s
     * themselves, but instead compares the value behind them as specified by
     * <code>value</code> in the <code>PlayerRights</code> enum.
     * 
     * @param other
     *            the argued right to compare.
     * @return true if this right is equal.
     */
    public boolean equalTo(PlayerRights other) {
        return value == other.value;
    }

    /**
     * Gets the value of this rank as seen by the protocol.
     * 
     * @return the value of this rank as seen by the protocol.
     */
    public int getProtocolValue() {
        return protocolValue;
    }

    /**
     * Gets the value of this rank as seen by the server.
     * 
     * @return the value of this rank as seen by the server.
     */
    public int getValue() {
        return value;
    }
}
