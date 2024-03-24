package org.lab6.commands;

public abstract class Command implements CommandNoArgument{
    /**
     * calls the corresponding function from LabWorkListManager class
     */
    public abstract void execute();

    /**
     * returns name and description of a command
     * @return
     */
    public abstract String getComment();
}
