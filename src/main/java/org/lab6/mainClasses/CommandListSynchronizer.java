package org.lab6.mainClasses;

import org.lab6.Main;

public class CommandListSynchronizer {
    public static void synchronizeCommandListWithClient(){
        UDP_transmitter.send(Main.getServerPort(), Main.getAdress() ,Controller.getSortedCommands());
    }
}
