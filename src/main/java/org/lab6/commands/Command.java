package org.lab6.commands;

public abstract class Command implements CommandNoArgument{

    private boolean requiresArgument=false;
    private boolean requiresLabWorkInstance=false;
    private boolean interruptsThread=false;
    private boolean givesResponse=false;
    private boolean requiresUserID=false;

    /**
     * calls the corresponding function from LabWorkListManager class
     */
    public abstract void execute();

    /**
     * returns name and description of a command
     * @return
     */
    public abstract String getComment();
    protected void setRequiresArgument(boolean value){requiresArgument=value;}
    protected void setRequiresLabWorkInstance(boolean value){requiresLabWorkInstance=value;}
    protected void setInterruptsThread(boolean value){interruptsThread=value;}
    protected void setGivesResponse(boolean value){givesResponse=value;}
    protected void setRequiresUserID(boolean value){requiresUserID=value;}


    public boolean isRequiresArgument(){return requiresArgument;}
    public boolean isRequiresLabWorkInstance(){return requiresLabWorkInstance;}
    public boolean isInterruptsThread(){return interruptsThread;}
    public boolean isGivesResponse(){return givesResponse;}
    public boolean isRequiresUserID(){return requiresUserID;}
}
