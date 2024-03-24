package org.lab6.commands;
public interface CommandNoArgument {
	/**
	 * calls the corresponding function from LabWorkListManager class
	 */
	void execute();
	/**
	 * returns name and description of a command
	 * @return
	 */
	String getComment();
}