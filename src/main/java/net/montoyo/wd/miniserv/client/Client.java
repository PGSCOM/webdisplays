/*
 * Copyright (C) 2018 BARBOTIN Nicolas
 */

package net.montoyo.wd.miniserv.client;

import net.montoyo.wd.net.server_bound.C2SMessageMiniservConnect;

/**
 * Stub implementation of miniserv Client for compilation.
 * TODO: Implement full miniserv client functionality
 */
public class Client {
    private static final Client INSTANCE = new Client();
    
    public static Client getInstance() {
        return INSTANCE;
    }
    
    /**
     * Decrypt the encryption key received from the server
     * @param encryptedKey The encrypted key
     * @return true if decryption was successful
     */
    public boolean decryptKey(byte[] encryptedKey) {
        // TODO: Implement key decryption
        return false;
    }
    
    /**
     * Begin connection to miniserv server
     * @return Connection message to send to server
     */
    public C2SMessageMiniservConnect beginConnection() {
        // TODO: Implement connection initialization
        return new C2SMessageMiniservConnect(new byte[0], new byte[0]);
    }
}
