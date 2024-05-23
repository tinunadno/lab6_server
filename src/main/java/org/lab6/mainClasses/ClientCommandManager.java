package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClientCommandManager extends Thread{
    private int port;
    private int serverPort;
    private InetAddress adress;
    private Controller controller;
    private boolean isAlive;
    private int userID;
    private String userName;
    private UUID userToken;
    private ResponseManager responseManager;
    private boolean isAuthorized;
    private boolean isAuthorizedName;
    public ClientCommandManager(int port, int serverPort){
        this.port=port;
        this.serverPort=serverPort;
        this.adress=Main.getAdress();
        this.responseManager=new ResponseManager(serverPort, adress);
        this.isAuthorized=false;
        isAlive=true;

        userToken=UUID.randomUUID();
        try{Thread.sleep(150);}catch (InterruptedException e){}
        Message tokenMessage=new Message("token:", userToken);
        UDP_transmitter.send(port, adress, tokenMessage);
        Main.appendConnectedUsers(this);
        controller=new Controller(serverPort, adress, this, responseManager);
    }
    @Override
    public void run(){
        while(isAlive){
            SendedCommand sendedCommand = UDP_transmitter.get(port);
            if(userToken.equals(sendedCommand.getToken())) {
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
            }else{
                responseManager.append("bad token, try to send message again");
            }
            Main.submitResponse(responseManager);
        }
        Main.removeConnectedUser(this);
    }

    public void setIsAuthorized(boolean val){
        this.isAuthorized=val;
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
    public int getUserId(){return userID;}
    public String getUserName(){return this.userName;}
    @Override
    public String toString(){
        return "[User name:"+userName+"; userID:"+userID+"; port<client-server>:"+port+";\n port<server-client>:"+serverPort+"; client adress:"+adress.toString()+"]";
    }
    public void kill(){
        isAlive=false;
    }
}
