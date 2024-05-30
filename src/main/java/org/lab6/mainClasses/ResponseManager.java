package org.lab6.mainClasses;

import org.lab6.mainClasses.UDPInteraction.Message;

import java.net.SocketAddress;

public class ResponseManager{
    private  String responses="";
    private int port;
    private SocketAddress address;
    public ResponseManager(SocketAddress address){
        this.port=port;
        this.address=address;
    }
    public void append(String Message){
        responses+=Message+"\n";
    }
    public void sendMessage(){
        if(!responses.equals("")) {
            Message response = new Message(responses);
            ClientInteractionManager.send(address, response);
        }
        responses="";
    }
}
