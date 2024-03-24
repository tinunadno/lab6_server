package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class AddIfMax extends Command implements CommandWithParsedInstance{
	private LabWork parsedInstance;
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	@Override
	public void execute(){
		LabWorkListManager.addIfMax(parsedInstance);
	}
	@Override
	public String getComment(){
		return "add_if_max%добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
	}
}