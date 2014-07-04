package server.core.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * A static gateway type class that is used to limit the maximum amount of
 * connections per host.
 * 
 * @author blakeman8192
 * @author lare96
 */
public class HostGateway {

    /** A {@link Logger} for printing debugging info. */
    private static Logger logger = Logger.getLogger(HostGateway.class
            .getSimpleName());

    /** The maximum amount of connections per host. */
    public static final int MAX_CONNECTIONS_PER_HOST = 1;

    /** Used to keep track of hosts and their amount of connections. */
    private static ConcurrentHashMap<String, Integer> hostMap = new ConcurrentHashMap<String, Integer>();

    /** Used to keep track of unfriendly hosts. */
    private static CopyOnWriteArrayList<String> disabledHosts = new CopyOnWriteArrayList<String>();

    /** So this class cannot be instantiated. */
    private HostGateway() {

    }

    /**
     * Checks the host into the gateway.
     * 
     * @param host
     *            the host that needs to be checked.
     * @return true if the host can connect, false if it has reached the maximum
     *         amount of connections.
     */
    public static boolean enter(String host) {

        /**
         * If the host is coming from the hosting computer we don't need to
         * check it.
         */
        if (host.equals("127.0.0.1") || host.equals("localhost")) {
            logger.info("Session request from " + host
                    + "<unlimited> accepted.");
            return true;
        }

        /** Makes sure this host is not connecting too fast. */
        if (!HostThrottler.throttleHost(host)) {
            return false;
        }

        /** Reject if this host is banned. */
        if (disabledHosts.contains(host)) {
            logger.warning("Session request from IP banned host<" + host
                    + "> rejected.");
            return false;
        }

        Integer amount = hostMap.putIfAbsent(host, 1);

        /** If the host was not in the map, they're clear to go. */
        if (amount == null) {
            logger.info("Session request from " + host + "<1> accepted.");
            return true;
        }

        /** If they've reached the connection limit, return false. */
        if (amount == MAX_CONNECTIONS_PER_HOST) {
            logger.warning("Session request from " + host + "<" + amount
                    + "> over connection limit, rejected.");
            return false;
        }

        /** Otherwise, replace the key with the next value if it was present. */
        hostMap.putIfAbsent(host, amount + 1);
        logger.info("Session request from " + host + "<" + hostMap.get(host)
                + "> accepted.");
        return true;
    }

    /**
     * Unchecks the host from the gateway.
     * 
     * @param host
     *            the host that needs to be unchecked.
     */
    public static void exit(String host) {

        /**
         * If the host is coming from the hosting computer we don't need to
         * uncheck it.
         */
        if (host.equals("127.0.0.1") || host.equals("localhost")) {
            return;
        }

        /** Otherwise get the amount for the host. */
        Integer amount = hostMap.get(host);

        /** If there's no value for this host do nothing. */
        if (amount == null) {
            return;
        }

        /** Otherwise remove the host from the map if it's at 1 connection. */
        if (amount == 1) {
            hostMap.remove(host);
            HostThrottler.getTimeMap().remove(host);
            return;
        }

        /** Or decrement the amount of connections stored. */
        if (amount > 1) {
            hostMap.putIfAbsent(host, amount - 1);
        }
    }

    /**
     * Gets the map of connections.
     * 
     * @return the map of connections.
     */
    public static ConcurrentHashMap<String, Integer> getHostMap() {
        return hostMap;
    }

    /**
     * Gets a list of unfriendly hosts.
     * 
     * @return a list of the unfriendly hosts.
     */
    public static CopyOnWriteArrayList<String> getDisabledHosts() {
        return disabledHosts;
    }
}
