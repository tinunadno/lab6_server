package org.lab6.commands;

import org.lab6.mainClasses.Message;

public class ShotDown extends Command{
    @Override
    public void execute(){
        new Message("shotting down...");
        System.exit(0);
    }
    @Override
    public String getComment(){
        return "shot_down%выключает сервер, оставляет клиента работать";
    }
}
