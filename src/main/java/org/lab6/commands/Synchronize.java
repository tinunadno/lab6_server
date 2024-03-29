package org.lab6.commands;

import org.lab6.mainClasses.CommandListSynchronizer;
import org.lab6.mainClasses.Message;

public class Synchronize extends Command{
    public void execute(){
        CommandListSynchronizer.synchronizeCommandListWithClient();
    }
    public String getComment(){
        return "Synchronize%User Mustn't see this, it's service command";
    }

}
