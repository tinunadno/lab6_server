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
    public ClientCommandManager(int port, int serverPort){
        this.port=port;
        this.serverPort=serverPort;
        this.adress=Main.getAdress();
        this.responseManager=new ResponseManager(serverPort, adress);
        isAlive=true;
    }
    @Override
    public void run(){
        UserAuthorizer userAuthorizer=new UserAuthorizer(port, serverPort, adress);
        ResultSet userInfo =userAuthorizer.init();
        try{
            userID=Integer.parseInt(userInfo.getString("id"));
            userName=userInfo.getString("username");
        }catch (SQLException e){
            e.printStackTrace();
        }
        userToken=UUID.randomUUID();
        try{Thread.sleep(150);}catch (InterruptedException e){}
        Message tokenMessage=new Message("token:", userToken);
        UDP_transmitter.send(port, adress, tokenMessage);
        controller=new Controller(serverPort, adress, this, userID, userName, responseManager);
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
            responseManager.sendMessage();
        }
    }
    public void kill(){
        isAlive=false;
    }
}
