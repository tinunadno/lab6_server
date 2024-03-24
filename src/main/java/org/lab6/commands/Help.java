package org.lab6.commands;


import org.lab6.mainClasses.Controller;

public class Help extends Command {
	@Override
	public void execute(){
		Controller.showComands();
	}
	@Override
	public String getComment(){
		return "help%вывести справку по доступным командам";
	}
}