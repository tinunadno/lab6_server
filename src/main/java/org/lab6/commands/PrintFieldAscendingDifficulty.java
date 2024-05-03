package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class PrintFieldAscendingDifficulty extends Command{
	@Override
	public void execute(){
		LabWorkListManager.printFieldAscendingDifficulty(responseManager);
	}
	@Override
	public String getComment(){
		return "print_field_ascending_difficulty%вывести значения поля difficulty всех элементов в порядке возрастания";
	}
}