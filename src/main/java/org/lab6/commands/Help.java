package org.lab6.commands;


import org.lab6.mainClasses.Controller;
import org.lab6.mainClasses.ResponseManager;

public class Help extends Command{
	public Help(){
		this.setUserGUIAvailable(true);
	}
	@Override
	public void execute(){
		Controller.showComands(responseManager);
	}
	@Override
	public String getComment(){
		return "help%вывести справку по доступным командам";
	}
}