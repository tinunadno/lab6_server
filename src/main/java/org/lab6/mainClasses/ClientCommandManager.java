package org.lab6.mainClasses;

import org.lab6.Main;

public class ClientCommandManager {
    public static void startMonitoring(){
        while(true){
            SendedCommand sendedCommand = UDP_transmitter.get(Main.getPort());
            if(sendedCommand.isArgumentExists() && sendedCommand.isNeedParsedInstance())Controller.invoke(
                    sendedCommand.getCommandName(), sendedCommand.getArgument(), sendedCommand.getParsedInstance());
            else if(sendedCommand.isArgumentExists())Controller.invoke(sendedCommand.getCommandName(),
                    sendedCommand.getArgument());
            else if(sendedCommand.isNeedParsedInstance()) Controller.invoke(sendedCommand.getCommandName(),
                    sendedCommand.getParsedInstance());
            else Controller.invoke(sendedCommand.getCommandName());
        }
    }
}
