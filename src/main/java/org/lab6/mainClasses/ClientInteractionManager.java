package org.lab6.mainClasses;

import org.lab6.Main;
import org.lab6.mainClasses.UDPInteraction.SendedCommand;
import org.lab6.mainClasses.UDPInteraction.Server_UDP_acceptor;
import org.lab6.storedClasses.LabWork;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientInteractionManager extends Thread{
    private Server_UDP_acceptor transmitter;
    private ExecutorService commandExecutor;
    private static ExecutorService sendingExecutor;
    private HashMap<InetSocketAddress, ClientCommandManager> currentClients;
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
        Object acceptedObject=transmitter.get();
        InetSocketAddress userAddress=transmitter.getUserAdress();
        if(currentClients.containsKey(userAddress)) {
            currentClients.get(userAddress).setSendedCommand((SendedCommand) acceptedObject);
            commandExecutor.submit(currentClients.get(userAddress));
        }else{
            ClientCommandManager ccm=new ClientCommandManager(userAddress);
            currentClients.put(userAddress, ccm);
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
    public static Set<InetSocketAddress> getAddresses(){
        return cim.currentClients.keySet();
    }
}
