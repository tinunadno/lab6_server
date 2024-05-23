package org.lab6;

import org.lab6.mainClasses.*;
import org.postgresql.jdbc.TimestampUtils;

import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Main {
    private static List<ClientCommandManager> connectedUsers;
    private static int port=17937;
    private static int currentPort=17749;
    private static int currentServerPort=17750;
    private static int serverPort=17938;
    private static InetAddress adress=null;
    private static Connection connection;
    private static ExecutorService responseExecutorService;
    private static boolean isApplicationRunning;
    public static void main(String[] args) {
        connectedUsers=new ArrayList<>();
        isApplicationRunning=true;
        responseExecutorService= Executors.newCachedThreadPool();
        String connectionUrl = "jdbc:postgresql://pg:5432/studs";
        String user="s409324";
        String password="IIcX*5966";

        try {
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            connection=DriverManager.getConnection(connectionUrl, user, password);
            if(connection!=null)System.out.println("Successfully connected to DataBase!");
            else System.out.println("failed to connect to DataBase, connection is null");
        }catch (ClassNotFoundException e) {
            System.out.println("ERROR:No JDBC driver found");
        }catch(SQLException e) {
            System.out.println("ERROR:can't connect to database");
        }catch(Exception e){
            e.printStackTrace();
        }



        LabWorkListManager.init();
        UserWaiter.startUserMonitor();
    }
    public static Connection getConnection(){return connection;}
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
    public static void submitResponse(ResponseManager responseManager){
        responseExecutorService.submit(responseManager);
    }
    public static boolean isRunning(){
        return isApplicationRunning;
    }
    public static void appendConnectedUsers(ClientCommandManager ccm){
        int prevLen=connectedUsers.size();
        connectedUsers.add(ccm);
        showConnectedUsers(prevLen);

    }
    public static void removeConnectedUser(ClientCommandManager ccm){
        int prevLen=connectedUsers.size();
        connectedUsers.removeIf(clientCommandManager -> (clientCommandManager.equals(ccm)));
        showConnectedUsers(prevLen);
    }
    public static void showConnectedUsers(int prevLen){
        for(int i=0;i<prevLen*2; i++) {
            System.out.print(String.format("\033[%dA", 1));
            System.out.print("\033[2K");
        }
        connectedUsers.forEach(clientCommandManager -> System.out.print(clientCommandManager+"\n"));
    }
}