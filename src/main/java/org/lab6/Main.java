package org.lab6;

import org.lab6.mainClasses.*;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
public class Main {
    private static int port=6453;
    private static int currentPort=17749;
    private static int currentServerPort=17750;
    private static int serverPort=6464;
    private static InetAddress adress=null;
    private static final String psqlUserName="s409324";
    private static final String psqlPassword="IIcX*5966";
    private static final String psqlUrl="mysql://g/studs";


    public static void main(String[] args) {
        try{
            Class.forName("java.sql.Driver").getDeclaredConstructor().newInstance();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(NoSuchMethodException e){
            e.printStackTrace();
        }catch(Exception e){}
        try {
            Connection conn = DriverManager.getConnection(psqlUrl, psqlUserName, psqlPassword);
            System.out.println("Connection to Store DB succesfull!");
        }catch (SQLException e){
            System.out.println("not connected(");
            e.printStackTrace();
        }
        LabWorkListManager.init(new ArrayList<>());
        UserWaiter.startUserMonitor();
    }
    public static int getPort(){return port;}
    public static int getServerPort(){return serverPort;}
    public static int getCurrentPort(){return currentPort;}
    public static int getCurrentServerPort(){return currentServerPort;}
    public static void incCurrentPort(){
        currentPort+=2;
        currentServerPort+=2;
    }
    public static void setInetAdress(InetAddress Address){adress=Address;}
    public static InetAddress getAdress(){return adress;}
}