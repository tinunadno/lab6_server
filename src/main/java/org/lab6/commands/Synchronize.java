package org.lab6.commands;

import org.lab6.mainClasses.*;

import java.net.InetAddress;
import java.util.Arrays;

public class Synchronize extends Command implements ThreadInteractingCommand{
    private ClientCommandManager clientThread;
    public Synchronize(){
        this.setInterruptsThread(true);
    }
    public void execute(){
        ClientInteractionManager.send(clientThread.getUserAddress(), Controller.getSortedCommands());
    }
    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }
    public String getComment(){
        return "Synchronize%User Mustn't see this, it's service command";
    }

}
