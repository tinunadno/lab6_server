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
    private static int port=17938;
    private static Connection connection;
    private static boolean isApplicationRunning;
    public static void main(String[] args) {
        isApplicationRunning=true;
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
        ClientInteractionManager cim=new ClientInteractionManager(port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    cim.clearChannel();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
    }
    public static Connection getConnection(){return connection;}
    public static boolean isRunning(){
        return isApplicationRunning;
    }
}