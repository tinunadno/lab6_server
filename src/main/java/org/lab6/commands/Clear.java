package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class Clear extends Command implements UserIdRequire{
	private int userID;
	private String userName;
	public Clear(){
		this.setRequiresUserID(true);
		this.setUserGUIAvailable(true);
	}
	@Override
	public void setUserId(int userID, String userName){
		this.userID=userID;
		this.userName=userName;
	}
	@Override
	public void execute(){
		LabWorkListManager.clear(userID, responseManager);
	}
	@Override
	public String getComment(){
		return "clear%очистить коллекцию";
	}
}