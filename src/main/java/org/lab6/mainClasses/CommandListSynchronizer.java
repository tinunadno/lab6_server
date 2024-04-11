package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;

public class CommandListSynchronizer {
    public static void synchronizeCommandListWithClient(int port, InetAddress address){
        UDP_transmitter.send(port, address ,Controller.getSortedCommands());
    }
}
