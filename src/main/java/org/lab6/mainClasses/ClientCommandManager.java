package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;

public class ClientCommandManager extends Thread{
    private int port;
    private int serverPort;
    private InetAddress adress;
    private Controller controller;
    private boolean isAlive;
    public ClientCommandManager(int port, int serverPort){
        this.port=port;
        this.serverPort=serverPort;
        this.adress=Main.getAdress();
        controller=new Controller(serverPort, adress, this);
        isAlive=true;
    }
    @Override
    public void run(){
        System.out.println("asdasdasd");
        System.out.println(port);
        System.out.println(serverPort);
        while(isAlive){
            SendedCommand sendedCommand = UDP_transmitter.get(port);
            System.out.println(sendedCommand.getCommandName());
            try {
                if (sendedCommand.isArgumentExists() && sendedCommand.isNeedParsedInstance()) controller.invoke(
                        sendedCommand.getCommandName(), sendedCommand.getArgument(), sendedCommand.getParsedInstance());
                else if (sendedCommand.isArgumentExists()) controller.invoke(sendedCommand.getCommandName(),
                        sendedCommand.getArgument());
                else if (sendedCommand.isNeedParsedInstance()) controller.invoke(sendedCommand.getCommandName(),
                        sendedCommand.getParsedInstance());
                else controller.invoke(sendedCommand.getCommandName());
            }catch(NullPointerException e){
                System.out.println("can't process command");
                ResponseManager.append("can't deserialize object on server");
            }
            ResponseManager.sendMessage(serverPort, adress);
        }
    }
    public void kill(){
        isAlive=false;
    }
}
