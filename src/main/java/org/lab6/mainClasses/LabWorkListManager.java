package org.lab6.mainClasses;

import org.lab6.storedClasses.Difficulty;
import org.lab6.storedClasses.LabWork;
import org.lab6.storedClasses.Person;

import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class LabWorkListManager {
	private static ArrayList<LabWork> list;
	private static java.time.LocalDate creationDate;

	/**
	 * initialize LabWork ArrayList
	 */
	public static void init(){
		list=DBParser.parseLabWorkFromDB();
	}

	/**
	 * add LabWork Object to LabWork List
	 * @param lw
	 */
	
	public static void append(LabWork lw, int userID, String userName, ResponseManager responseManager){
		lw.setUserID(userID);
		lw.setUserName(userName);
		try{
			LabWorkDAO.addLabWork(lw);
			list.add(lw);
			responseManager.append("successfully added new instance");
		}catch(SQLException e){
			responseManager.append("SERVER_ERROR:can't add LabWork instance to DataBase");
			e.printStackTrace();
		}
	}


	/**
	 * replace LabWork object in ArrayList by its id in ArrayList
	 * @param id
	 * @param lw
	 */
	public static void set(int id, LabWork lw, int userID, ResponseManager responseManager){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getID()==id) {
				try {
					LabWorkDAO.update((int) list.get(i).getID(), userID, lw);
					lw.setID((int) list.get(i).getID());
					lw.setUserID(list.get(i).getUserID());
					lw.setCreationDate(list.get(i).getCreationDate());
					list.set(i, lw);
					responseManager.append("successfully updated instance with id "+id);
				}catch (SQLException e){
					e.printStackTrace();
					responseManager.append("SERVER_ERROR:can't update instance with id "+id+", because unpredictable sql error");
				}catch (IllegalUserAccessException e){
					responseManager.append(e.getMessage());
				}
			}
		}
	}

	/**
	 * removes LabWork object from ArrayList by its id
	 * @param id
	 */
	
	public static void remove(int id, int userID, ResponseManager responseManager){

		try{
			LabWorkDAO.remove(id, userID);

			list.removeIf(lw->(lw.getID()==id));
			responseManager.append("successfully removed instance with index " + id);
		}
		catch(SQLException e){
			responseManager.append("SERVER_ERROR:can't delete LabWork instance with id "+id+", because sql unpredictable mistake");
			e.printStackTrace();
		}catch (IllegalUserAccessException e){
			responseManager.append(e.getMessage());
		}
	}

	/**
	 * clear ArrayList
	 */
	public static void clear(int userID, ResponseManager responseManager){
		try {

			LabWorkDAO.clear(userID);

			Person.clearPassportBase();

								list.removeIf(lw->(lw.getUserID()==userID));
			responseManager.append("successfully cleared LabWork Base");
		}catch(SQLException e){
			responseManager.append("SERVER_ERROR:can't clear LabWork instances of current user");
			e.printStackTrace();
		}
	}

	/**
	 * save ArrayList as json file
	 * @param way
	 */
	public static void save(String way, ResponseManager responseManager){
		try (var fw = new FileWriter(way)) {
			fw.write(toJson());
			responseManager.append("successfully saved current LabWork List on server");
		} catch (Exception e) {
			responseManager.append("bad file name");
		}
	}

	/**
	 * add LabWork object if minimal point field is max
	 * @param lw
	 */
	public static void addIfMax(LabWork lw, int userID, String userName, ResponseManager responseManager){
		boolean flag=true;
		for(LabWork labwork: list)
			flag=!(labwork.getMinimalPoint()>lw.getMinimalPoint());
		if(flag)append(lw, userID, userName, responseManager);
	}

	/**
	 * removes LabWork object with max minimalPoint
	 * @param val
	 */
	public static void RemoveGreater(float val, int userID, ResponseManager responseManager){
		for(int i=0;i<list.size();i++)
			if(list.get(i).getMinimalPoint()>val)remove((int)list.get(i).getID(), userID, responseManager);
		responseManager.append("remove greater");
	}

	/**
	 * shows LabWork objects with desired description
	 * @param description
	 */
	public static void FilterByDescription(String description, ResponseManager responseManager){
		String ret="";
		for(int i=0;i<list.size();i++)
			if(list.get(i).getDescription().equals(description))ret+=(list.get(i).toString()+"\n")+"\n";
		responseManager.append(ret);
	}

	/**
	 * print all LabWork objects sorted by difficulty
	 */
	public static void printFieldAscendingDifficulty(ResponseManager responseManager){
		String ret="";
		for(Difficulty diff:Difficulty.values())
			for(int i=0;i<list.size();i++)
				if(list.get(i).getDifficulty()==diff)ret+=(list.get(i).toString()+"\n")+"\n";
		responseManager.append(ret);
	}

	/**
	 * print LabWork object with minimal name
	 */
	public static void printMinByName(ResponseManager responseManager){
		if(list.size()!=0 && list!=null){
			LabWork lw=list.get(0);
			for(int i=1;i<list.size();i++)
				if(lw.getName().length()>list.get(i).getName().length())lw=list.get(i);
			responseManager.append(lw.toString());
		}
		else {
			responseManager.append("list is empty");
		}
	}
	public static void buyLabWork(int lwID, int userID,String userName, ResponseManager responseManager){
		try {
			ResultSet lw_info = LabWorkDAO.getLabWorkInfoForBuy(lwID);
			double lw_price=Double.parseDouble(lw_info.getString("price"));
			int lw_user_id=Integer.parseInt(lw_info.getString("user_id"));
			if(lw_user_id==userID){
				responseManager.append("this LabWork instance already yours");
				return;
			}
			double userWallet=LabWorkDAO.getMoneyCount(userID);
			if(userWallet<lw_price){
				responseManager.append("you don't have enough money to buy labWork with ID:"+lwID);
				return;
			}
			LabWorkDAO.insertMoney(userID, lw_price*-1.0);
			LabWorkDAO.insertMoney(lw_user_id, lw_price);
			LabWorkDAO.changeLabWorkUser(lwID, userID, userName);
			list.forEach(
					(lw) ->{
						if(lw.getID()==lwID) {
							lw.setUserID(userID);
							lw.setUserName(userName);
						}
					});
			responseManager.append("successfully buyed LabWork with ID:"+lwID);
		}catch (SQLException e){
			e.printStackTrace();
			responseManager.append("transaction cancelled because of some sql exceptions");
		}
	}

	/**
	 * sort objects in ArrayList
	 */
	public static void sort(){
		Collections.sort(list);
	}

	/**
	 * print collection info (size, type, init date)
	 * @return
	 */
	public static String getCollectionInfo(){
		return "Collection info:[size:"+list.size()+", type:"+list.getClass()+", init date:"+creationDate+"]";
	}

	/**
	 * get Collection as String
	 * @return
	 */
	public static String getCollectionAsString(){
		if(list.size()==0)return "list is empty";
		String ret="";
		for (LabWork value : list) {
			ret+=(value.toString()+"\n\n");
		}
		return ret;
	}

	/**
	 * get Collection as String in json format
	 * @return
	 */
	public static String toJson(){
		String ret="{\n\"LabWorks\": [\n";
		for(int i=0;i<list.size()-1;i++)
			ret+=list.get(i).toJson()+",\n";
		ret+=list.get(list.size()-1).toJson()+"\n]\n}";
		return ret;
	}
}