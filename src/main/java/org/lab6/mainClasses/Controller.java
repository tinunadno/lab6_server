package org.lab6.mainClasses;

import org.lab6.commands.*;
import org.lab6.storedClasses.LabWork;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private ClientCommandManager clientThread;
    private int userID;
    private ResponseManager responseManager;
    private String userName;
    /**
     * Map with command objects and names
     */

    private static Map<String, Command> comands = new HashMap<String, Command>();

    static {
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
        comands.put("insert_money", new InsertMoney());
        comands.put("get_user_info", new GetUserInfo());
        comands.put("buy_lab_work", new BuyLabWork());
        comands.put("authorize_name_a", new InsertUserNameAuthorize());
        comands.put("authorize_name_r", new InsertUserNameRegister());
        comands.put("authorize_password_a", new InsertUserPasswordAuthorize());
        comands.put("authorize_password_r", new InsertUserPasswordRegister());
        comands.put("get_user_available_commands", new getUserInterfaceAvailableCommands());
        comands.put("get_lab_work_list", new GetLabWorkList());
    }

    public Controller(ClientCommandManager clientThread, ResponseManager responseManager) {
        this.clientThread = clientThread;
        this.responseManager=responseManager;
    }

    /**
     * Calls command object without argument
     *
     * @param key
     */
    public void invoke(String key) {
        try {
            Command command = comands.get(key);
            setCommandFields(command);
            command.execute();
        } catch (NullPointerException e) {
            responseManager.append("\"" + key + "\" is not a command, use help for syntax");
        }
    }


    /**
     * calls command object with argument
     *
     * @param key
     * @param argument
     */
    public void invoke(String key, String argument) {
        try {
            Command command = comands.get(key);
            if (command.isRequiresArgument())
                ((CommandWithArgument) command).setArgument(argument);
            setCommandFields(command);
            comands.get(key).execute();
        } catch (NullPointerException e) {
            responseManager.append("\"" + key + "\" is not a command, use help for syntax");
        }
    }

    public void invoke(String key, LabWork labWork) {
        try {
            Command command = comands.get(key);
            if (command.isRequiresLabWorkInstance())
                ((CommandWithParsedInstance) (comands.get(key))).setParsedInstance(labWork);
            setCommandFields(command);
            comands.get(key).execute();
        } catch (NullPointerException e) {
            responseManager.append("\"" + key + "\" is not a command, use help for syntax");
        }
    }

    public void invoke(String key, String argument, LabWork labWork) {
        try {
            Command command = comands.get(key);
            if (command.isRequiresArgument())
                ((CommandWithArgument) command).setArgument(argument);
            if (command.isRequiresLabWorkInstance())
                ((CommandWithParsedInstance) (comands.get(key))).setParsedInstance(labWork);
            setCommandFields(command);
            comands.get(key).execute();
        } catch (NullPointerException e) {
            responseManager.append("\"" + key + "\" is not a command, use help for syntax");
        }
    }
    private void setCommandFields(Command command){
        if(command.isInterruptsThread())
            ((ThreadInteractingCommand)command).setThread(clientThread);
        if(command.isRequiresUserID())
            ((UserIdRequire)command).setUserId(userID, userName);
        ((RequireResponse)command).setResponseManager(responseManager);
    }
    public void setUserID(int userID){
        this.userID=userID;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    /**
     * showing command names and descriptions from commands Map
     */
    public static void showComands(ResponseManager responseManager) {
        String ret = "";
        for (Command value : comands.values()) {
            ret += (value.getComment().replace("%", "   ")) + "\n";
        }
        responseManager.append(ret);
    }

    public static ArrayList<ArrayList<String>> getSortedCommands() {
        ArrayList<String> commandsWithNoArgument = new ArrayList<>();
        ArrayList<String> commandsWithArgument = new ArrayList<>();
        ArrayList<String> commandsWithNeedParse = new ArrayList<>();
        for (Command value : comands.values()) {
            String commandName = value.getComment().substring(0, value.getComment().indexOf('%'));
            if ((value.isRequiresArgument()) && (value.isRequiresLabWorkInstance())) {
                commandsWithNeedParse.add(commandName);
                commandsWithArgument.add(commandName);
            } else if (value.isRequiresArgument()) commandsWithArgument.add(commandName);
            else if (value.isRequiresLabWorkInstance()) commandsWithNeedParse.add(commandName);
            if(!value.isRequiresArgument()) commandsWithNoArgument.add(commandName);
        }
        ArrayList<ArrayList<String>> commandListByTypes = new ArrayList<>();
        commandListByTypes.add(commandsWithArgument);
        commandListByTypes.add(commandsWithNoArgument);
        commandListByTypes.add(commandsWithNeedParse);
        return commandListByTypes;
    }
    public static ArrayList<String> getUserGUIAvailableCommandList(){
        ArrayList<String> userGUIAvailableCommands=new ArrayList<>();
        for (String key : comands.keySet()) {
            if((comands.get(key)).isUserGUIAvailable())
                userGUIAvailableCommands.add(key);
        }
        return userGUIAvailableCommands;
    }
}