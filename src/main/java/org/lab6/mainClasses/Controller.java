package org.lab6.mainClasses;

import org.lab6.commands.*;
import org.lab6.storedClasses.LabWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
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
		comands.put("save", new SaveOnServer());
		comands.put("synchronize", new Synchronize());
		comands.put("shot_down", new ShotDown());
		comands.put("get_list_as_json", new GetListAsJson());
	}

	/**
	 * Calls command object without argument
	 * @param key
	 */
	public static void invoke(String key){
		try{
		comands.get(key).execute();
		}catch(NullPointerException e){
			new Message("\""+key+"\" is not a command, use help for syntax");
		}
	}

	/**
	 * calls command object with argument
	 * @param key
	 * @param argument
	 */
	public static void invoke(String key, String argument){
		try{
			((CommandWithArgument)(comands.get(key))).setArgument(argument);
			comands.get(key).execute();
		}catch(NullPointerException e){
			new Message("\""+key+"\" is not a command, use help for syntax");
		}
	}
	public static void invoke(String key, LabWork labWork){
		try{
			((CommandWithParsedInstance)(comands.get(key))).setParsedInstance(labWork);
			comands.get(key).execute();
		}catch(NullPointerException e){
			new Message("\""+key+"\" is not a command, use help for syntax");
		}
	}

	public static void invoke(String key, String argument, LabWork labWork){
		try{
			((CommandWithParsedInstance)(comands.get(key))).setParsedInstance(labWork);
			((CommandWithArgument)(comands.get(key))).setArgument(argument);
			comands.get(key).execute();
		}catch(NullPointerException e){
			new Message("\""+key+"\" is not a command, use help for syntax");
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
		new Message(ret);
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