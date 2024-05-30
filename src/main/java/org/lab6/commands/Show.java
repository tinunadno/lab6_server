package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class Show extends Command{
	public Show(){
		this.setUserGUIAvailable(true);
	}
	@Override
	public void execute(){
		responseManager.append(LabWorkListManager.getCollectionAsString());
	}
	@Override
	public String getComment(){
		return "show%вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
	}
}