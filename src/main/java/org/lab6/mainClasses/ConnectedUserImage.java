package org.lab6.mainClasses;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;

public class ConnectedUserImage {
    private static int usersCount=0;
    public static void showUsers(HashMap<InetSocketAddress, ClientCommandManager> users){
        for(int i=0;i<usersCount*2; i++) {
            System.out.print(String.format("\033[%dA", 1));
            System.out.print("\033[2K");
        }
        for(SocketAddress key:users.keySet()){
            System.out.println(users.get(key));
        }
        usersCount=users.size();
    }
}
