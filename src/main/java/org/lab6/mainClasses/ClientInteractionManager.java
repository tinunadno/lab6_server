package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientInteractionManager {
    private Server_UDP_acceptor transmitter;
    private ExecutorService commandExecutor;
    private static ExecutorService sendingExecutor;
    private HashMap<SocketAddress, ClientCommandManager> currentClients;
    private static ClientInteractionManager cim;
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
            this.startMonitor();
        }
    }
    private void get(){
        Object acceptedObject=transmitter.get();
        SocketAddress userAddress=transmitter.getUserAdress();
        if(currentClients.containsKey(userAddress)) {
            currentClients.get(userAddress).setSendedCommand((SendedCommand) acceptedObject);
            commandExecutor.submit(currentClients.get(userAddress));
        }else{
            ClientCommandManager ccm=new ClientCommandManager(userAddress);
            currentClients.put(userAddress, ccm);
            ConnectedUserImage.showUsers(currentClients);
        }
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
