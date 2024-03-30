package org.lab6.commands;

import org.lab6.Main;
import org.lab6.mainClasses.Message;
import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.UDP_transmitter;

public class ShotDown extends Command{
    @Override
    public void execute(){
        LabWorkListManager.save("./src/main/java/org/lab6/test.json");
        UDP_transmitter.send(Main.getPort(),Main.getAdress(),new Message("server shot down..."));
        System.exit(0);
    }
    @Override
    public String getComment(){
        return "shot_down%выключает сервер, оставляет клиента работать";
    }
}
