package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class BuyLabWork extends Command implements CommandWithArgument, UserIdRequire{
    private int userID;
    private String userName;
    private int lwID;
    public BuyLabWork(){
        this.setRequiresUserID(true);
        this.setRequiresArgument(true);
    }

    @Override
    public void execute(){
        LabWorkListManager.buyLabWork(lwID, userID, userName, responseManager);
    }
    @Override
    public void setArgument(String argument){
        try {
            this.lwID = Integer.parseInt(argument);
        }catch (NumberFormatException e){
            responseManager.append(argument+" is not number, so it can't be applied as LabWork ID");
        }
    }
    @Override
    public void setUserId(int userID, String userName){
        this.userID=userID;
        this.userName=userName;
    }
    @Override
    public String getComment(){
        return "buy_lab_work%LabWork_id%покупка labWork у другого пользователя";
    }
}
