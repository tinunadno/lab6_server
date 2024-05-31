package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.ClientInteractionManager;
import org.lab6.mainClasses.Controller;
import org.lab6.mainClasses.LabWorkListManager;

public class GetLabWorkList extends Command implements ThreadInteractingCommand{
    private ClientCommandManager clientThread;
    public GetLabWorkList(){
        this.setInterruptsThread(true);
    }
    public void execute(){
        ClientInteractionManager.send(clientThread.getUserAddress(), LabWorkListManager.getCollection());
    }
    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }
    public String getComment(){
        return "Synchronize%User Mustn't see this, it's service command";
    }
}
