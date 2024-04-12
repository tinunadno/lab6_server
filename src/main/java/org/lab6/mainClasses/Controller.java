package org.lab6.mainClasses;

import org.lab6.commands.*;
import org.lab6.storedClasses.LabWork;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
	private int port;
	private InetAddress address;
	private ClientCommandManager clientThread;
	private int userID;
	/**
	 * 	Map with command objects and names
	 */

	private static Map<String, Command> comands=new HashMap<String, Command>();
	static{
		comands.put("help", new Help());
		comands.put("info", new Info());
		comands.put("show", new Show());
		comands.put("add", new Add());
		comands.put("update", new Update());
		comands.put("remove_by_id", new RemoveByID());
		comands.put("clear", new Clear());
		comands.put("add_if_max", new AddIfMax());
		comands.put("remove_greater", new RemoveGreater());
		comands.put("filter_by_description", new FilterByDescription());
		comands.put("print_field_ascending_difficulty", new PrintFieldAscendingDifficulty());
		comands.put("min_by_name", new MinByName());
		comands.put("sort", new Sort());
		comands.put("synchronize", new Synchronize());
		comands.put("disconnect", new Disconnect());
		comands.put("get_list_as_json", new GetListAsJson());
	}
	public Controller(int port, InetAddress address, ClientCommandManager clientThread, int userID){
		this.port=port;
		this.address=address;
		this.clientThread=clientThread;
		this.userID=userID;
	}

	/**
	 * Calls command object without argument
	 * @param key
	 */
	public void invoke(String key){
		try{
			Command command=comands.get(key);
			if(command instanceof ResponseCommand) {
				((ResponseCommand) command).setAddress(address);
				((ResponseCommand) command).setPort(port);
			}
			if(command instanceof InterruptingCommand)
				((InterruptingCommand)command).setThread(clientThread);
			if(command instanceof UserIdRequire)
				((UserIdRequire) command).setUserId(userID);
			command.execute();
		}catch(NullPointerException e){
			ResponseManager.append("\""+key+"\" is not a command, use help for syntax");
		}
	}


	/**
	 * calls command object with argument
	 * @param key
	 * @param argument
	 */
	public void invoke(String key, String argument){
		try{
			Command command=comands.get(key);
			if(command instanceof UserIdRequire)
				((UserIdRequire) command).setUserId(userID);
			if(command instanceof CommandWithArgument)
				((CommandWithArgument)command).setArgument(argument);
			comands.get(key).execute();
		}catch(NullPointerException e){
			ResponseManager.append("\""+key+"\" is not a command, use help for syntax");
		}
	}
	public void invoke(String key, LabWork labWork){
		try{
			Command command=comands.get(key);
			if(command instanceof UserIdRequire)
				((UserIdRequire) command).setUserId(userID);
			if(command instanceof CommandWithParsedInstance)
				((CommandWithParsedInstance)(comands.get(key))).setParsedInstance(labWork);
			comands.get(key).execute();
		}catch(NullPointerException e){
			ResponseManager.append("\""+key+"\" is not a command, use help for syntax");
		}
	}

	public void invoke(String key, String argument, LabWork labWork){
		try{
			Command command=comands.get(key);
			if(command instanceof UserIdRequire)
				((UserIdRequire) command).setUserId(userID);
			if(command instanceof CommandWithParsedInstance)
				((CommandWithParsedInstance)(comands.get(key))).setParsedInstance(labWork);
			if(command instanceof CommandWithArgument)
				((CommandWithArgument)command).setArgument(argument);
			comands.get(key).execute();
		}catch(NullPointerException e){
			ResponseManager.append("\""+key+"\" is not a command, use help for syntax");
		}
	}

	/**
	 * showing command names and descriptions from commands Map
	 */
	public static void showComands(){
		String ret="";
		for (Command value : comands.values()) {
			ret+=(value.getComment().replace("%", "   "))+"\n";
		}
		ResponseManager.append(ret);
	}
	public static ArrayList<ArrayList<String>> getSortedCommands(){
		ArrayList<String> commandsWithNoArgument=new ArrayList<>();
		ArrayList<String> commandsWithArgument=new ArrayList<>();
		ArrayList<String> commandsWithNeedParse=new ArrayList<>();
		for (Command value : comands.values()) {
			String commandName=value.getComment().substring(0, value.getComment().indexOf('%'));
			if((value instanceof CommandWithArgument) && (value instanceof CommandWithParsedInstance)){
				commandsWithNeedParse.add(commandName);
				commandsWithArgument.add(commandName);
			}
			else if(value instanceof CommandWithArgument)commandsWithArgument.add(commandName);
			else if(value instanceof CommandWithParsedInstance)commandsWithNeedParse.add(commandName);
			else commandsWithNoArgument.add(commandName);
		}
		ArrayList<ArrayList<String>> commandListByTypes=new ArrayList<>();
		commandListByTypes.add(commandsWithArgument);
		commandListByTypes.add(commandsWithNoArgument);
		commandListByTypes.add(commandsWithNeedParse);
		return commandListByTypes;
	}
}