package org.lab6.commands;

import org.lab6.Main;
import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.Message;
import org.lab6.mainClasses.ResponseManager;
import org.lab6.mainClasses.UDP_transmitter;

public class GetListAsJson extends Command{
    @Override
    public void execute(){
        Message json=new Message(LabWorkListManager.toJson());
        ResponseManager.append("successfully sent json file");

        UDP_transmitter.send(Main.getPort(), Main.getAdress(), json);
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("interrupted, failed to wait response");
        }
    }
    @Override
    public String getComment(){return "get_list_as_json%возвращает LabWork list в формате json";}
}
