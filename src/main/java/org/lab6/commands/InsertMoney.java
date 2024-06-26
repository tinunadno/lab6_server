package org.lab6.commands;

import org.lab6.mainClasses.LabWorkDAO;
import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.ResponseManager;

import java.sql.SQLException;

public class InsertMoney extends Command implements CommandWithArgument, UserIdRequire {
    private String argument;
    private int userID;
    private String userName;

    public InsertMoney() {
        this.setRequiresArgument(true);
        this.setRequiresUserID(true);
        this.setUserGUIAvailable(true);
    }

    @Override
    public void execute() {

        try {
            int sum = Integer.parseInt(argument);
            LabWorkDAO.insertMoney(userID, sum);
            responseManager.append("successfully added " + sum + " to your wallet");
        } catch (SQLException e) {
            responseManager.append("can't insert money on your account, check data you inserted");
        } catch (NumberFormatException e) {
            responseManager.append("cannot convert \"" + argument + "\" to LabWork ID.");
        }
    }

    @Override
    public void setUserId(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    @Override
    public String getComment() {
        return "insert_money%внести денег на счет";
    }

    @Override
    public void setArgument(String arg) {
        this.argument = arg;
    }
}
