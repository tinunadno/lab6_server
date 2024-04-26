package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class Clear extends Command implements UserIdRequire{
	private int userID;
	public Clear(){
		this.setRequiresUserID(true);
	}
	@Override
	public void setUserId(int userID){
		this.userID=userID;
	}
	@Override
	public void execute(){
		LabWorkListManager.clear(userID);
	}
	@Override
	public String getComment(){
		return "clear%очистить коллекцию";
	}
}