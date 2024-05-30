package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class Info extends Command{
	public Info(){
		this.setUserGUIAvailable(true);
	}
	@Override
	public void execute(){
		responseManager.append(LabWorkListManager.getCollectionInfo());
	}
	@Override
	public String getComment(){
		return "info%вывести в стандартный поток вывода информацию о коллекции";
	}
}