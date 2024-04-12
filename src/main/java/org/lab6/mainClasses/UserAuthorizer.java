package org.lab6.mainClasses;

import java.net.InetAddress;

public class UserAuthorizer {
    private int port;
    private int serverPort;
    private InetAddress address;
    public UserAuthorizer(int port, int serverPort, InetAddress address){
        this.port=port;
        this.serverPort=serverPort;
        this.address=address;
    }
    public int init(){
        String userData=((Message)UDP_transmitter.get(port)).getMessage();

        return 0;
    }
}
