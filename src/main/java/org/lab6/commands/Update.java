package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.ResponseManager;
import org.lab6.storedClasses.LabWork;

public class Update extends Command implements CommandWithArgument, CommandWithParsedInstance, UserIdRequire {
	private LabWork parsedInstance;
	private int userID;
	public Update(){
		this.setRequiresArgument(true);
		this.setRequiresLabWorkInstance(true);
		this.setRequiresUserID(true);
	}
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void setUserId(int userID){
		this.userID=userID;
	}
	private String argument;
	@Override
	public void execute(){
		try {
			int index = Integer.parseInt(argument);
			LabWorkListManager.set(index, parsedInstance, userID);
		}catch(NumberFormatException e){
			ResponseManager.append("can't interpretate "+argument+" as integer");
		}
	}
	@Override
	public String getComment(){
		return "update%id%обновить значение элемента коллекции, id которого равен заданному";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}