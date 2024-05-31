package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.ClientInteractionManager;
import org.lab6.mainClasses.Controller;
import org.lab6.mainClasses.LabWorkListManager;

public class GetLabWorkList extends Command implements ThreadInteractingCommand, CommandWithArgument{
    private ClientCommandManager clientThread;
    private String argument;
    public GetLabWorkList(){
        this.setInterruptsThread(true);
        this.setRequiresArgument(true);
    }
    public void execute(){
        if(argument.equals("init") || LabWorkListManager.getTotalListUpdate()!=clientThread.getTotalListUpdate()) {
            clientThread.setTotalListUpdate(LabWorkListManager.getTotalListUpdate());
            ClientInteractionManager.send(clientThread.getUserAddress(), LabWorkListManager.getCollection());
        }else{
            ClientInteractionManager.send(clientThread.getUserAddress(), null);
        }
    }
    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }
    public String getComment(){
        return "GETLWLIST%get lab work list";
    }
    @Override
    public void setArgument(String arg) {
        this.argument = arg;
    }
}
