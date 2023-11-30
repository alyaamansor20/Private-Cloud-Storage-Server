package com.example.privatecloudstorageserver;
import java.util.UUID;
/**
 * manage clients .
 */
public class HostManager {
    UUID mUid;
    String mPassword;

    /**
     * constructor for HostManager class
     */
    public HostManager() {
        this.mUid = UUID.randomUUID();
        this.mPassword = " ";
    }
    /**
     * Gets the client id .
     * @return the mUidSelf
     */
    public UUID getUid() {
        return mUid;
    }

    /**
     * set client id
     * @param mUid
     */
    public void setUid(UUID mUid) {
        this.mUid = mUid;
    }
    /**
     * Gets the client password.
     * @return the mSelfPass
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * set client password
     * @param mPassword
     */
    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
