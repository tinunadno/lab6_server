package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;

public class SaveOnServer extends Command implements CommandWithArgument {
	private String argument;
	@Override
	public void execute(){LabWorkListManager.save("./src/main/java/org/lab6/test.json");}
	@Override
	public String getComment(){
		return "save%сохранить коллекцию в файл";
	}
	public void setArgument(String arg){this.argument=arg;}
}