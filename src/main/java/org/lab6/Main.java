package org.lab6;

import org.lab6.mainClasses.*;
import org.lab6.storedClasses.LabWork;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.ArrayList;

public class Main {
    private static int port=6453;
    private static int serverPort=6464;
    private static InetAddress adress=null;

    public static void main(String[] args) {


        Object bootOption=UDP_transmitter.get(getPort());
        if(bootOption instanceof Message){
            if(((Message) bootOption).getMessage().equals("server_boot")){
                try {
                    LabWorkListManager.init(JsonToLabWork.getLabWork("src/main/java/org/lab6/test.json"));
                    ResponseManager.append("successfully booted from server");
                }catch(FileNotFoundException e){
                    System.out.println("VERY BAD, I LOST MY BOOT FILE");
                    ResponseManager.append("fail booting from server");
                    LabWorkListManager.init(new ArrayList<>());
                }
                ResponseManager.sendMessage();
            }
        }else if(bootOption instanceof ArrayList<?>){
            LabWorkListManager.init((ArrayList<LabWork>) bootOption);
        }
        ClientCommandManager.startMonitoring();

    }
    public static int getPort(){return port;}
    public static int getServerPort(){return serverPort;}
    public static void setInetAdress(InetAddress Address){adress=Address;}
    public static InetAddress getAdress(){return adress;}
}