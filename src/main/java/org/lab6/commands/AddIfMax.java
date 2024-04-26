package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class AddIfMax extends Command implements CommandWithParsedInstance, UserIdRequire{
	private LabWork parsedInstance;
	private int userID;
	public AddIfMax(){
		this.setRequiresLabWorkInstance(true);
		this.setRequiresUserID(true);
	}
	@Override
	public void setUserId(int userID){
		this.userID=userID;
	}
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void execute(){
		LabWorkListManager.addIfMax(parsedInstance, userID);
	}
	@Override
	public String getComment(){
		return "add_if_max%добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
	}
}