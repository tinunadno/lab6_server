package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class Clear extends Command implements UserIdRequire{
	private int userID;
	@Override
	public void setUserId(int userID){
		this.userID=userID;
	}
	@Override
	public void execute(){
		System.out.println(userID);
		LabWorkListManager.clear(userID);
	}
	@Override
	public String getComment(){
		return "clear%очистить коллекцию";
	}
}