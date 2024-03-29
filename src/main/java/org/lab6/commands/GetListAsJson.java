package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.Message;

public class GetListAsJson extends Command{
    @Override
    public void execute(){
        Message json=new Message();
        Message.append("successfully sent json file");
        json.append_object(LabWorkListManager.toJson());
        json.sentMessage_object();
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("interrupted, failed to wait response");
        }
    }
    @Override
    public String getComment(){return "get_list_as_json%возвращает LabWork list в формате json";}
}
