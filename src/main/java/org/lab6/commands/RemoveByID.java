package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class RemoveByID extends Command implements CommandWithArgument, UserIdRequire {
	private String argument;
	private int userID;
	private String userName;
	public RemoveByID(){
		this.setRequiresArgument(true);
		this.setRequiresUserID(true);
	}
	@Override
	public void execute(){
		int index=Integer.parseInt(argument);
		LabWorkListManager.remove(index, userID, responseManager);
	}
	@Override
	public void setUserId(int userID, String userName){
		this.userID=userID;
		this.userName=userName;
	}
	@Override
	public String getComment(){
		return "remove_by_id%id%удалить элемент из коллекции по его id";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}