/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.miniserv.server;

/**
 * Stub implementation of miniserv Server for compilation.
 * TODO: Implement full miniserv server functionality
 */
public class Server {
    private static final Server INSTANCE = new Server();
    private final ClientManager clientManager = new ClientManager();
    
    public static Server getInstance() {
        return INSTANCE;
    }
    
    /**
     * Get the client manager
     * @return The client manager instance
     */
    public ClientManager getClientManager() {
        return clientManager;
    }
}
