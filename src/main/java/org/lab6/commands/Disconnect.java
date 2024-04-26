package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.Message;
import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.UDP_transmitter;

import java.net.InetAddress;

public class Disconnect extends Command implements ResponseCommand, InterruptingCommand{
    private int port;
    private InetAddress address;
    private ClientCommandManager clientThread;
    public Disconnect(){
        this.setGivesResponse(true);
        this.setInterruptsThread(true);
    }

    @Override
    public void execute(){
        LabWorkListManager.save("./src/main/java/org/lab6/test.json");
        UDP_transmitter.send(port,address,new Message("disconnecting"));
        clientThread.kill();

        //System.exit(0);
    }
    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }
    @Override
    public void setPort(int port){
        this.port=port;
    }
    @Override
    public void setAddress(InetAddress address){
        this.address=address;
    }
    @Override
    public String getComment(){
        return "shot_down%выключает сервер, оставляет клиента работать";
    }
}
