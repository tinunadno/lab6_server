package org.lab6.commands;

import org.lab6.mainClasses.LabWorkDAO;
import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.ResponseManager;

import java.sql.SQLException;

public class GetUserInfo  extends Command implements UserIdRequire{
    private int userID;
    private String userName;
    public GetUserInfo(){
        this.setRequiresUserID(true);
    }
    @Override
    public void setUserId(int userID, String userName){
        this.userID=userID;
        this.userName=userName;
    }
    @Override
    public void execute(){
        try {
            responseManager.append("[user name: " + userName + ",\n user wallet: " + LabWorkDAO.getMoneyCount(userID) + "]");
        }catch (SQLException e){
            responseManager.append("can't get information about this user");
        }
    }
    @Override
    public String getComment(){
        return "get_user_info%получить имя юзера и количество денег на счету";
    }
}
