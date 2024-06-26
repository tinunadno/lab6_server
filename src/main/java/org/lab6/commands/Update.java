package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.ResponseManager;
import org.lab6.storedClasses.LabWork;

public class Update extends Command implements CommandWithArgument, CommandWithParsedInstance, UserIdRequire{
	private LabWork parsedInstance;
	private int userID;
	private String userName;
	public Update(){
		this.setRequiresArgument(true);
		this.setRequiresLabWorkInstance(true);
		this.setRequiresUserID(true);
	}
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void setUserId(int userID, String userName){
		this.userID=userID;
		this.userName=userName;
	}
	private String argument;
	@Override
	public void execute(){
		try {
			int index = Integer.parseInt(argument);
			LabWorkListManager.set(index, parsedInstance, userID, responseManager);
		}catch(NumberFormatException e){
			responseManager.append("can't interpretate "+argument+" as integer");
		}
	}
	@Override
	public String getComment(){
		return "update%id%обновить значение элемента коллекции, id которого равен заданному";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}