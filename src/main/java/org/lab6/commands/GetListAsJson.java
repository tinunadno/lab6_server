package org.lab6.commands;

import org.lab6.Main;
import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.Message;
import org.lab6.mainClasses.ResponseManager;
import org.lab6.mainClasses.UDP_transmitter;

import java.net.InetAddress;

public class GetListAsJson extends Command implements ResponseCommand{
    private int port;
    private InetAddress address;
    public GetListAsJson(){
        this.setGivesResponse(true);
    }
    @Override
    public void execute(){
        Message json=new Message(LabWorkListManager.toJson());
        ResponseManager.append("successfully sent json file");

        UDP_transmitter.send(port, address, json);
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            System.out.println("interrupted, failed to wait response");
        }
    }
    @Override
    public void setPort(int port){
        this.port=port;
    }
    @Override
    public void setAddress(InetAddress address){
        this.address=address;
    }
    @Override
    public String getComment(){return "get_list_as_json%возвращает LabWork list в формате json";}
}
