package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class RemoveGreater extends Command implements CommandWithArgument, UserIdRequire {
	private String argument;
	private int userID;
	public RemoveGreater(){
		this.setRequiresArgument(true);
		this.setRequiresUserID(true);
	}
	@Override
	public void setUserId(int userID){
		this.userID=userID;
	}
	@Override
	public void execute(){
		LabWorkListManager.RemoveGreater(Float.parseFloat(argument), userID);
	}
	@Override
	public String getComment(){
		return "remove_greater%удалить из коллекции все элементы, превышающие заданный";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}