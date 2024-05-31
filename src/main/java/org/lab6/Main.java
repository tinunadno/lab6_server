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
    private static boolean isApplicationRunning;
    public static void main(String[] args) {
        isApplicationRunning=true;

        LabWorkListManager.init();
        ClientInteractionManager cim=new ClientInteractionManager(port);
        cim.start();
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
    public static boolean isRunning(){
        return isApplicationRunning;
    }
}