package com.example.privatecloudstorageserver;

/**
 * bridge between network manager and server
 */
public class Proxy extends Connect {
    String mPassword;
    /**
     * constructor for Proxy class
     * take the ip address ,port numper of a (host/server) save them  for next connect
     * @param ipAdd identify the proxy server
     * @param portNum identify the proxy server we want to talk to
     * @param id
     * @param password
     */
    public Proxy(String ipAdd,int portNum,String id,String password) {
        super(ipAdd, portNum, id);
        mPassword=password;
    }
    /**
     * Gets the proxy password.
     * @return the proxy password.
     */
    public String getPasswordProxy() {
        return mPassword;
    }

}
