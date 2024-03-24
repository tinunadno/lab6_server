package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class RemoveByID extends Command implements CommandWithArgument {
	private String argument;
	@Override
	public void execute(){
		int index=Integer.parseInt(argument);
		LabWorkListManager.remove(index);
	}
	@Override
	public String getComment(){
		return "remove_by_id%id%удалить элемент из коллекции по его id";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}