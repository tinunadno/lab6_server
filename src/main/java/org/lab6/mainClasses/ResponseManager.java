package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;

public class ResponseManager implements Runnable{
    private  String responses="";
    private int port;
    private InetAddress address;
    public ResponseManager(int port, InetAddress address){
        this.port=port;
        this.address=address;
    }
    public  void append(String Message){
        responses+=Message+"\n";
    }
    @Override
    public void run(){
        this.sendMessage();
    }
    public  void sendMessage(){
        if(!responses.equals("")) {
            Message response = new Message(responses);
            UDP_transmitter.send(port, address, response);
        }
        responses="";
    }
}
