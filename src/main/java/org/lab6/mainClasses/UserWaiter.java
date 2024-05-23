package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.Message;
import org.lab6.mainClasses.UDP_transmitter;

public class UserWaiter {
    public static void startUserMonitor(){
        while(Main.isRunning()) {
            UDP_transmitter.get(Main.getPort());
            try{Thread.sleep(400);}catch(InterruptedException e){}
            Message message = new Message(Main.getCurrentPort() + "%" + Main.getCurrentServerPort());
            UDP_transmitter.send(Main.getServerPort(), Main.getAdress(), message);
            (new ClientCommandManager(Main.getCurrentPort(), Main.getCurrentServerPort())).start();
            Main.incCurrentPort();
            Main.setInetAdress(null);
        }
    }
}
