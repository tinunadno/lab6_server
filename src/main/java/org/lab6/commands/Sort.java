package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class Sort extends Command {
	@Override
	public void execute(){
		LabWorkListManager.sort();
		responseManager.append("sorted LabWork List");
	}
	@Override
	public String getComment(){
		return "sort%отсортировать коллекцию в естественном порядке";
	}
}