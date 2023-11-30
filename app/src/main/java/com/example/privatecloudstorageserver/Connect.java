package com.example.privatecloudstorageserver;
/**
 * Connect class is a super class
 */
public class Connect {
    private String mIpAddress;
    private int mPortNumber;
    private String mId;
    /**
     * Gets the ip address
     * @return the ip address
     */
    public String getIpAddress() {
        return mIpAddress;
    }
    /**
     * Gets the port number
     * @return the port number
     */
    public int getPortNumber() {
        return mPortNumber;
    }
    /**
     * Gets the id of (proxy or server)
     * @return the id
     */
    public String getId() {
        return mId;
    }
    /**
     * Sets the ip address which
     * identify a host
     */
    public void setIpAddress(String mIpAddress) {
        this.mIpAddress = mIpAddress;
    }
    /**
     * Sets the port number which
     * identify Application/Server on the System
     */
    public void setPortNumber(int mPortNumber) {
        this.mPortNumber = mPortNumber;
    }
    /**
     * Sets the id of the (proxy or server)
     */
    public void setId(String mId) {
        this.mId = mId;
    }
    /**
     * Constructor for Connect class
     */
    public Connect(){}
    /**
     * Constructor for Connect class with parameter
     * @param ipAdd identify the (server/proxy)
     * @param portNum identify the (server/proxy) we want to talk to
     * @param id
     */
    public Connect(String ipAdd, int portNum, String id) {
        mIpAddress =ipAdd;
        mPortNumber =portNum;
        mId=id;
    }
}
