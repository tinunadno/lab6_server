package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class Add extends Command implements CommandWithParsedInstance, UserIdRequire{
	private LabWork parsedInstance;
	private int userID;

	@Override
	public void setUserId(int userID){
		this.userID=userID;
	}
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void execute(){
		LabWorkListManager.append(parsedInstance, userID);
	}
	@Override
	public String getComment(){
		return "add%добавить новый элемент в коллекцию";
	}
}