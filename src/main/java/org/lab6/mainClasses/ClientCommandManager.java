package org.lab6.mainClasses;

import org.lab6.mainClasses.UDPInteraction.Message;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.UUID;

public class ClientCommandManager extends Thread{
    private InetSocketAddress address;
    private Controller controller;
    private boolean isAlive;
    private int userID;
    private String userName;
    private UUID userToken;
    private ResponseManager responseManager;
    private boolean isAuthorized;
    private boolean isAuthorizedName;
    private int totalListUpdate;
    public ClientCommandManager(InetSocketAddress address){
        totalListUpdate=0;
        this.address=address;
        this.responseManager=new ResponseManager(address);
        controller=new Controller(this, responseManager);
        this.isAuthorized=false;
        isAlive=true;

        userToken=UUID.randomUUID();
        Message tokenMessage=new Message("token:", userToken);
        ClientInteractionManager.send(address, tokenMessage);
    }
    private SendedCommand sendedCommand;
    public void setSendedCommand(SendedCommand sc){
        this.sendedCommand=sc;
    }
    @Override
    public void run(){
        if(isAlive) {
            if (userToken.equals(sendedCommand.getToken())) {
                try {
                    if (sendedCommand.isArgumentExists() && sendedCommand.isNeedParsedInstance()) controller.invoke(
                            sendedCommand.getCommandName(), sendedCommand.getArgument(), sendedCommand.getParsedInstance());
                    else if (sendedCommand.isArgumentExists()) controller.invoke(sendedCommand.getCommandName(),
                            sendedCommand.getArgument());
                    else if (sendedCommand.isNeedParsedInstance()) controller.invoke(sendedCommand.getCommandName(),
                            sendedCommand.getParsedInstance());
                    else controller.invoke(sendedCommand.getCommandName());
                } catch (NullPointerException e) {
                    responseManager.append("can't deserialize object on server");
                }
            } else {
                responseManager.append("bad token, try to send message again");
            }
            responseManager.sendMessage();
        }
    }
    public UUID getUserToken(){return userToken;}
    public void setIsAuthorized(boolean val){
        this.isAuthorized=val;
        ClientInteractionManager.showUser();
    }
    public void setAuthorizedName(boolean val){
        this.isAuthorizedName=val;
    }
    public void setUserName(String name){
        this.userName=name;
        controller.setUserName(name);
    }
    public void setUserID(int ID) {
        this.userID=ID;
        controller.setUserID(ID);
    }
    public InetSocketAddress getUserAddress(){return address;}
    public int getUserId(){return userID;}
    public String getUserName(){return this.userName;}
    @Override
    public String toString(){
        return "[User name:"+userName+"; userID:"+userID+"; client address:"+address.toString()+"]\n";
    }
    public void kill(){
        isAlive=false;
        ClientInteractionManager.removeConnectedUser(address);
    }
    public int getTotalListUpdate(){return totalListUpdate;}
    public void setTotalListUpdate(int value){totalListUpdate=value;}
}
