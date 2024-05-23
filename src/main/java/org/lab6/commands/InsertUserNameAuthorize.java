package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.LabWorkDAO;
import org.lab6.mainClasses.ResponseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertUserNameAuthorize extends Command implements ThreadInteractingCommand, CommandWithArgument {
    private String argument;
    private ClientCommandManager clientThread;
    public InsertUserNameAuthorize(){
        this.setRequiresArgument(true);
        this.setInterruptsThread(true);
    }

    @Override
    public void execute(){
        try {
            int userID=LabWorkDAO.getUserID(argument);
            if(userID!=-1){
                clientThread.setAuthorizedName(true);
                clientThread.setUserName(argument);
                clientThread.setUserID(userID);
                responseManager.append("successfully authorized user name");
            }else{
                responseManager.append("can't find user with this name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setThread(ClientCommandManager clientThread){
        this.clientThread=clientThread;
    }

    @Override
    public String getComment(){
        return "authorize_a%insert user name for authorization";
    }

    @Override
    public void setArgument(String arg){this.argument=arg;}
}
