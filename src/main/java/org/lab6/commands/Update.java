package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.storedClasses.LabWork;

public class Update extends Command implements CommandWithArgument, CommandWithParsedInstance {
	private LabWork parsedInstance;
	@Override
	public void setParsedInstance(LabWork parsedInstance){this.parsedInstance=parsedInstance;}
	private String argument;
	@Override
	public void execute(){
		try {
			int index = Integer.parseInt(argument);
			LabWork labwork=parsedInstance;
			LabWorkListManager.set(index, labwork);
		}catch(NumberFormatException e){
			System.out.println("can't interpretate "+argument+" as integer");
		}
	}
	@Override
	public String getComment(){
		return "update%id%обновить значение элемента коллекции, id которого равен заданному";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}