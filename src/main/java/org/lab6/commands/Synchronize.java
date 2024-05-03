package org.lab6.commands;

import org.lab6.mainClasses.CommandListSynchronizer;
import org.lab6.mainClasses.Message;

import java.net.InetAddress;

public class Synchronize extends Command implements ResponseCommand{
    private int port;
    private InetAddress address;
    public Synchronize(){
        this.setGivesResponse(true);
    }
    public void execute(){
        CommandListSynchronizer.synchronizeCommandListWithClient(port, address);
    }
    public void setPort(int port){
        this.port=port;
    }
    public void setAddress(InetAddress address){
        this.address=address;
    }
    public String getComment(){
        return "Synchronize%User Mustn't see this, it's service command";
    }

}
