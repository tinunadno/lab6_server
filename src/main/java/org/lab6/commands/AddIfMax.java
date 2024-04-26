package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class AddIfMax extends Command implements CommandWithParsedInstance, UserIdRequire{
	private LabWork parsedInstance;
	private int userID;
	private String userName;
	public AddIfMax(){
		this.setRequiresLabWorkInstance(true);
		this.setRequiresUserID(true);
	}
	@Override
	public void setUserId(int userID, String userName){
		this.userID=userID;
		this.userName=userName;
	}
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void execute(){
		LabWorkListManager.addIfMax(parsedInstance, userID, userName);
	}
	@Override
	public String getComment(){
		return "add_if_max%добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
	}
}