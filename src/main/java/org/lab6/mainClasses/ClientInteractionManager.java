package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.commands.UserIdRequire;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.mainClasses.UDPInteraction.Server_UDP_acceptor;
import org.lab6.storedClasses.LabWork;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientInteractionManager extends Thread{
    private Server_UDP_acceptor transmitter;
    private ExecutorService commandExecutor;
    private static ExecutorService sendingExecutor;
    private HashMap<UUID, ClientCommandManager> currentClients;
    private static ClientInteractionManager cim;
    @Override
    public void run(){
        startMonitor();
    }
    public void startMonitor(){
        while(Main.isRunning()){
            get();
        }
    }
    public ClientInteractionManager(int port){
        if(cim==null) {
            transmitter = new Server_UDP_acceptor(port);
            currentClients = new HashMap<>();
            commandExecutor = Executors.newCachedThreadPool();
            sendingExecutor=Executors.newFixedThreadPool(10);
            cim=this;
        }
    }
    private void get(){
        SendedCommand acceptedObject=(SendedCommand) (transmitter.get());
        UUID userToken=acceptedObject.getToken();
        if(currentClients.containsKey(userToken)) {
            currentClients.get(userToken).setSendedCommand(acceptedObject);
            commandExecutor.submit(currentClients.get(userToken));
        }else{
            ClientCommandManager ccm=new ClientCommandManager(transmitter.getUserAdress());
            currentClients.put(ccm.getUserToken(), ccm);
        }
    }
    public static void showUser(){
        ConnectedUserImage.showUsers(cim.currentClients);
    }
    public static void send(SocketAddress clientAddress, Object obj){
        cim.transmitter.setSendingObject(obj);
        cim.transmitter.setUserSendingAddress(clientAddress);
        sendingExecutor.submit(cim.transmitter);
    }
    public static void removeConnectedUser(SocketAddress userAdress){
        cim.currentClients.remove(userAdress);
        ConnectedUserImage.showUsers(cim.currentClients);
    }
    public void clearChannel(){
        transmitter.clearChannel();
    }
}
