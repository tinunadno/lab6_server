package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;

public class ResponseManager {
    private static String responses="";
    public static void append(String Message){
        responses+=Message+"\n";
    }
    public static void sendMessage(int port, InetAddress address){
        if(!responses.equals("")) {
            Message response = new Message(responses);
            UDP_transmitter.send(port, address, response);
        }
        responses="";
    }
}
