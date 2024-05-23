package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.LabWorkDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertUserNameRegister extends Command implements ThreadInteractingCommand, CommandWithArgument{
    private String argument;
    private ClientCommandManager clientThread;
    public InsertUserNameRegister(){
        this.setRequiresArgument(true);
        this.setInterruptsThread(true);
    }

    @Override
    public void execute(){
        try {
            int userID=LabWorkDAO.getUserID(argument);
            if(userID!=-1) {
                responseManager.append("user with this name already exists");
            }
            else {
                responseManager.append("successfully added new user name");
                clientThread.setUserName(argument);
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
        return "authorize_r%insert new user name for registration";
    }

    @Override
    public void setArgument(String arg){this.argument=arg;}
}
