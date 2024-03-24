package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.Message;

public class GetListAsJson extends Command{
    @Override
    public void execute(){
        new Message("successfully sent json file");
        new Message(LabWorkListManager.toJson());
    }
    @Override
    public String getComment(){return "get_list_as_json%возвращает LabWork list в формате json";}
}
