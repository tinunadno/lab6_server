package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;

import java.net.InetAddress;

public class Disconnect extends Command implements ThreadInteractingCommand {
    private int port;
    private InetAddress address;
    private ClientCommandManager clientThread;
    public Disconnect(){
        this.setGivesResponse(true);
        this.setInterruptsThread(true);
    }

    @Override
    public void execute(){
        responseManager.append("disconnecting");
        clientThread.kill();

        //System.exit(0);
    }
    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }
    @Override
    public String getComment(){
        return "shot_down%выключает сервер, оставляет клиента работать";
    }
}
