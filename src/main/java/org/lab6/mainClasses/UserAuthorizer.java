package org.lab6.mainClasses;

import org.lab6.Main;

import java.net.InetAddress;
import java.sql.*;

public class UserAuthorizer {
    private int port;
    private int serverPort;
    private InetAddress address;
    public UserAuthorizer(int port, int serverPort, InetAddress address){
        this.port=port;
        this.serverPort=serverPort;
        this.address=address;
    }
    public int init(){
        if((UDP_transmitter.get(port)).equals("r")){
            String newUserName=((Message)UDP_transmitter.get(port)).getMessage();
            String newUserPassword=((Message)UDP_transmitter.get(port)).getMessage();
            String query = "INSERT INTO users(userName, password)\nVALUES\n('"+newUserName+"','"+newUserPassword+"')";
            try {
                Statement st=Main.getConnection().createStatement();
                st.execute(query);
                UDP_transmitter.send(serverPort, address, new Message("Successfully added new user"));
            }catch(SQLException e){
                System.out.println("bad query");
                e.printStackTrace();
            }
        }

        int userId = tryToGetUserName();
        System.out.println(userId);
        tryToGetPassword(userId);
        return userId;

    }
    private int tryToGetUserName(){
        String query = "SELECT id, username FROM Users";
        PreparedStatement s=null;
        try {
            s = Main.getConnection().prepareStatement(query);
        }catch(SQLException e){
            System.out.println("bad querry");
        }
        while(true) {
            String userName = ((Message) UDP_transmitter.get(port)).getMessage();
            System.out.println("USNM:"+userName);
            try {
                ResultSet userNameSet = s.executeQuery();
                while (userNameSet.next()) {
                    System.out.println("DBNM:"+userNameSet.getString("username"));
                    if (userNameSet.getString("username").equals( userName)) {
                        Message message=new Message("SUCCESS");
                        UDP_transmitter.send(serverPort, address, message);
                        return Integer.parseInt(userNameSet.getString("id"));
                    }
                }
                Message message=new Message("can't find user with this name");
                UDP_transmitter.send(serverPort, address, message);
            } catch (SQLException e) {
                System.out.println("can't send query to DataBase");
            }
        }
    }
    private void tryToGetPassword(int userID){
        String query = "SELECT password FROM Users WHERE(id="+userID+")";
        try {
            PreparedStatement s = Main.getConnection().prepareStatement(query);
            ResultSet userPasswordSet = s.executeQuery();
            userPasswordSet.next();
            String userPassword = userPasswordSet.getString("password");
            while(true){
                String userPasswordFromClient = ((Message) UDP_transmitter.get(port)).getMessage();
                if(userPassword.equals(userPasswordFromClient)){
                    Message message=new Message("SUCCESS");
                    UDP_transmitter.send(serverPort, address, message);
                    return;
                }
                Message message=new Message("wrong password");
                UDP_transmitter.send(serverPort, address, message);
            }
        }catch(SQLException e){
            System.out.println("bad querry");
            e.printStackTrace();
        }
    }
}
