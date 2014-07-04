package server.world.entity.player.content;

import server.util.Misc;
import server.world.World;
import server.world.entity.player.Player;

/**
 * Manages the friend and ignore lists and sending of a private message from one
 * player to another.
 * 
 * @author lare96
 */
public class PrivateMessage {

    /** The player in control of the private message. */
    private Player player;

    /** The last private messaging id made by the player. */
    private int lastPrivateMessageId = 1;

    /**
     * Create a new {@link PrivateMessage}.
     * 
     * @param player
     *            the player in control of the private message.
     */
    public PrivateMessage(Player player) {
        this.player = player;
    }

    /**
     * Refreshes the friends list on login for yourself and players you have
     * added.
     */
    public void sendPrivateMessageOnLogin() {

        /** Sends the private messaging list for this player. */
        player.getPacketBuilder().sendPrivateMessagingList(2);

        /** Updates the list with all your friends. */
        for (long name : player.getFriends()) {
            if (name == 0) {
                continue;
            }

            /** Update with online/offline. */
            Player load = World.getPlayer(name);
            player.getPacketBuilder().loadPrivateMessage(name,
                    load == null ? 0 : 1);
        }

        for (Player players : World.getPlayers()) {
            if (players == null)
                continue;

            if (players.getFriends().contains(player.getUsernameHash())) {
                players.getPacketBuilder().loadPrivateMessage(
                        player.getUsernameHash(), 1);
            }
        }
    }

    /**
     * Refreshes the friends list on logout for players you have added.
     */
    public void sendPrivateMessageOnLogout() {

        for (Player players : World.getPlayers()) {
            if (players == null)
                continue;

            if (players.getFriends().contains(player.getUsernameHash())) {
                players.getPacketBuilder().loadPrivateMessage(
                        player.getUsernameHash(), 0);
            }
        }
    }

    /**
     * Adds someone to your friends list.
     * 
     * @param name
     *            the name of the person to add in a {@link Long} format.
     */
    public void addFriend(long name) {

        /** Block if the friends list is full. */
        if (player.getFriends().size() >= 200) {
            player.getPacketBuilder().sendMessage("Your friends list is full.");
            return;
        }

        /**
         * Block if the person you are trying to add is already on your friends
         * list.
         */
        if (player.getFriends().contains(name)) {
            player.getPacketBuilder().sendMessage(
                    "" + Misc.longToName(name)
                            + " is already on your friends list.");
            return;
        }

        /** Add the name to your friends list. */
        player.getFriends().add(name);

        /** Update the friends list with online/offline. */
        Player load = World.getPlayer(name);
        player.getPacketBuilder()
                .loadPrivateMessage(name, load == null ? 0 : 1);
    }

    /**
     * Adds someone to your ignores list.
     * 
     * @param name
     *            the name of the person to add in a {@link Long} format.
     */
    public void addIgnore(long name) {

        /** Block if the ignores list is full. */
        if (player.getIgnores().size() >= 100) {
            player.getPacketBuilder().sendMessage("Your ignores list is full.");
            return;
        }

        /**
         * Block if the person you are trying to add is already on your ignores
         * list.
         */
        if (player.getIgnores().contains(name)) {
            player.getPacketBuilder().sendMessage(
                    "" + Misc.longToName(name)
                            + " is already on your ignores list.");
            return;
        }

        /** Add the name to your ignores list. */
        player.getIgnores().add(name);
    }

    /**
     * Remove a friend from your friends list.
     * 
     * @param name
     *            the name of the person to remove in a {@link Long} format.
     */
    public void removeFriend(long name) {
        if (player.getFriends().contains(name)) {
            player.getFriends().remove(name);
        } else {
            player.getPacketBuilder().sendMessage(
                    "" + Misc.longToName(name)
                            + " is not even on your friends list...");
        }
    }

    /**
     * Remove an ignore from your ignores list.
     * 
     * @param name
     *            the name of the person to remove in a {@link Long} format.
     */
    public void removeIgnore(long name) {
        if (player.getIgnores().contains(name)) {
            player.getIgnores().remove(name);
        } else {
            player.getPacketBuilder().sendMessage(
                    "" + Misc.longToName(name)
                            + " is not even on your ignores list...");
        }
    }

    /**
     * Sends a private message to another player.
     * 
     * @param sendingFrom
     *            the player sending the message.
     * @param sendingTo
     *            the player being sent the message.
     * @param message
     *            the message in a {@link Byte} array format.
     * @param messageSize
     *            the total size of the message.
     */
    public void sendPrivateMessage(Player sendingFrom, long sendingTo,
            byte[] message, int messageSize) {
        Player send = World.getPlayer(sendingTo);

        if (send != null) {
            send.getPacketBuilder().sendPrivateMessage(
                    sendingFrom.getUsernameHash(),
                    sendingFrom.getRights().getProtocolValue(), message,
                    messageSize);
        }
    }

    /**
     * Gets your last private message id (plus 1).
     * 
     * @return your last private message id (plus 1).
     */
    public int getLastPrivateMessageId() {
        return lastPrivateMessageId++;
    }
}
