package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class Add extends Command implements CommandWithParsedInstance{
	private LabWork parsedInstance;
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void execute(){
		LabWorkListManager.append(parsedInstance);
	}
	@Override
	public String getComment(){
		return "add%добавить новый элемент в коллекцию";
	}
}