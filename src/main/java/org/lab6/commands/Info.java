package org.lab6.commands;

import org.lab6.mainClasses.LabWorkListManager;
import org.lab6.mainClasses.Message;

public class Info extends Command {
	@Override
	public void execute(){Message.append(LabWorkListManager.getCollectionInfo());
	}
	@Override
	public String getComment(){
		return "info%вывести в стандартный поток вывода информацию о коллекции";
	}
}