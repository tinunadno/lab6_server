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
    public ResultSet init(){
        if((UDP_transmitter.get(port)).equals("r")){
            String newUserName=((Message)UDP_transmitter.get(port)).getMessage();
            String newUserPassword=((Message)UDP_transmitter.get(port)).getMessage();

            String insert_query = "INSERT INTO users(userName, password, wallet)\nVALUES\n('"+newUserName+"','"+newUserPassword+"', 0)";
            try {
                Statement st=Main.getConnection().createStatement();
                st.execute(insert_query);

                UDP_transmitter.send(serverPort, address, new Message("Successfully added new user"));
            }catch(SQLException e){
                ResponseManager.append(e.getMessage());
                e.printStackTrace();
            }
        }

        ResultSet userInfo = tryToGetUserInfo();
        try {
            tryToGetPassword(userInfo.getString("id"));
        }catch (SQLException e){}
        return userInfo;

    }
    private ResultSet tryToGetUserInfo(){
        String query = "SELECT id, username FROM Users";
        PreparedStatement s=null;
        try {
            s = Main.getConnection().prepareStatement(query);
        }catch(SQLException e){
            System.out.println("bad query");
        }
        while(true) {
            String userName = ((Message) UDP_transmitter.get(port)).getMessage();
            try {
                ResultSet userNameSet = s.executeQuery();
                while (userNameSet.next()) {
                    String user_name=userNameSet.getString("username");
                    if (user_name.equals( userName)) {
                        Message message=new Message("SUCCESS");
                        UDP_transmitter.send(serverPort, address, message);
                        return userNameSet;
                    }
                }
                Message message=new Message("can't find user with this name");
                UDP_transmitter.send(serverPort, address, message);
            } catch (SQLException e) {
                System.out.println("can't send query to DataBase");
            }
        }
    }
    private void tryToGetPassword(String userID){
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
