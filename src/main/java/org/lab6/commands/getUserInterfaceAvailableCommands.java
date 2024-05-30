package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.ClientInteractionManager;
import org.lab6.mainClasses.Controller;

import javax.naming.ldap.Control;
import java.util.ArrayList;

public class getUserInterfaceAvailableCommands extends Command implements ThreadInteractingCommand{
    private ClientCommandManager clientThread;
    public getUserInterfaceAvailableCommands(){
        this.setInterruptsThread(true);
    }
    public void execute(){
        ClientInteractionManager.send(clientThread.getUserAddress(), Controller.getUserGUIAvailableCommandList());
    }
    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }
    public String getComment(){
        return "get_user_available_commands%User Mustn't see this, it's service command";
    }
}
