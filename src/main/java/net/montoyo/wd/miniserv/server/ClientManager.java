/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.miniserv.server;

import java.util.UUID;

/**
 * Stub implementation of miniserv ClientManager for compilation.
 * TODO: Implement full client management functionality
 */
public class ClientManager {
    
    /**
     * Encrypt the client's key with the provided RSA parameters
     * @param playerId The player's UUID
     * @param modulus RSA modulus
     * @param exponent RSA exponent
     * @return The encrypted key, or null if encryption failed
     */
    public byte[] encryptClientKey(UUID playerId, byte[] modulus, byte[] exponent) {
        // TODO: Implement RSA encryption
        return null;
    }
}
