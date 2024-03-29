package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.Message;

public class Show extends Command {
	@Override
	public void execute(){
		Message.append(LabWorkListManager.getCollectionAsString());
	}
	@Override
	public String getComment(){
		return "show%вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
	}
}