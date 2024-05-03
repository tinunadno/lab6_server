package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class MinByName extends Command{
	@Override
	public void execute(){
		LabWorkListManager.printMinByName(responseManager);
	}
	@Override
	public String getComment(){
		return "min_by_name%вывести любой объект из коллекции, значение поля name которого является минимальным";
	}
}