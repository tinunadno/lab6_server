package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;
import org.lab6.mainClasses.HashPasswords;
import org.lab6.mainClasses.LabWorkDAO;

import java.sql.SQLException;

public class InsertUserPasswordAuthorize extends Command implements ThreadInteractingCommand, CommandWithArgument {
    private String argument;
    private ClientCommandManager clientThread;

    public InsertUserPasswordAuthorize() {
        this.setRequiresArgument(true);
        this.setInterruptsThread(true);
    }

    @Override
    public void execute() {
        try {
            String userPassword = LabWorkDAO.getUserPassword(clientThread.getUserName());
            String hashedPassword = HashPasswords.toSHA384(argument);
            if (userPassword.equals(hashedPassword)) {
                responseManager.append("successfully authorized!");
                clientThread.setIsAuthorized(true);
            } else {
                responseManager.append("wrong password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setThread(ClientCommandManager clientThread) {
        this.clientThread = clientThread;
    }

    @Override
    public String getComment() {
        return "insert_password_a%insert user password for authorization";
    }

    @Override
    public void setArgument(String arg) {
        this.argument = arg;
    }
}
