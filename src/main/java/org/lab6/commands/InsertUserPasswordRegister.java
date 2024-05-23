package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.HashPasswords;
import org.lab6.mainClasses.LabWorkDAO;

import java.sql.SQLException;

public class InsertUserPasswordRegister extends Command implements ThreadInteractingCommand, CommandWithArgument{
    private String argument;
    private ClientCommandManager clientThread;

    public InsertUserPasswordRegister() {
        this.setRequiresArgument(true);
        this.setInterruptsThread(true);
    }

    @Override
    public void execute(){
        try{
            LabWorkDAO.insertNewUser(clientThread.getUserName(), HashPasswords.toSHA384(argument));
            responseManager.append("successfully inserted new user password");
            clientThread.setIsAuthorized(true);
            int userID=LabWorkDAO.getUserID(clientThread.getUserName());
            clientThread.setUserID(userID);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setThread(ClientCommandManager clientThread) {
        this.clientThread = clientThread;
    }

    @Override
    public String getComment() {
        return "insert_password_r%insert user password for register";
    }

    @Override
    public void setArgument(String arg) {
        this.argument = arg;
    }
}
