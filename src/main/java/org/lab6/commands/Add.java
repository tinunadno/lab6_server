package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class Add extends Command implements CommandWithParsedInstance, UserIdRequire{

	private LabWork parsedInstance;
	private int userID;
	private String userName;

	public Add(){
		this.setRequiresUserID(true);
		this.setRequiresLabWorkInstance(true);
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
		LabWorkListManager.append(parsedInstance, userID, userName);
	}
	@Override
	public String getComment(){
		return "add%добавить новый элемент в коллекцию";
	}
}