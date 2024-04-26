package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class FilterByDescription extends Command implements CommandWithArgument{
	private String argument;
	public FilterByDescription(){
		this.setRequiresArgument(true);
	}
	@Override
	public void execute(){
		LabWorkListManager.FilterByDescription(argument);
	}
	@Override
	public String getComment(){
		return "filter_by_description description%вывести элементы, значение поля description которых равно заданному";
	}
	@Override
	public void setArgument(String arg){this.argument=arg;}
}