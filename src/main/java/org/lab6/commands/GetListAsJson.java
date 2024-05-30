package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

import java.net.InetAddress;

public class GetListAsJson extends Command{
    private int port;
    private InetAddress address;
    public GetListAsJson(){
        this.setGivesResponse(true);
        this.setRequiresArgument(true);
    }
    @Override
    public void execute(){
        String json=(LabWorkListManager.toJson());
        responseManager.append(json);
    }
    @Override
    public String getComment(){return "get_list_as_json%возвращает LabWork list в формате json";}
}
