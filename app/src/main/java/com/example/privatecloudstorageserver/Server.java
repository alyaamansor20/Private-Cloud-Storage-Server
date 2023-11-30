package com.example.privatecloudstorageserver;
/**
 * class server contain information about the server witch
 * id of server ,port number and ip address which identify a(host/server)
 */
public class Server extends Connect{
    public Server(){
        super();
    }
    /**
     * Constructor of Server class
     * @param ipAdd identify the server
     * @param portNum identify the server we want to talk to
     * @param id
     */
    public Server(String ipAdd, int portNum, String id) {
        super(ipAdd, portNum, id);
    }
}
